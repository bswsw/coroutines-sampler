package chapter12.follower

interface PersonAccountRepository {
    suspend fun searchByName(name: String): List<Follower.PersonAccount>
}
