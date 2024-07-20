# 예외 전파 제어

## Job

코루틴에 새로운 root job을 만들어서 구조활르 꺠는 방식으로 예외 전파를 막는다.

## SupervisorJob

자식 코루틴에서 발생한 예외를 부모 코루틴에게 전파되지 않지만 다른 자식 코루틴에게는 그대로 전파된다.
사용하려는 코루틴에 직접 주입해주어야한다.
이것 또한 Job이기 때문에 기본적으로는 구조화를 깨지기 때문에 상위 잡을 생성자에 주입해주면 구조화가 깨지지 않는다.

```kotlin
fun main() = runBlocking<Unit> {
    val supervisor = SupervisorJob(coroutineContext.job)
    launch(CoroutineName("co1") + supervisor) {
        launch(CoroutineName("co3")) {
            throw Exception("Exception in co3")
        }

        launch(CoroutineName("co4")) {
            delay(100)
            println(Thread.currentThread().name)
        }

        delay(100)
        println(Thread.currentThread().name)
    }

    launch(CoroutineName("co2") + supervisor) {
        delay(100)
        println(Thread.currentThread().name)
    }

    supervisor.complete()
}

```

흔히하는 실수~

```kotlin
fun main() = runBlocking<Unit> {
    launch(CoroutineName("Parent Coroutine") + SupervisorJob()) {
        launch(ConoutineName("Coroutine1")) {
            launch(CoroutineName("Coroutine3")) {
                throw Exception("예외 발생")
            }
            delay(100L)
            prin tln ("[${Thread.currentThread().name}] 코루틴 실행")
        }
        launch(CoroutineName("Coroutine2")) {
            delay(100L)
            println(" [${Thread.currentThread().name}] 코루틴 실행")
            delay(1000L)
        }
    }
}
```

## SupervisorScope

SupervisorJob를 CoroutineScope에 결합한 스타일과 비슷하게 사용할 수 있다.


# 예외 처리

# CoroutineExceptionHandler

예외를 글로벌하게 처리할 수 있는 핸들러이다.

예외가 부모 코루틴으로 전파된다면 예외가 처리된 것으로 보기 때문에 자식 코루틴에 설정된 핸들러는 동작하지 않는다.

```kotlin
fun main() = runBlocking<Unit> {
    val context = CoroutineExceptionHandler { ctx, ex ->
        println("예외발생: ${ex}")
    }

    launch(context) {
        throw Exception("코루틴 예외 발생2")
    }

    delay(1000)
}
```
예외처리와 예외전파는 별개!

# try-catch

코루틴 내부 로직에서 try-catch로 에외가 처리되고 있다면 예외가 전파되지 않는다.

하지만 코루틴 빌더 함수를 try-catch로 감싸더라도 예외는 전파된다.

```kotlin
try {
    val job1 = launch {
        throw Exception("예외 발생 1")
    }
} catch (e: Exception) {
    println(e.message)
}
```
예외가 던져지는 곳과 예외를 처리하는 곳의 스레드가 다르기 때문이다.


# async try-catch

await 호출을 try-catch로 감싸서 예외를 처리할 수 있다.

await는 리턴값을 받을 뿐 async 람다가 호출된 시점에 로직은 실행되기 때문에 예외는 전파될 수 있다. 

supervisorScope와 함께 사용하여 예외 전파를 막을 수 있다.

그런데 그냥 async 내부 로직에서 잡아도 잘 처리된다.

```kotlin
val deferred2 = async {
    try {
        throw RuntimeException("예외 발생2")
    } catch (e: Exception) {
        println(e.message)
    }

}
```

# 전파되지 않는 예외

## CancellationException

현재 코루틴을 취소 시킬 뿐 부모 코루틴으로 예외가 전파되지 않는다.

상속해서 사용할 수도 있다.

```kotlin
object MyException : CancellationException("MyException")

fun main() = runBlocking<Unit> {
    val job1 = launch {
        throw MyException
    }

    job1.invokeOnCompletion { exception ->
        println(exception) // 발생한 예외 출력
    }

    val job2 = launch {
        delay(100)
        println("job2")
    }
}

```

## JobCancellationException

job.cancel() 로 취소된 코루틴은 JobCancellationException이 발생한다.

CancellationException를 상속한 클래스이다.

## withTimeout

withTimeout은 TimeoutCancellationException을 발생시킨다.

CancellationException를 상속한 클래스이다.
