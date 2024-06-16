package test

import kotlinx.coroutines.*
import kotlin.test.Test

class TestRuleExample {

    private suspend fun someFunctionDeepInTheStack() {
        withContext(Dispatchers.IO) {
            delay(Long.MAX_VALUE)
        }
    }

    @Test
    fun handlingTest() = runBlocking {
        val job = launch {
            someFunctionDeepInTheStack()
        }

        job.join()
    }

}
