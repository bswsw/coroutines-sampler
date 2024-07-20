package chapter8

import kotlinx.coroutines.*

fun main() = runBlocking<Unit> {
//    val supervisor = SupervisorJob(coroutineContext.job)
//    launch(CoroutineName("co1") + supervisor) {
//        launch(CoroutineName("co3")) {
//            throw Exception("Exception in co3")
//        }
//
//        launch(CoroutineName("co4")) {
//            delay(100)
//            println(Thread.currentThread().name)
//        }
//
//        delay(100)
//        println(Thread.currentThread().name)
//    }
//
//    launch(CoroutineName("co2") + supervisor) {
//        delay(100)
//        println(Thread.currentThread().name)
//    }
//
//    supervisor.complete()


    supervisorScope {
        launch(CoroutineName("co1")) {
            launch(CoroutineName("co3")) {
                throw Exception("Exception in co3")
            }

            launch(CoroutineName("co4")) {
                delay(100)
                println(Thread.currentThread().name)
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
