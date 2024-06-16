package chpater3

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking<Unit> {
    val defaultDispatcher = Dispatchers.Default
    val ioDispatcher = Dispatchers.IO
    val dispatcher = defaultDispatcher.limitedParallelism(2)

    launch(dispatcher) {
        println("[${Thread.currentThread().name}] 부모 코루틴")

        launch {
            println("[${Thread.currentThread().name}] 자식 코루틴 1")
        }

        launch {
            println("[${Thread.currentThread().name}] 자식 코루틴 2")
        }
    }
}
