package com.assignment.ui.fragments

import androidx.fragment.app.testing.launchFragment
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import com.assignment.adapters.DashboardAdapter
import com.assignment.data.api.ApiService
import com.assignment.data.repository.FactsRepository
import com.assignment.model.FactsResponseModel
import com.assignment.room.DatabaseHelper
import com.assignment.room.FactsModel
import com.assignment.viewmodels.FactsViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DashboardFragmentTest {

    private lateinit var factsViewModel:FactsViewModel
    private lateinit var factsRepository: FactsRepository

    @Mock
    private lateinit var apiService: ApiService

    @Mock
    private lateinit var databaseHelper: DatabaseHelper


    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        factsRepository= FactsRepository(apiService)
        factsViewModel= FactsViewModel(factsRepository,databaseHelper)
    }

    @Test
    fun testWhenListNotEmpty() {
        val scenario = launchFragment<DashboardFragment>()
        scenario.onFragment { fragment ->
        val list = mutableListOf<FactsResponseModel.Rows>()
        list.add(0, FactsResponseModel.Rows("About Canada", "Country", ""))
        fragment.factsList=list
        fragment.dashboardAdapter= DashboardAdapter(
            ApplicationProvider.getApplicationContext(),
            fragment.factsList)

        fragment.recyclerView.adapter=fragment.dashboardAdapter
        Assert.assertEquals(1, fragment.binding.recyclerView.adapter?.itemCount)
         }
    }

    @Test
    fun testWhenListEmpty() {
        val scenario = launchFragment<DashboardFragment>()
        scenario.onFragment { fragment ->
        val list = mutableListOf<FactsResponseModel.Rows>()
        fragment.factsList=list
            fragment.dashboardAdapter= DashboardAdapter(
                ApplicationProvider.getApplicationContext(),
                fragment.factsList)

            fragment.recyclerView.adapter=fragment.dashboardAdapter
            Assert.assertEquals(0, fragment.binding.recyclerView.adapter?.itemCount)
        }
    }
}


