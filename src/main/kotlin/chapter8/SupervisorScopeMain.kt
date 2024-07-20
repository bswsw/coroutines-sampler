package chapter8

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
    supervisorScope {
        launch(CoroutineName("co1")) {
            launch(CoroutineName("co3")) {
                throw Exception("Exception in co3")
            }

            delay(100)
            println(Thread.currentThread().name)
        }

        launch(CoroutineName("co2")) {
            delay(100)
            println(Thread.currentThread().name)
        }
    }
}
