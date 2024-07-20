package chapter8

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val handler = CoroutineExceptionHandler { ctx, ex ->
        println("예외발생: ${ex}")
    }

    CoroutineScope(handler).launch {
        throw Exception("코루틴 예외 발생1")
    }
}
