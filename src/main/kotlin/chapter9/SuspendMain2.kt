package chapter9

import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun main() = runBlocking {
    println("before launch")

    suspendCoroutine<Unit> {
        it.resume(Unit)
    }

    suspendCoroutine<Unit> {
        it.resumeWithException(RuntimeException("error"))
    }

    val str = suspendCoroutine {
        it.resume("hello-world")
    }

    println(str)

    println("after")
}
