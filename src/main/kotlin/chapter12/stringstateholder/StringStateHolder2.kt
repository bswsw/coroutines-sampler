package chapter12.stringstateholder

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StringStateHolder2 {

    var stringState = ""
        private set

    suspend fun updateStringWithDelay(newString: String) = coroutineScope {
        launch {
            delay(1000)
            stringState = newString
        }
    }
}
