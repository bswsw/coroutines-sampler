package chapter12

import kotlinx.coroutines.delay

class RepeatAddWithDelayUseCase {

    suspend fun add(repeatTimes: Int): Int {
        var result = 0
        repeat(repeatTimes) {
            delay(100L)
            result += 1
        }
        return result
    }
}
