컬렉션 확장함수 `joinAll`이 있다.

```kotlin
public suspend fun Collection<Job>.joinAll(): Unit = forEach { it.join() }
```

lazy job을 join으로 실행하면 순차실행이 가능하다.

```kotlin
fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val job = launch(start = CoroutineStart.LAZY) {
        myDelay(1000)
        println(elapsed(startTime))
    }

    println("before join")
    job.start()
    println("after join")
}
```
```
before join
after join
elapsed: 1007ms
```

```kotlin
fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val job = launch(start = CoroutineStart.LAZY) {
        myDelay(1000)
        println(elapsed(startTime))
    }

    println("before join")
//    job.start()
    job.join()
    println("after join")
}
```
```
before join
elapsed: 1012ms
after join
```

반복문 코루틴 제어는 다른 언어에서도 비슷하게 지원한다.

https://dragontory.tistory.com/25


코루틴은 어떻게 취소될까?

```kotlin
// CancellableContinuationImpl.kt

public override fun cancel(cause: Throwable?): Boolean {
    _state.loop { state ->
        if (state !is NotCompleted) return false // false if already complete or cancelling
        
        // 취소할때 발생된 예외로 CancelledContinuation 생성.
        val update = CancelledContinuation(this, cause, handled = state is CancelHandler || state is Segment<*>)
        if (!_state.compareAndSet(state, update)) return@loop // retry on cas failure
        // Invoke cancel handler if it was present
        when (state) {
            is CancelHandler -> callCancelHandler(state, cause)
            is Segment<*> -> callSegmentOnCancellation(state, cause)
        }
        // Complete state update
        detachChildIfNonResuable()
        dispatchResume(resumeMode) // no need for additional cancellation checks
        return true
    }
}
```

Continuation Pattern (연속 패턴)

https://fastercapital.com/topics/types-of-continuation-patterns.html

https://fastercapital.com/ko/content/%EC%97%B0%EC%86%8D-%ED%8C%A8%ED%84%B4%EC%9D%84-%ED%86%B5%ED%95%9C-%EC%A0%88%EC%B0%A8%EC%A0%81-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%ED%99%9C%EC%84%B1%ED%99%94.html#%EC%97%B0%EC%86%8D-%ED%8C%A8%ED%84%B4-%EC%86%8C%EA%B0%9C

비동기 작업을 처리할때 필수적인 패턴. (Continuation patterns are an essential aspect of programming, especially in dealing with asynchronous operations.)

비동기 작업이 완료된 후 프로그램을 계속 실행하는데 사용됨.

callback: 함수가 콜백으로 비동기 작업에 전달됨. 작업이 완료되면 실행된다.

promise: 비동기 작업의 최종 완료 상태 (성공/실패)를 처리하는데 사용됨. 에러처리등을 효율적으로 할 수 있음.

async/await: 동기코드와 유사한 비동기코드 작성 가능. 기다리기 위해 wait 같은 키워드를 사용할 수 있음. 가독성이 뛰어남

generator pattern / observable pattern도 Continuation Pattern에 포함된다.
