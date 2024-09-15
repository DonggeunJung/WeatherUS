package com.example.weatherus.viewmodel

import com.example.weatherus.data.StateApi
import com.example.weatherus.data.StateDatabase
import com.example.weatherus.domain.StateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class StateRepositoryTest {
    private val api: StateApi = StateApi.instance

    @Test
    fun stateApi_should_return_States_list() {
        CoroutineScope(Dispatchers.IO).launch {
            val states = api.states()
            Assert.assertFalse(states.isNullOrEmpty())
            Assert.assertEquals(53, states?.size)
            Assert.assertEquals(states?.get(0) ?: "", "AL")
        }
    }

    @Test
    fun stateReporitory_should_return_States_list() {
        val db = mock(StateDatabase::class.java)
        Mockito.`when`(db?.stateDao()?.getAll()).thenReturn(null)
        val stateRepository = StateRepository(api, db)
        CoroutineScope(Dispatchers.IO).launch {
            val states = stateRepository.reqStates()
            Assert.assertFalse(states.isNullOrEmpty())
            Assert.assertEquals(2, states?.size)
            Assert.assertEquals(states?.get(0) ?: "", "AL")
        }
    }

}