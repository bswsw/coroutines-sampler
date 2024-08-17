package chapter12

interface UserNameRepository {
    fun saveUserName(id: String, name: String)
    fun getNameByUserId(id: String): String?
}

class StubUserNameRepository(private val userNameMap: Map<String, String>) : UserNameRepository {
    override fun saveUserName(id: String, name: String) {
        //
    }

    override fun getNameByUserId(id: String): String? {
        return userNameMap[id]
    }
}

class FakeUserNameRepository : UserNameRepository {
    private val userNameMap = mutableMapOf<String, String>()

    override fun saveUserName(id: String, name: String) {
        userNameMap[id] = name
    }

    override fun getNameByUserId(id: String): String? {
       return userNameMap[id]
    }
}

class SpyUserNameRepository : UserNameRepository {
    private val userNameMap = mutableMapOf<String, String>()
    private val saveUserNameCallCount = mutableMapOf<String, Int>()
    private val getNameByUserIdCallCount = mutableMapOf<String, Int>()

    override fun saveUserName(id: String, name: String) {
        userNameMap[id] = name
        saveUserNameCallCount[id] = saveUserNameCallCount.getOrDefault(id, 0) + 1
    }

    override fun getNameByUserId(id: String): String? {
        getNameByUserIdCallCount[id] = getNameByUserIdCallCount.getOrDefault(id, 0) + 1
        return userNameMap[id]
    }

    fun getSaveUserNameCallCount(id: String): Int {
        return saveUserNameCallCount.getOrDefault(id, 0)
    }

    fun getGetNameByUserIdCallCount(id: String): Int {
        return getNameByUserIdCallCount.getOrDefault(id, 0)
    }
}
