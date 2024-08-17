package chapter12.stringstateholder

import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class StringStateHolderTest {

    @Test
    fun `문자열을 변경한다`() {
        // given
        val dispatcher = StandardTestDispatcher()
        val sut = StringStateHolder(dispatcher)

        // when
        sut.updateStringWithDelay("ABC")

        // then
        dispatcher.scheduler.advanceUntilIdle()
        assertEquals("ABC", sut.stringState)
    }
}
