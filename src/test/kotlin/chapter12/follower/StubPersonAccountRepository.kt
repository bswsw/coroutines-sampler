package chapter12.follower

import kotlinx.coroutines.delay

class StubPersonAccountRepository(private val users: List<Follower.PersonAccount>) : PersonAccountRepository {

    override suspend fun searchByName(name: String): List<Follower.PersonAccount> {
        delay(1000L)
        return users.filter { it.name.contains(name) }
    }
}
