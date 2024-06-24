package chapter4

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val startTime = System.currentTimeMillis()
    val job = launch(start = CoroutineStart.LAZY) {
        myDelay(1000)
        println(elapsed(startTime))
    }

    println("before join")
//    job.start()
    job.join()
    println("after join")
}

fun elapsed(startTime: Long) =
    "elapsed: ${System.currentTimeMillis() - startTime}ms"


suspend fun myDelay(time: Long) {
    kotlinx.coroutines.delay(time)
}
