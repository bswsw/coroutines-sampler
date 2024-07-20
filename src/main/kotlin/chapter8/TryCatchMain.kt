package chapter8

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
//    val job1 = launch {
//        try {
//            throw Exception("예외 발생 1")
//        } catch (e: Exception) {
//            println(e.message)
//        }
//    }

    try {
        val job1 = launch {
            throw Exception("예외 발생 1")
        }
    } catch (e: Exception) {
        println(e.message)
    }

    val job2 = launch {
        delay(100)
        println("job2 실행")
    }
}
