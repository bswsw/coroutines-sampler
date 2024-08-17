package chapter12.stringstateholder

import kotlinx.coroutines.*

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
