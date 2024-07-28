package chapter9

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()

    launch {
        delayAndPrintHelloWorld()
    }

    launch {
        delayAndPrintHelloWorld()
    }

    println("Time taken: ${System.currentTimeMillis() - startTime} ms")
}

suspend fun delayAndPrintHelloWorld() {
    delay(1000)
    println("Hello, World!")
}
