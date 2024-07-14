package chapter7

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    val parentJob = Job()

    launch(parentJob) {
        println(coroutineContext.job.parent)

        val childJob = Job()
        launch {
            delay(1000)
            println(coroutineContext.job == childJob) // ??
            println(coroutineContext.job.parent == parentJob) // ??
        }
    }

    delay(50)
//    parentJob.cancel()
    delay(2000)
}
