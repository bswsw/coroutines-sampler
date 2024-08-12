## 공유 상태를 사용하는 코루틴의 문제와 데이터 동기화

### AtomicInteger 사용하기

```kotlin
val atomicInt = AtomicInteger(0)

fun main() = runBlocking {
    val times = measureTimeMillis {
        withContext(Dispatchers.Default) {
            repeat(10_000) {
                launch {
                    atomicInt.incrementAndGet()
                }
            }
        }
    }

    println("${atomicInt.get()} in $times ms")
}
```

### Actor 사용하기

```kotlin
sealed class CounterMsg
object IncCounter : CounterMsg()
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()

fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var count = 0
    for (msg in channel) {
        when (msg) {
            is IncCounter -> count++
            is GetCounter -> msg.response.complete(count)
        }
    }
}

fun main() = runBlocking<Unit> {
    val actor = counterActor()

    val times = measureTimeMillis {
        withContext(Dispatchers.Default) {
            repeat(10_000) {
                launch {
                    actor.send(IncCounter)
                }
            }
        }
    }

    val response = CompletableDeferred<Int>()
    actor.send(GetCounter(response))
    println("${atomicInt.get()} in $times ms")
    actor.close()
}

```

속도 비교: AtomicInteger < Mutex < Actor < 싱글스레드풀


## CoroutineStart.UNDISPATCHED vs Dispatchers.Unconfined

실행: 둘다 코루틴 빌더를 호출한 스레드에서 실행된다.
재개
- CoroutineStart.UNDISPATCHED: 코루틴이 실행된 스레드에서 재개된다.
- Dispatchers.Unconfined: 자신을 재개시킨 스레드에서 동작한다.

무제한 디스패처는 재개되는 스레드를 예측하기 어렵기 대문에 비동기 작업이 불안해질 수 있다.


### Continuation 

```kotlin
fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { ctx, ex ->
        println("예외발생: ${ex}")
    }

    val job = launch(Job() + handler) {
        println("before launch")

        suspendCancellableCoroutine<Unit> {
            it.resume(Unit)
        }

        try {
            suspendCancellableCoroutine<Unit> {
                it.resumeWithException(RuntimeException("error"))
            }
        } catch (e: Exception) {
            println("[catch] ${e.message}")
        }

        suspendCancellableCoroutine<Unit> {
            it.resumeWithException(RuntimeException("error"))
        }

        val str = suspendCancellableCoroutine {
            it.resume("hello-world")
        }
        println(str)

        println("after")
    }
}
```
