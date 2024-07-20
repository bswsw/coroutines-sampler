package chapter8

import kotlinx.coroutines.*

fun main() = runBlocking {
//    val deferred1 = async {
//        throw RuntimeException("예외 발생1")
//    }
//
//    try {
//        deferred1.await()
//    } catch (e: Exception) {
//        println(e.message)
//    }


val deferred2 = async {
    try {
        throw RuntimeException("예외 발생2")
    } catch (e: Exception) {
        println(e.message)
    }
}

    launch(CoroutineName("Coroutine2")) {
        delay(100L)
        println(" [${Thread.currentThread().name}] 코루틴 실행")
    }

    val result = deferred2.await()
}
