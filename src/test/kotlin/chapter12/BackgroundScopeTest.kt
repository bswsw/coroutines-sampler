package chapter12

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.time.Duration

class BackgroundScopeTest {

    @Test
    fun `메인스레드만 사용하는 runtest`() = runTest {
        println(Thread.currentThread())
    }

    @Test
    fun `끝나지 않아 실패하는 테스트`() = runTest(timeout = Duration.parse("PT5S")) {
        var result = 0

        launch {
            while (true) {
                delay(1000)
                result += 1
            }
        }

        advanceTimeBy(1500)
        assertEquals(1, result)

        advanceTimeBy(1000)
        assertEquals(2, result)
    }

    @Test
    fun `backgroundScope를 사용하는 테스트`() = runTest {
        var result = 0

        backgroundScope.launch {
            while (true) {
                delay(1000)
                result += 1
            }
        }

        advanceTimeBy(1500)
        assertEquals(1, result)
        println(result)

        advanceTimeBy(1000)
        assertEquals(2, result)
        println(result)
    }
}
