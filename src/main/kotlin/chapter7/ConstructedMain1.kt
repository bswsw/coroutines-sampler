package chapter7

import kotlinx.coroutines.*

@OptIn(ExperimentalStdlibApi::class, ExperimentalCoroutinesApi::class)
fun main() = runBlocking {
    val context = newSingleThreadContext("MyThread") + CoroutineName("ParentCoroutine")

    println("부모 잡: ${coroutineContext[Job]?.parent}")

    println("========================================")

    val job = launch(context) {
        println("스레드: ${Thread.currentThread().name}")
        println("디스패처: ${coroutineContext[CoroutineDispatcher]}")
        println("잡: ${coroutineContext[Job]}")
        println("부모 잡: ${coroutineContext[Job]?.parent}")

        println("========================================")

        launch(CoroutineName("ChildCoroutine")) {
            println("스레드: ${Thread.currentThread().name}")
            println("디스패처: ${coroutineContext[CoroutineDispatcher]}")
            println("잡: ${coroutineContext[Job]}")
            println("부모 잡: ${coroutineContext[Job]?.parent}")

            println("========================================")
        }
    }

}
