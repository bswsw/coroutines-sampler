package chapter4

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val job = launch(Dispatchers.IO) {
        println("I'm working in thread ${Thread.currentThread().name}")
    }

    println("=================================")
    Thread.sleep(1000)
}
