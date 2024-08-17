package chapter12

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import kotlinx.coroutines.test.TestCoroutineScheduler
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineScheduler {

    @Test
    fun `가상 시간 조절 테스트`() {
        val scheduler = TestCoroutineScheduler()

        scheduler.advanceTimeBy(5000L)
        assertEquals(5000L, scheduler.currentTime)

        scheduler.advanceTimeBy(6000L)
        assertEquals(11000L, scheduler.currentTime)

        scheduler.advanceTimeBy(10000L)
        assertEquals(21000L, scheduler.currentTime)
    }

    @Test
    fun `가상 시간 위에서 테스트 진행`() {
        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        val scope = CoroutineScope(dispatcher)

        // given
        var result = 0

        // when
        scope.launch {
            delay(10000)
            result = 1

            delay(10000)
            result = 2

            println(Thread.currentThread().name)
        }

        // then
        assertEquals(0, result)

        scheduler.advanceTimeBy(5000)
        assertEquals(0, result)

        scheduler.advanceTimeBy(6000)
        assertEquals(1, result)

        scheduler.advanceTimeBy(10000)
        assertEquals(2, result)
    }

    @Test
    fun advanceUntilIdle() {
        val scheduler = TestCoroutineScheduler()
        val dispatcher = StandardTestDispatcher(scheduler)
        val scope = CoroutineScope(dispatcher)

        // given
        var result = 0

        // when
        scope.launch {
            delay(10000)
            result = 1

            delay(10000)
            result = 2

            println(Thread.currentThread().name)
        }

        // then
        assertEquals(0, result)

        scheduler.advanceUntilIdle()
        assertEquals(2, result)
    }

    @Test
    fun testScope() {
        val scope = TestScope()

        // given
        var result = 0

        // when
        scope.launch {
            delay(10000)
            result = 1

            delay(10000)
            result = 2

            println(Thread.currentThread().name)
        }

        // then
        assertEquals(0, result)

        scope.advanceUntilIdle()
        assertEquals(2, result)
    }

    @Test
    fun `runTest 사용하기`() {
        // given
        var result = 0

        // when
        runTest {
            delay(10000)
            result = 1

            delay(10000)
            result = 2

            println(Thread.currentThread().name)
        }

        assertEquals(2, result)
    }

    @Test
    fun `runTest로 감싸기`() = runTest {
        // given
        var result = 0

        // when
        delay(10000)
        result = 1

        delay(10000)
        result = 2

        println(Thread.currentThread().name)

        // then
        assertEquals(2, result)
    }

    @Test
    fun `runTest에서 가상시간 확인`() = runTest {
        delay(10000)
        println("현재 시간: $currentTime")

        delay(10000)
        println("현재 시간: $currentTime")
    }

    @Test
    fun `runTest 내부에서 advanceUntilIdle 사용`() = runTest {
        var result = 0

        launch { // 시간이 자동으로 흐르지 않는다.
            delay(10000)
            result = 1
        }
        println("시간: $currentTime / 결과: $result")

        advanceUntilIdle()
        println("시간: $currentTime / 결과: $result")
    }

    @Test
    fun `runTest 내부에서 join 사용`() = runTest {
        var result = 0

        launch { // 시간이 자동으로 흐르지 않는다.
            delay(10000)
            result = 1
        }.join()

        println("시간: $currentTime / 결과: $result")
    }
}
