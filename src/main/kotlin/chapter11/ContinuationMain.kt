package chapter11

import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun main() = runBlocking {
    val handler = CoroutineExceptionHandler { ctx, ex ->
        println("예외발생: ${ex}")
    }

    val job = launch(Job() + handler) {
        println("before launch")

        suspendCancellableCoroutine<Unit> {
            it.resume(Unit)
        }

        try {
            suspendCancellableCoroutine<Unit> {
                it.resumeWithException(RuntimeException("error"))
            }
        } catch (e: Exception) {
            println("[catch] ${e.message}")
        }

        suspendCancellableCoroutine<Unit> {
            it.resumeWithException(RuntimeException("error"))
        }

        val str = suspendCancellableCoroutine {
            it.resume("hello-world")
        }
        println(str)

        println("after")
    }
}
