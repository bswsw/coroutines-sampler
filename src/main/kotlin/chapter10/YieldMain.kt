package chapter10

import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine

fun main() = runBlocking {
    val job = launch {
        while (isActive)  {
            println("hello launch")
            yield()
        }
    }

    delay(100L)
    job.cancel()
}
