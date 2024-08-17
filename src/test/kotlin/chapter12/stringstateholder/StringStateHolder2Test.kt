package chapter12.stringstateholder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class StringStateHolder2Test {

    @Test
    fun `문자열을 변경한다`() = runTest {
        // given
        val sut = StringStateHolder2()

        // when
        launch {
            sut.updateStringWithDelay("ABC")
        }

        // then
        advanceUntilIdle()
        assertEquals("ABC", sut.stringState)
    }
}
