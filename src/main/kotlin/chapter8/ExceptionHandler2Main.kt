package chapter8

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext,
                                                       throwable ->
        println("[예외 발생] ${throwable}")
    }
    launch(CoroutineName("Coroutine1") + exceptionHandler) {
        throw Exception("Coroutine2에 예외가 발생했습니다")
    }

    delay(1000)
}
