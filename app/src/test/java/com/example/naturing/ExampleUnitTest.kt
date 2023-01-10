package com.example.naturing

import android.app.usage.UsageEvents.Event.NONE
import android.media.MediaPlayer
import androidx.annotation.VisibleForTesting.NONE
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.naturing.data.entities.AllRingtonesResponseModel
import com.example.naturing.data.remote.RetrofitFactory
import com.example.naturing.data.repository.AllRingtonesRepository
import com.example.naturing.viewmodels.AllRingtonesViewModel
import com.example.naturing.views.RingtonesScreenActivity
import com.google.ar.core.Config
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.runners.MockitoJUnitRunner
import org.robolectric.RobolectricTestRunner
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {


  var x = mockk<AllRingtonesRepository>(RetrofitFactory.service.toString())

  @MockK
  var repo = AllRingtonesRepository(RetrofitFactory.service)

  @MockK
  private lateinit var repository: AllRingtonesRepository


  private lateinit var viewModel: AllRingtonesViewModel

  @Rule
  @JvmField
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @Before
  fun setUp() {
    MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
    viewModel = AllRingtonesViewModel(repository)
  }

  @Test
  fun `test func for clicks`() {
    viewModel.kitaplikToolbarOnClick()
    assertEquals(viewModel.kitaplikSayfaDurum.value,"1")
    viewModel.exitToolbarOnClick()
    assertEquals(viewModel.cikisButonDurum.value,"1")
    viewModel.ucretliSayfaClick()
    assertEquals(viewModel.ucretliAllSayfaDurumu.value,"1")
    viewModel.ucretsizSayfaClick()
    assertEquals(viewModel.ucretsizAllSayfaDurumu.value,"1")

  }

  val allRingtonesResultFromAPI: MutableLiveData<MutableList<AllRingtonesResponseModel>> by lazy {
    MutableLiveData<MutableList<AllRingtonesResponseModel>> ()
  }

  @Test
  fun fetchData_success() {
    val allRingtonesResultFromAPI: MutableLiveData<MutableList<AllRingtonesResponseModel>> by lazy {
      MutableLiveData<MutableList<AllRingtonesResponseModel>> ()
    }
    `when`(x.getAllRingtones()).thenReturn(allRingtonesResultFromAPI)

    viewModel.allDeveloper.observeForever {
      assertEquals(allRingtonesResultFromAPI,it)
    }


  }



}
