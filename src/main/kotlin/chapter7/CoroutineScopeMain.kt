package chapter7

import kotlinx.coroutines.*

@OptIn(ExperimentalStdlibApi::class)
suspend fun main() = coroutineScope {
    println("Hello, ${Thread.currentThread().name}")
    println("부모 잡: ${coroutineContext[Job]?.parent}")

    println("===========================================")

    val job = launch {
        println("World, ${Thread.currentThread().name}")
        println("부모 잡: ${coroutineContext[Job]?.parent}")

        println("===========================================")

        val data = async {
            "World, ${Thread.currentThread().name}"

            listOf(
                "디스패처: ${coroutineContext[CoroutineDispatcher.Key]}",
                "잡: ${coroutineContext[Job]}",
                "부모 잡: ${coroutineContext[Job]?.parent}"
            )
        }.await()

        println(data.joinToString("\n"))
    }
}
