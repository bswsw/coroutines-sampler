package chapter6

import kotlinx.coroutines.CoroutineName
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

data class User(val payAccountId: Long) {
    override fun toString(): String = "User($payAccountId)"
}

data class CoroutineUser(
    val user: User
) : AbstractCoroutineContextElement(CoroutineUser) {

    companion object Key : CoroutineContext.Key<CoroutineUser>

    override fun toString(): String = "CoroutineUser($user)"
}
