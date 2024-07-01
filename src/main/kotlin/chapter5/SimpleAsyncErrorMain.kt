package chapter5

import kotlinx.coroutines.*

fun main() = runBlocking {
    try {
        val deferred = async {
            println("Async coroutine started")
            throw RuntimeException("Exception in async coroutine")
            "Async coroutine result" // 예외 발생 시 이 코드는 실행되지 않음
        }

        // await()을 호출하여 결과를 받으며, 동시에 예외가 전파됨
        val result = deferred.await()
        println("Async coroutine result: $result") // 실행되지 않음

    } catch (e: Exception) {
        println("Caught exception in main coroutine: ${e.message}")
    }

    println("Main coroutine finished")
}
