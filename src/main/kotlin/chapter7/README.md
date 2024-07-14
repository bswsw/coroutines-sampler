
부모 컨텍스트와 자식 컨텍스트의 합성

```kotlin
private fun foldCopies(originalContext: CoroutineContext, appendContext: CoroutineContext, isNewCoroutine: Boolean): CoroutineContext {
    // Do we have something to copy left-hand side?
    val hasElementsLeft = originalContext.hasCopyableElements()
    val hasElementsRight = appendContext.hasCopyableElements()

    // Nothing to fold, so just return the sum of contexts
    if (!hasElementsLeft && !hasElementsRight) {
        return originalContext + appendContext
    }

    var leftoverContext = appendContext
    val folded = originalContext.fold<CoroutineContext>(EmptyCoroutineContext) { result, element ->
        if (element !is CopyableThreadContextElement<*>) return@fold result + element
        // Will this element be overwritten?
        val newElement = leftoverContext[element.key]
        // No, just copy it
        if (newElement == null) {
            // For 'withContext'-like builders we do not copy as the element is not shared
            return@fold result + if (isNewCoroutine) element.copyForChild() else element
        }
        // Yes, then first remove the element from append context
        leftoverContext = leftoverContext.minusKey(element.key)
        // Return the sum
        @Suppress("UNCHECKED_CAST")
        return@fold result + (element as CopyableThreadContextElement<Any?>).mergeForChild(newElement)
    }

    if (hasElementsRight) {
        leftoverContext = leftoverContext.fold<CoroutineContext>(EmptyCoroutineContext) { result, element ->
            // We're appending new context element -- we have to copy it, otherwise it may be shared with others
            if (element is CopyableThreadContextElement<*>) {
                return@fold result + element.copyForChild()
            }
            return@fold result + element
        }
    }
    return folded + leftoverContext
}
```


CoroutineScope 를 직접 생성해서 코루틴을 실행할 수 있다.

```kotlin
fun main() {
    CoroutineScope(Dispatchers.IO).launch {
        println("Hello, ${Thread.currentThread().name}")
    }

    Thread.sleep(100)
}
```

코루틴 의존성을 가지고 있다면 동기적인 프로그램에서도 일부 로직에서 비동기가 필요하다면 비교적 간단하게 사용할 수 있다는 것을 보여준다.


구조화에 대한 뇌피셜

코루틴의 구조화는 코루틴 간 트리구조의 부모자식 관계를 만들고 관리하는 것을 의미 한다.

부모 자식 관계를 통해 코루틴의 생명주기를 관리하고 코루틴이 사용하는 자원을 효율적으로 관리할 수 있다.

부모 코루틴이 종료되면 자식 코루틴도 종료되는데 부모의 CoroutineScope 나 CoroutineContext를 cancel 하는 것으로 자식 코루틴도 종료된다.


Job 생성시에 parentJob 파라미터를 주입하여 코루틴의 부모 자식 관계가 구조화 된다.

```kotlin
fun main() = runBlocking<Unit> {
    val parentJob = Job(coroutineContext.job)
}
```


구조화된 코루틴은 CoroutineScope 나 CoroutineContext 를 cancel 하여 자식 코루틴에게 종료를 전파할 수 있는데 사실상 Job을 종료하는 것과 다름없다.
```kotlin
public fun CoroutineScope.cancel(cause: CancellationException? = null) {
    val job = coroutineContext[Job] ?: error("Scope cannot be cancelled because it does not have a job: $this")
    job.cancel(cause)
}
```

```kotlin
public fun CoroutineContext.cancel(cause: CancellationException? = null) {
    this[Job]?.cancel(cause)
}
```

CoroutineScope나 Job을 새로 생성하여 구조화를 깰 수 있는데

여러 코루틴 사이에 들어가 있다면 가독성과 유지보수성이 떨어지고 상위 코루틴에 의해 생명주기가 관리되지 않아 안정성과 자원관리에 여러움이 있을 수 있다.

CoroutineContext를 결합할 때 Job 주입 여부에 따라서도 구조화를 깰 수 있다.

```kotlin
fun main() = runBlocking<Unit> {
    launch(Job()) { 
        ...
    }
}
```

Job이 아닌 다른 컨텍스트 요소를 결합하더라도 구조화는 깨지지 않는다.

```kotlin
fun main() = runBlocking<Unit> {
    launch(CoroutineName("parent") { 
        ...
    }
}
```

결론적으로 코루틴이 구조화가 되는 것은 Job을 통해 부모자식관계를 만듦으로서 가능한 것이다.


생각해보기

```kotlin
    val parentJob = Job()

    launch(parentJob) {
        val childJob = Job()
        launch {
            delay(1000)
            println(coroutineContext.job == childJob) // ??
            println(coroutineContext.job.parent == parentJob) // ??
        }
    }
```
