package chapter6

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

fun main() = runBlocking<Unit> {
    val name = CoroutineName("MyCoroutine")
    val dispatcher = Dispatchers.IO
    val context = name + dispatcher

    println(context[CoroutineName] == context[name.key])
    println(name.key === CoroutineName.Key)
    println(name.key == CoroutineName.Key)
}
