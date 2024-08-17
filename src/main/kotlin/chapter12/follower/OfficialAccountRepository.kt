package chapter12.follower

interface OfficialAccountRepository {
    suspend fun searchByName(name: String): List<Follower.OfficialAccount>
}
