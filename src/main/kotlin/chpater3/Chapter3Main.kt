package chpater3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    launch(singleThreadDispatcher) {
        println("[${Thread.currentThread().name}] ${System.currentTimeMillis()}")
        Thread.sleep(1000)
    }

    launch(singleThreadDispatcher) {
        println("[${Thread.currentThread().name}] ${System.currentTimeMillis()}")
        Thread.sleep(1000)
    }

    launch(multiThreadDispatcher) {
        println("[${Thread.currentThread().name}] ${System.currentTimeMillis()}")
        Thread.sleep(1000)
    }

    launch(multiThreadDispatcher) {
        println("[${Thread.currentThread().name}] ${System.currentTimeMillis()}")
        Thread.sleep(1000)
    }

    Dispatchers.IO
    Dispatchers.Default
    Dispatchers.Main
}
