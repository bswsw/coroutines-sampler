package chapter4

import kotlinx.coroutines.*

fun main() = runBlocking {
    val job: Job = launch {
        delay(1000)
        println("어쩌구 저쩌구")
    }

    job.isCompleted

    job.ensureActive()
    println("취소전: $job")
    job.cancelAndJoin()
    println("취소후: $job")
    job.ensureActive()
}

fun check(job: Job) = when (Triple(job.isActive, job.isCancelled, job.isCompleted)) {
    Triple(false, false, false) -> "new"
    Triple(true, false, false) -> "active"
    Triple(false, false, true) -> "completed"
    Triple(false, true, false) -> "cancelling"
    Triple(false, true, true) -> "cancelled"
    else -> "unknown"
}

suspend fun myFunction() {
    println("before")
    delay(1000)
    println("after")
}
