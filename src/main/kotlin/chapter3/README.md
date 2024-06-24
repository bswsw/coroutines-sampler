# CoroutineDispatcher

## CoroutineDispatcher란?

Coroutine: 일시 중단이 가능한 작업
CoroutineDispatcher: Coroutine을 스레드로 보내 실행시키는 역할

대기열에 적재되지 않고 즉시 실행하는 코루틴 존재
대기열이 없는 CoroutineDispatcher 구현체 존재


## 제한된 Dispatcher, 무제한 Dispatcher

사용할 수 있는 스레드나 스레드풀의 제한 여부에 따라 분류됨

### 제한된 Dispatcher

CoroutineDispatcher 객체별로 어떤 작업을 처리할지 미리 역하을 부여하고 역할에 맞춰 실행을 요청하는 것이 효율적

e.g. IO 작업을 위한 Dispatcher, CPU 연산을 위한 Dispatcher 등등..

### 무제한 Dispatcher

실행요청된 코루틴이 이전 코드가 실행되던 스레드에서 계속 실행되도록 하여 스레드가 매번 달라질 수 있음.
₩

### 미리 정의된 Dispatcher

#### Dispatchers.IO: IO



#### Dispatchers.Default

```kotlin
private val default = UnlimitedIoScheduler.limitedParallelism(
        systemProp(
            IO_PARALLELISM_PROPERTY_NAME,
            64.coerceAtLeast(AVAILABLE_PROCESSORS)
        )
    )
```

#### Dispatchers.Main

ui가 있는 애플리케이션의 메인스레드 사용을 위함. 특정 의존성을 추가해야 사용할 수 있음. 


## 코루틴 디버깅 하기

https://www.jetbrains.com/help/idea/debug-kotlin-coroutines.html
