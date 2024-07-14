package chapter7

import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    println("Hello, ${Thread.currentThread().name}")

    launch {
        println("World, ${Thread.currentThread().name}")

        launch {
            delay(1000)
            println("Nested, ${Thread.currentThread().name}")
        }

        launch {
            delay(1000)
            println("Nested2, ${Thread.currentThread().name}")
        }

        this.cancel()
    }

    println("Hello2, ${Thread.currentThread().name}")
}
