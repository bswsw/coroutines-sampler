package chapter6

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

fun main() = runBlocking<Unit> {
    val dispatcher = newSingleThreadContext("MyThread")
    val name = CoroutineName("MyCoroutine")

    val context = dispatcher + name
    val newName = CoroutineName("NewCoroutine")
    val newContext: CoroutineContext = context + newName

    launch(context) {
        println("Hello, ${Thread.currentThread().name} : ${context[newName.key]}")
    }
}
