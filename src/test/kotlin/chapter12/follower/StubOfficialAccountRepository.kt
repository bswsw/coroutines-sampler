package chapter12.follower

import kotlinx.coroutines.delay


class StubOfficialAccountRepository(private val users: List<Follower.OfficialAccount>) : OfficialAccountRepository {
    override suspend fun searchByName(name: String): List<Follower.OfficialAccount> {
        delay(1000L)
        return users.filter { it.name.contains(name) }
    }
}
