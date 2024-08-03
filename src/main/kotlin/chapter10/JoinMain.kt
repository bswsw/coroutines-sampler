package chapter10

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val job = launch {
        println("1. start")
        delay(1000L)
        println("2. complete")
    }

    println("3. suspend and yield")
    job.join()
    println("4. resume")
}
