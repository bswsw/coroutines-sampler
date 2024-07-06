package chapter6

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val name = CoroutineName("MyCoroutine")
    val dispatcher = Dispatchers.IO
    val context = name + dispatcher

    println(context)

    val deleted = context.minusKey(CoroutineName)

    println(deleted)
}
