package chapter5

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.*

private val logger = KotlinLogging.logger {  }

fun main() = runBlocking {

    val startTime = System.currentTimeMillis()

    val deferred1 = async(Dispatchers.IO) deferred1@{
        delay(1000)

        val list = listOf("James", "Jason")
        logger.info { list }
        return@deferred1 list
    }

    val deferred2 = async(Dispatchers.IO) deferred2@{
        delay(1000)

        val list = listOf("Jenny")
        logger.info { list }
        return@deferred2 list
    }

    val a = awaitAll(deferred1, deferred2)
//    val d1 = deferred1.await()
//    val d2 = deferred2.await()

    logger.info { a }
}
