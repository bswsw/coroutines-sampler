package chapter12

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class RepeatAddUseCaseTest {

    @Test
    fun `100번 더하면 100이 된다`() = runBlocking {
        val sut = RepeatAddUseCase()

        val result = sut.add(100)

        assertEquals(100, result)
    }
}
