package common

class Greeting {
    fun hello() = println("Hello, World!")
    fun hi() = println("Hi, World!")
}

fun greeting(block: Greeting.() -> Unit) {
    Greeting().block()
}

class Greeting2(val name: String) {
    fun hello() = println("Hello, $name!")
    fun hi() = println("Hi, $name!")
}

fun greeting2(name: String, block: Greeting2.() -> Unit) {
    Greeting2(name).block()
}

fun main() {
    greeting {
        hello()
        hi()
    }

    greeting2("Kotlin") {
        hello()
        hi()
    }
}
