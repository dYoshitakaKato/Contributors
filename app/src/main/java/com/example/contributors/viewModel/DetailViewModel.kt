package com.example.contributors.viewModel

import android.content.Context
import androidx.lifecycle.*
import com.example.contributors.R
import com.example.contributors.model.ContributorDetail
import com.example.contributors.repository.ContributorRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(private val repository: ContributorRepository,
                                                  @Assisted private val login: String,
                                                  @ApplicationContext private val context: Context)
    : ViewModel() {
    private val _dataLoading = MutableLiveData<Boolean>(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _snackbarText = MutableLiveData<String>("")
    val snackbarText: LiveData<String> = _snackbarText

    private val _model = MutableLiveData<ContributorDetail>()
    val model: LiveData<ContributorDetail> = _model

    fun load(){
        _dataLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.createService().fetchDetail(login)
            _dataLoading.postValue(false)
            if (response.isSuccessful) {
                val body = response.body() ?: return@launch
                _model.postValue(body)
                return@launch
            }
            _snackbarText.postValue(context.getString(R.string.deta_load_error_message))
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