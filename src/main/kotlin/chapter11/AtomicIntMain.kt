package chapter11

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

val atomicInt = AtomicInteger(0)

fun main() = runBlocking {
    val times = measureTimeMillis {
        withContext(Dispatchers.Default) {
            repeat(10_000) {
                launch {
                    atomicInt.incrementAndGet()
                }
            }
        }
    }

    println("${atomicInt.get()} in $times ms")
}
