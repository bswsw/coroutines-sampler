package chapter7

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class CustomCoroutineScope : CoroutineScope {
    override val coroutineContext: CoroutineContext =
        Job() + newSingleThreadContext("CustomThread")
}
