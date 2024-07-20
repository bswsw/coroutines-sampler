package chapter5

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.*

private val logger = KotlinLogging.logger {  }

fun main() = runBlocking {
    val deferred1 = async(Dispatchers.IO) {
        delay(1000)
        logger.info { "delay" }
        "deferred1"
    }

    val deferred2 = async(Dispatchers.IO) {
        delay(1000)
        logger.info { "delay" }
        "deferred2"
    }

    val job = launch {
        delay(1000)
        logger.info { "launch" }
    }

//    val result = listOf(deferred1, deferred2).awaitAll()
//    job.join()
//
//    logger.info { result }

    delay(10000)
}
