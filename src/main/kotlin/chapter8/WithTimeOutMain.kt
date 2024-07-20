package chapter8

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = launch(CoroutineName("Child Coroutine")) {
        val result = withTimeoutOrNull(1000L) { // 실행 시간을 1 초로 제한
            delay(2000L) // 2 초의 시간이 걸리는 작업
        }

        throw CancellationException()

        TimeoutCancellationException("TimeoutCancellationException")
    }
}
