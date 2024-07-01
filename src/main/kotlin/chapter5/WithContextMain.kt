package chapter5

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

private val logger = KotlinLogging.logger {  }

fun main() = runBlocking {
    val result = withContext(Dispatchers.IO) {
        delay(1000)
        logger.info { "delay" }
        "Dummy Response"
    }

    logger.info { result }
}
