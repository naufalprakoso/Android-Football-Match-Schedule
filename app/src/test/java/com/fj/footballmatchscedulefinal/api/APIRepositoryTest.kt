package com.fj.footballmatchscedulefinal.api

import org.junit.Test
import org.mockito.Mockito

/**
 * Created by naufalprakoso on 30/05/18.
 */
class APIRepositoryTest {
    @Test
    fun testDoRequest() {
        val apiRepository = Mockito.mock(APIRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=English%20Premier%20League"
        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }
}