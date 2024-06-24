package chapter4

import kotlinx.coroutines.*

fun main() = runBlocking {
    val updateTokenJob = launch(Dispatchers.IO) {
        println("토큰 업데이트 시작")
        delay(100L)
        println("토큰 업데이트 완료")
    }
    updateTokenJob.join()

    val networkCallJob = launch(Dispatchers.IO) {
        println("네트워크 요청")
    }

    listOf(updateTokenJob, networkCallJob).joinAll()
}
