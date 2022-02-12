package com.example.contributors.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contributors.R
import com.example.contributors.model.ContributorDetail
import com.example.contributors.repository.ContributorDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor()
    : ViewModel() {
    @Inject
    lateinit var contributorRepository: ContributorDetailRepository

    private val _dataLoading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _message = MutableLiveData<Int>()
    val message: LiveData<Int> = _message

    private val _model = MutableLiveData<ContributorDetail>()
    val model: LiveData<ContributorDetail> = _model

    fun onLoad(login: String, dispatcher: CoroutineDispatcher = Dispatchers.IO){
        viewModelScope.launch(dispatcher) {
            _dataLoading.postValue(true)
            val response = contributorRepository.get(login)
            _dataLoading.postValue(false)
            if (response.isSuccess) {
                _model.postValue(response.responseData!!)
                return@launch
            }
            _message.postValue(R.string.deta_load_error_message)
        }
    }
}