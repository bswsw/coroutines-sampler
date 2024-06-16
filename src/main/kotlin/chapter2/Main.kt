package org.example.chapter2

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    println("[${Thread.currentThread().name}] Hello Coroutines World!")

    launch(context = CoroutineName("launch-1")) {
        println("[${Thread.currentThread().name}] Hello Coroutine launch 1")
    }

    launch(context = CoroutineName("launch-2")) {
        println("[${Thread.currentThread().name}] Hello Coroutine launch 2")
    }
}
