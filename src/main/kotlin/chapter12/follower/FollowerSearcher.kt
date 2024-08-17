package chapter12.follower

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class FollowerSearcher(
    private val officialAccountRepository: OfficialAccountRepository,
    private val personAccountRepository: PersonAccountRepository
) {

    suspend fun searchByName(name: String): List<Follower> = coroutineScope {
        val officialAccounts = async {
            officialAccountRepository.searchByName(name)
        }
        val personAccounts = async {
            personAccountRepository.searchByName(name)
        }

        listOf(
            *officialAccounts.await().toTypedArray(),
            *personAccounts.await().toTypedArray()
        )
    }
}
