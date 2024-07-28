package chapter9

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("before launch")

    launch {
        val result = doSomething()
        println(result)
    }

    println("after")
}

suspend fun doSomething() = supervisorScope {
    val job1 = async { doSomething1() }
    val job2 = async { doSomething2() }

    listOf(job1, job2).awaitAll()
}

suspend fun doSomething1(): String {
    delay(1000)
    return "doSomething1"
}

suspend fun doSomething2(): String {
    delay(1000)
    return "doSomething2"
}
