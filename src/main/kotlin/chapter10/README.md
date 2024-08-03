## 서브루틴과 코루틴의 차이

https://ko.wikipedia.org/wiki/%EC%BD%94%EB%A3%A8%ED%8B%B4

https://en.wikipedia.org/wiki/Coroutine

서브루틴은 호출자와 호출 대상이 서로 동기적으로 동작하지만, 코루틴은 호출자와 호출 대상이 비동기적으로 동작한다. 
코루틴은 호출자와 호출 대상이 서로 상태를 공유하면서 동작하기 때문에, 호출자가 호출 대상의 상태를 변경하거나 호출 대상이 호출자에게 상태를 전달할 수 있다.


## 코루틴의 스레드 양보

스레드를 양보하는 주체는 코루틴이다.
스레드에 코루틴을 할당해 실행되도록 만드는 주체는 CoroutineDispatcher 객체이지만
스레드를 양보하는 주체는 코루티능로 CoroutineDispatcher는 코루틴이 스레드를 양보하도록 강제하지 못한다.


```kotlin
fun main() = runBlocking {
    val job = launch {
        println("1. start")
        delay(1000L)
        println("2. complete")
    }
    
    println("3. suspend and yield")
    job.join()
    println("4. resume")
}
```

```kotlin
fun main() = runBlocking {
    val job = launch {
        while (isActive)  {
            println("hello launch")
            yield()
        }
    }

    delay(100L)
    job.cancel()
}
```

```kotlin
fun main() {
    g.forEach {
        println(it)
    }
}

val g = sequence {
    println("one")
    yield(1)
    println("two")
    yield(2)
    println("three")
}
```


## 코루틴의 실행 스레드

코루틴의 스레드는 CoroutineDispatcher가 사용하는 스레드 풀 안에서 사용할 수 있는 스레드 중 하나를 할당한다.

코루틴의 실행 스레드가 바뀌는 시점은 재개 시점뿐이다.

양보자히지 않으면 실행 스레드가 바뀌지 않는다. 

```kotlin
fun main() = runBlocking {
    val dispatcher = newFixedThreadPoolContext(2, "my-thread")

    val job = launch(dispatcher) {
        repeat(5) {
            println("[${Thread.currentThread().name}] hello launch1") // 실행 스레드 출력 
            delay(1000) // 일시 중단
            println("[${Thread.currentThread().name}] hello launch2") // 재개되어 실행 스레드 출력
        }
    }
}
```
