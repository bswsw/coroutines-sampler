package chapter5

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking<Unit> {
    GlobalScope.launch {
        delay(1000L)
        println("[${Thread.currentThread().name}] CoroutineScope.launch")
    }
}
