package com.assignment.ui.fragments

import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.assignment.data.network.Resource
import com.assignment.data.repository.FactsRepository
import com.assignment.model.FactsResponseModel
import com.assignment.room.DatabaseHelper
import com.assignment.viewmodels.FactsViewModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`

@RunWith(AndroidJUnit4::class)
class DashboardFragmentTest {

    private lateinit var factsViewModel:FactsViewModel
    private lateinit var dashboardFragment: DashboardFragment

    @Mock
    private lateinit var factsRepository: FactsRepository

    @Mock
    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var observer: Observer<Resource<FactsResponseModel>>

    @Before
    fun setUp(){
        factsViewModel= FactsViewModel(factsRepository,databaseHelper)
        dashboardFragment=DashboardFragment()
    }

    @Test
    fun testWhenListNotEmpty() {
        val scenario = launchFragment<DashboardFragment>()
        scenario.onFragment { fragment ->
            val list = mutableListOf<FactsResponseModel.Rows>()
            list.add(0, FactsResponseModel.Rows("About Canada", "Country", ""))
            lateinit var factsResponseModel: MutableLiveData<Resource<FactsResponseModel>>
            factsResponseModel.value = Resource.Success(FactsResponseModel("Canada", list))
            `when`(factsViewModel.factsApi()).thenReturn(factsResponseModel)
            fragment.callAdapter()
            assert(fragment.binding.recyclerView.adapter?.itemCount == 0)
        }
    }

    @Test
    fun testWhenListEmpty() {
        val scenario = launchFragment<DashboardFragment>()
        scenario.onFragment { fragment ->
            val list = mutableListOf<FactsResponseModel.Rows>()
            lateinit var factsResponseModel: MutableLiveData<Resource<FactsResponseModel>>
            factsResponseModel.value = Resource.Success(FactsResponseModel("Canada", list))
            `when`(factsViewModel.factsApi()).thenReturn(factsResponseModel)
            fragment.callAdapter()
            assert(fragment.binding.recyclerView.adapter?.itemCount == 0)
        }
    }
}


