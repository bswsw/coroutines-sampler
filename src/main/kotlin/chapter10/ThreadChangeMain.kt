package chapter10

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val dispatcher = newFixedThreadPoolContext(2, "my-thread")

    val job = launch(dispatcher) {
        repeat(5) {
            println("[${Thread.currentThread().name}] hello launch1")
            delay(1000)
            println("[${Thread.currentThread().name}] hello launch2")
        }
    }
}
