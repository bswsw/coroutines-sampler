package chapter5

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job = GlobalScope.launch {
        println("coroutine started")

        // 예외 발생
        throw RuntimeException("예외발생")
    }

    delay(1000)

    try {
        job.join()
    } catch (e: Exception) {
        println("coroutine exception: ${e.message}")
    }

    println("coroutine finished")
}
