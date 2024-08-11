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
