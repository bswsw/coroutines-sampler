package chapter11

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlin.system.measureTimeMillis

sealed class CounterMsg
object IncCounter : CounterMsg()
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()

fun CoroutineScope.counterActor() = actor<CounterMsg> {
    var count = 0
    for (msg in channel) {
        when (msg) {
            is IncCounter -> count++
            is GetCounter -> msg.response.complete(count)
        }
    }
}

fun main() = runBlocking<Unit> {
    val actor = counterActor()

    val times = measureTimeMillis {
        withContext(Dispatchers.Default) {
            repeat(10_000) {
                launch {
                    actor.send(IncCounter)
                }
            }
        }
    }

    val response = CompletableDeferred<Int>()
    actor.send(GetCounter(response))
    println("${atomicInt.get()} in $times ms")
    actor.close()
}
