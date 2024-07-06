package chapter6

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

fun main() = runBlocking {
    val coroutineName = CoroutineName("mycoroutine")
    val coroutineUser = CoroutineUser(User(3010))
    val coroutineJob = Job()
    val context = coroutineName + coroutineUser + coroutineJob

    context.get(CoroutineName)

//    val job = launch(context) {
//        val user = this.coroutineContext[CoroutineUser]
//        println(user)
//    }

    println(context)

//    val job = launch(EmptyCoroutineContext) {
//        println("${Thread.currentThread().name}")
//    }
}
