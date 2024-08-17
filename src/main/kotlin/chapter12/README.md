## 단위 테스트

### 단위

명확한 정의된 역할의 범위를 갖는 코드 집합

### 단위 테스트

단위에 대한 자동화된 테스트를 작성하고 실행하는 프로세스
소프트웨어의 특정 기능이 제대로 동작하는지 확인하는 테스트를 작성하는것


## 테스트 더블

의존성을 가지 객체를 테스트하기 위해 필요한 것
객체에 대한 대체물을 뜻하고 객체의 행동을 모방하는 객체를 만드는데 사용

### 더미

인스턴스화된 객체가 필요하지만 기능은 필요하지 않을 때 사용

### 스텁

미리 정의된 데이터를 반환하는 모방 객체로 반환값이 없는 동작은 구현하지 않으며 
반환값이 있는 동작만 미리 정의된 데이터를 반환하도록 구현

### 페이크

실제 객체와 비슷하게 동작하도록 구현된 모방 객체
반환값이 없는 동작이라도 비슷하게 동작하도록 구현

### 스파이

스텁과 유사하지만 호출되었을 때 특정 정보를 기록하는 객체

### 목

테스트 대상이 되는 호출에 대해 명세하고 그에 따라 동작하도록 구현된 객체


## 코루틴 테스트 라이브러리

### TestCoroutineScheduler

가상시간 기반의 테스트 스케줄러

advancedTimeBy : 가상 시간을 흐르게 만든다.

advancedUntilIdle : 모든 코루틴이 완료될 때까지 가상 시간을 흐르게 만든다.

### TestScope

TestDispacher와 TestCoroutineScheduler를 포함하면서 추가적인 기능을 제공.

### runTest

runTest > TestScope > StandardTestDispatcher > TestCoroutineScheduler

```kotlin
public fun runTest(
    context: CoroutineContext = EmptyCoroutineContext,
    timeout: Duration = DEFAULT_TIMEOUT.getOrThrow(),
    testBody: suspend TestScope.() -> Unit
): TestResult {
    check(context[RunningInRunTest] == null) {
        "Calls to `runTest` can't be nested. Please read the docs on `TestResult` for details."
    }
    return TestScope(context + RunningInRunTest).runTest(timeout, testBody)
}
```
```kotlin
private val DEFAULT_TIMEOUT: Result<Duration> = runCatching {
    systemProperty("kotlinx.coroutines.test.default_timeout", Duration::parse, 60.seconds)
}
```


### StringStateHolder

이렇게 하면 잘못 리팩토링된 코드일까?

AS-IS
```kotlin
class StringStateHolder(dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    private val scope = CoroutineScope(dispatcher)

    var stringState = ""
        private set

    fun updateStringWithDelay(newString: String) {
        scope.launch {
            delay(1000)
            stringState = newString
        }
    }
}
```

TO-BE
```kotlin
class StringStateHolder {

    var stringState = ""
        private set

    suspend fun updateStringWithDelay(newString: String) = coroutineScope {
        launch {
            delay(1000)
            stringState = newString
        }
    }
}
```

Test
```kotlin
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
```

예제 코드를 보면 생성자로 dispatcher를 받는다는 것 자체가 클라이언트 코드에서 dispatcher를 주입할 수 있게 하였는데 (어디서 많이 봤다 했더니 홍쓰의 그 코드...)
클래스에서 dispatcher를 받기 때문에 재사용성이 떨어져보인다.
함수 내부에서 delay를 사용하고 있기 때문에 suspend 함수로 선언해도 될 것 같고 coroutineScope를 사용하면 함수의 재사용성이 조금 더 높아질 것 같다.
그리고 테스트 코드도 훨씬 간결해지는 것으로 보인다.
근데 제대로 리팩토링한게 맞을까?


### backgroundScope

runTest 코루틴의 모든 코드가 실행되면 자동으로 취소되며 이를 통해 테스트가 무한히 실행되는 것을 방지할 수 있다.

```kotlin
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
```
