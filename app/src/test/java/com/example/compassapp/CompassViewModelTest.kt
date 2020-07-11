package com.example.compassapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.compassapp.data.models.CompassOrientation
import com.example.compassapp.data.orientation.CompassOrientationSource
import com.example.compassapp.data.repo.CompassRepository
import com.example.compassapp.viewmodels.CompassViewModel
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Spy
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class CompassViewModelTest {
    private lateinit var viewModel: CompassViewModel

    private lateinit var _chosenLatitudeAndLongitude: LiveData<Pair<Float, Float>>

    private lateinit var isErrorLiveData: LiveData<Boolean>

    @Mock
    private lateinit var repo: CompassRepository

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()
//
//    @Before
//    fun setup(){
////        `when`(repo.orientation).thenReturn(orientation)
//    }


//    @Test
//    fun test_getOrientationModel() {
//        val repo: CompassRepository = mock()
//        val viewModel = CompassViewModel(repo)
////        viewModel.todo.title = ""
////        val message = "Title is required"
//        val actual = viewModel.save()
//        Assert.assertNotNull(actual)
//        Assert.assertEquals(actual, message)
//        verify(repo, never()).insert(any())
//    }
}