package chapter11

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

var count = 0
val mutex = Mutex()

fun main() = runBlocking {
    val times = measureTimeMillis {
        withContext(Dispatchers.Default) {
            repeat(10_000) {
                launch {
                    mutex.withLock {
                        count += 1
                    }
                }
            }
        }
    }

    println("$count in $times ms")
}
