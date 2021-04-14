package com.assignment.viewmodels

import android.content.Context
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.assignment.data.api.ApiService
import com.assignment.data.network.Resource
import com.assignment.data.repository.FactsRepository
import com.assignment.model.FactsResponseModel
import com.assignment.room.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Harpreet Singh
 */
@RunWith(AndroidJUnit4::class)
class FactsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var factsDao: FactsDao
    private lateinit var database: AppDatabase


    private lateinit var factsViewModel: FactsViewModel

    @Mock
    private lateinit var observer: Observer<List<FactsModel>>

    @Mock
    private lateinit var apiObserver: Observer<Resource<FactsResponseModel>>

    @Mock
    private lateinit var factsRepository: FactsRepository

    @Mock
    private lateinit var databaseHelper: DatabaseHelper

    @Mock
    private lateinit var apiService: ApiService

    /**
     * we are initialising the database and dao object here
     */
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val context = ApplicationProvider.getApplicationContext<Context>()
        // initialize the db and dao variable
        database =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
                .build()
        factsDao = database.factsDao()
        databaseHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(context))
        factsViewModel = FactsViewModel(factsRepository, databaseHelper)
        factsRepository = FactsRepository(apiService)
    }

    @Test
    fun fetchFact() = runBlocking {
        var facts = ArrayList<FactsModel>()
        val factsModel = FactsModel(1, "Canada", "A country", "")
        //adding a sample object to arrayList
        facts.add(0, factsModel)
        //setting observer to observe when data is being added
        factsDao.getAll().observeForever(observer)
        factsDao.insertAll(facts)
        //verify if the added data is observed by the observer
        verify(observer).onChanged(Collections.singletonList(factsModel))
    }

    @Test
    fun clearAll() = runBlocking {
        //taking an empty arrayList
        var facts = ArrayList<FactsModel>()
        //initialising observer
        factsDao.getAll().observeForever(observer)
        //calling the delete all function
        factsDao.deleteAll()
        //verify with the observer that list got empty or not
        verify(observer).onChanged(facts)
    }


}