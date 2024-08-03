package chapter10

fun main() {
    g.forEach {
        println(it)
    }
}

val g = sequence {
    println("one")
    yield(1)
    println("two")
    yield(2)
    println("three")
}
