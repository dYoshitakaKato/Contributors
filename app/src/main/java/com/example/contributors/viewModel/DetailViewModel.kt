package com.example.contributors.viewModel

import androidx.lifecycle.*
import com.example.contributors.model.ContributorDetail
import com.example.contributors.repository.ContributorRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(private val repository: ContributorRepository,
                                                  @Assisted private val login: String)  : ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _model = MutableLiveData<ContributorDetail>()
    val model: LiveData<ContributorDetail> = _model

    fun load(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.createService().fetchDetail(login)
            if (response.isSuccessful) {
                val body = response.body() ?: return@launch
                _model.postValue(body)
                return@launch
            }
        }
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            login: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(login) as T
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(login: String): DetailViewModel
    }
}