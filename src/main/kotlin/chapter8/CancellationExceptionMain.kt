package chapter8

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.cancellation.CancellationException

object MyException : CancellationException("MyException")

fun main() = runBlocking<Unit> {
    val job1 = launch {
        throw MyException
    }

    job1.invokeOnCompletion { exception ->
        println(exception) // 발생한 예외 출력
    }

    val job2 = launch {
        delay(100)
        println("job2")
    }
}
