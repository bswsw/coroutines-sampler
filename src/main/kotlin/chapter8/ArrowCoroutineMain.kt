package chapter8

import arrow.core.raise.either
import arrow.fx.coroutines.parZip
import kotlinx.coroutines.*

fun main() = runBlocking {
    supervisorScope {
        val job = launch {
            parZip(
                { either<Throwable, String> { getUserName(1) } },
                { either<Throwable, String> { getUserName(2) } },
                {
                    either<Throwable, String> {
                        withTimeout(1000) {
                            delay(1000)
                            "hello"
                        }
                    }
                },
                { either<Throwable, String> { toError() } }
            ) { a, b, _, _ -> println(Pair(a, b)) }
        }
    }
}

suspend fun getUserName(id: Long): String {
    delay(2000 - (id * 1000))
    println("[${Thread.currentThread().name}] ${id}")
    return "name${id}"
}

suspend fun toError(): Nothing = throw Exception("error")
