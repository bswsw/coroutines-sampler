package chapter11

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

var singleCount = 0
val singleContext = newSingleThreadContext("SingleThreadContext")

fun main() = runBlocking {
    val times = measureTimeMillis {
        withContext(Dispatchers.Default) {
            repeat(10_000) {
                launch {
                    withContext(singleContext)  {
                        singleCount += 1
                    }
                }
            }
        }
    }

    println("$singleCount in $times ms")
}
