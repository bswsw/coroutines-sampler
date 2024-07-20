package chapter8

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main() = runBlocking {
    val job1 = supervisorScope {
        launch {
            launch {
                delay(100)
                println("1")
            }

            launch { throw RuntimeException("예외") }
        }

        launch {
            launch {
                delay(100)
                println(2)
            }
        }
    }
}
