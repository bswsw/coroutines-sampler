package chapter7

import kotlinx.coroutines.*

fun main() {
    CoroutineScope(Dispatchers.IO).launch {
        println("Hello, ${Thread.currentThread().name}")
    }

    Thread.sleep(100)
}
