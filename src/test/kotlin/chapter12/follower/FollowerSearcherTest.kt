package chapter12.follower

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class FollowerSearcherTest {

    private lateinit var followerSearcher: FollowerSearcher

    @BeforeEach
    fun setup() {
        followerSearcher = FollowerSearcher(
            stubOfficialAccountRepository,
            stubPersonAccountRepository
        )
    }

    @Test
    fun `이름에 포함하는 문자열로 검색하면 공식 계정과 개인 계정이 합쳐져 반환된다`() = runTest {
        // given
        val searchName = "A"
        val expected = listOf(officialAccountFollowers[0], personAccountFollowers[0])

        // when
        val actual = followerSearcher.searchByName(searchName)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `존재하지 않는 이름으로 검색하면 빈 리스트가 반환된다`() = runTest {
        // given
        val searchName = "Z"
        val expected = emptyList<Follower>()

        // when
        val actual = followerSearcher.searchByName(searchName)

        // then
        assertEquals(expected, actual)
    }

    companion object {
        private val officialAccountFollowers = listOf(
            Follower.OfficialAccount(id = "0", "officialA"),
            Follower.OfficialAccount(id = "1", "officialB"),
            Follower.OfficialAccount(id = "2", "officialC"),
        )

        private val stubOfficialAccountRepository = StubOfficialAccountRepository(officialAccountFollowers)

        private val personAccountFollowers = listOf(
            Follower.PersonAccount(id = "3", "personA"),
            Follower.PersonAccount(id = "4", "personB"),
            Follower.PersonAccount(id = "5", "personC"),
        )

        private val stubPersonAccountRepository = StubPersonAccountRepository(personAccountFollowers)
    }
}
