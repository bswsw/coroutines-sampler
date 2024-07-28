package chapter9

import kotlinx.coroutines.delay

suspend fun doSomethingSuspend(param: String): String {
    delay(1000)
    return "doSomethingSuspend: ${param}"
}
