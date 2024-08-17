package chapter12

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class AddUseCaseTest {

    @Test
    fun `1 더하기 2는 3이다`() {
        val addUseCase = AddUseCase()
        val result = addUseCase.add(1, 2)
        assertEquals(3, result)
    }
}
