CoroutineContext 내부 코드를 보면 함수형 매커니즘을 일부 차용했다.

함수적으로 합칠 수 있는 연산자를 제공한다.

plus 연산자로 합치면 CombinedContext 를 리턴한다.

```kotlin
public operator fun plus(context: CoroutineContext): CoroutineContext =
    if (context === EmptyCoroutineContext) this else // fast path -- avoid lambda creation
        context.fold(this) { acc, element ->
            val removed = acc.minusKey(element.key)
            if (removed === EmptyCoroutineContext) element else {
                // make sure interceptor is always last in the context (and thus is fast to get when present)
                val interceptor = removed[ContinuationInterceptor]
                if (interceptor == null) CombinedContext(removed, element) else {
                    val left = removed.minusKey(ContinuationInterceptor)
                    if (left === EmptyCoroutineContext) CombinedContext(element, interceptor) else
                        CombinedContext(CombinedContext(left, element), interceptor)
                }
            }
        }
```

CombinedContext 구조를 보면 거꾸로된? 함수형 컬렉션과 유사하다. 

```kotlin
internal class CombinedContext(
    private val left: CoroutineContext,
    private val element: Element
) : CoroutineContext, Serializable
```

left -> tail
element -> head

그래서 fold 구현도 조금 특이하다.

```kotlin
 public override fun <R> fold(initial: R, operation: (R, Element) -> R): R =
    operation(left.fold(initial, operation), element)
```

Set 자료구조와도 비슷한 부분이 있는데 동등성 동일성을 동시에 만족하기 위해 Hash를 사용하지 않고 Key 동반객체를 사용했다. 

```kotlin
// CombineContext
override fun <E : Element> get(key: Key<E>): E? {
    var cur = this
    while (true) {
        cur.element[key]?.let { return it }
        val next = cur.left
        if (next is CombinedContext) {
            cur = next
        } else {
            return next[key]
        }
    }
}

// Element
public override operator fun <E : Element> get(key: Key<E>): E? =
    @Suppress("UNCHECKED_CAST")
    if (this.key == key) this as E else null

```

컨텍스틑의 키는 object 이기 때문에 동일성, 동동성을 모두 만족한다.

```kotlin
val name = CoroutineName("MyCoroutine")
println(name.key === CoroutineName.Key)
println(name.key == CoroutineName.Key)
```

커스텀한 컨텍스트 사용하기

```kotlin
data class User(val payAccountId: Long) {
    override fun toString(): String = "User($payAccountId)"
}

data class CoroutineUser(
    val user: User
) : AbstractCoroutineContextElement(CoroutineUser) {

    companion object Key : CoroutineContext.Key<CoroutineUser>

    override fun toString(): String = "CoroutineUser($user)"
}

fun main() = runBlocking {
    val coroutineName = CoroutineName("myCoroutine")
    val coroutineUser = CoroutineUser(User(3010))
    val context = coroutineName + coroutineUser

    val job = launch(context) {
        val user = this.coroutineContext[CoroutineUser]
        println(user)
    }
}
```

뇌피셜~~

CoroutineContext 가 제공하는 대부분 함수들은 레퍼런스를 건들지 않고 새로운 객체를 리턴한다.

그래서 부모에서 자식으로 전파되는 컨텍스트에 새로운 컨텍스트를 결합했을 때 발생할 수 있는 부작용을 최소화 할 수 있다.


CoroutineContext 같은 API를 직접적으로 많이 사용하지 않더라도 필요한 이유는 

스레드를 넘나드는 논블러킹 비동기 라이브러리에서 ThreadLocal을 사용할 수 없기 때문이다.

e.g. 하이버네이트 트랜잭션, 스프링 시큐리티 컨텍스트 등등.. 
