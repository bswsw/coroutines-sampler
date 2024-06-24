package chapter4

import kotlinx.coroutines.delay

suspend fun myFunction() {
    println("before")
    var counter = 0
    delay(1000)
    counter++
    println("counter = $counter")
    println("after")
}
