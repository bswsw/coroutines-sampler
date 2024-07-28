일시 중단 함수는 코루틴이 아니다.

일시 중단 함수는 코루틴 내부, 일시 중단 함수 내부에서 사용할 수 있다.


### 일시 중단 함수 내에서 코루틴 실행하기

coroutineScope를 사용하면 구조화를 깨지 않는 CoroutineScope 객체를 만든다.

이 때 구조화가 깨지지는 않지만 실행 되는 코루틴이 많이 엮여 있으면 예외가 상위로 전파되고 취소가 하위로 전달될 수 있다.

이 때 supervisorScope를 사용하면 이를 방지할 수 있다.


### 일시 중단 함수 조금 더 살펴보기

일시 중단 함수는 값 또는 COROUTINE_SUSPEND 를 리턴한다.

```kotlin
suspend fun doSomethingSuspend(param: String): String {
    delay(1000)
    return "doSomethingSuspend: ${param}"
}
```

```java
public static final Object doSomethingSuspend(@NotNull String param, @NotNull Continuation $completion)
```

```java
Object $result = ((<undefinedtype>)$continuation).result;
      Object var4 = IntrinsicsKt.getCOROUTINE_SUSPENDED();
      switch (((<undefinedtype>)$continuation).label) {
         case 0:
            ResultKt.throwOnFailure($result);
            ((<undefinedtype>)$continuation).L$0 = param;
            ((<undefinedtype>)$continuation).label = 1;
            if (DelayKt.delay(1000L, (Continuation)$continuation) == var4) {
               return var4;
            }
            break;
         case 1:
            param = (String)((<undefinedtype>)$continuation).L$0;
            ResultKt.throwOnFailure($result);
            break;
         default:
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }
```





### 비교적 고수준으로 Continuation 전달 방식 체험 하기

```kotlin
suspend fun main() {
    println("before launch")

    suspendCoroutine<Unit> { continuation ->
        ...
    }

    println("after")
}
```

코드 블럭 사이를 중단 지점으로 만들어주는 suspendCoroutine 함수를 사용할 수 있다.

이 함수는 람다로 Continuation 객체를 받아서 사용할 수있고 이 람다는 코루틴이 중단되기 전에 실행된다.

람다 함수는 Continuation 객체를 저장한 뒤 코루틴을 다시 실행할 시점을 결정하기 위해 사용된다.

그래서 전달 받은 Continutation 객체로 무언갈 하지 않으면 코루틴이 그대로 중단 된다.

```kotlin
fun main() = runBlocking {
    println("before launch")

    suspendCoroutine<Unit> {
        it.resume(Unit)
    }

    suspendCoroutine<Unit> {
        it.resumeWithException(RuntimeException("error"))
    }

    println("after")
}
```

resume 함수에 값을 넘겨주면 리턴 받아 사용할 수 있다. (async-await 처럼)

```kotlin
fun main() = runBlocking {
    println("before launch")

    val str = suspendCoroutine {
        it.resume("hello-world")
    }

    println(str)

    println("after")
}
```


### 일시 중단 함수의 최적화와 성능

코드로 보면 코루틴을 중단 후 바로 실행을 하는 코드더라도 실제로는 최적화로 인해 곧바로 재개될 경우 아예 중단되지 않고 실행될 수도 있다. (테스트 못해봄)

일반 함수에 비해 중단 함수를 사용하면 비용이 클 것이라고 생각하지만 실제로는 그렇지 않다.

함수를 상태로 나누는 것은 숫자를 비교하는 것 만큼 쉬운 일이며 실행점이 변하는 비용 또한 거의 들지 않는다.
