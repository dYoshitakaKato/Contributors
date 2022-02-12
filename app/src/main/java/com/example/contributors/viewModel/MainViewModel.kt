package com.example.contributors.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contributors.R
import com.example.contributors.model.Contributor
import com.example.contributors.repository.ContributorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor()  : ViewModel() {

    @Inject
    lateinit var contributorsRepository: ContributorsRepository

    private val _items = MutableLiveData<List<Contributor>>(ArrayList())
    val items: LiveData<List<Contributor>> = _items

    private val _dataLoading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    fun onLoad(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(dispatcher) {
            _dataLoading.postValue(true)
            val response = contributorsRepository.get()
            _dataLoading.postValue(false)
            if (response.isSuccess) {
                with(_items) { postValue(response.responseData) }
                return@launch
            }
            _message.postValue(R.string.deta_load_error_message)
        }
    }
}