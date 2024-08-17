package chapter12

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RepeatAddWithDelayUseCaseTest {

    @Test
    fun `100번 더하면 100이 반환된다`() {
        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)

        // given
        val sut = RepeatAddWithDelayUseCase()

        // when
        var result = 0
        CoroutineScope(dispatcher).launch {
            result = sut.add(100)
            assertEquals(100, result)
        }

        scheduler.advanceUntilIdle()
        assertEquals(100, result)
    }
}
