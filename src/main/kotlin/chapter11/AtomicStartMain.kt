package chapter11

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {
    val job = launch(start = CoroutineStart.ATOMIC) {
        println("작업1")
    }

    job.cancel()
    println("작업2")
}
