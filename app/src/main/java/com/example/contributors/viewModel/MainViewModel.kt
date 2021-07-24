package com.example.contributors.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contributors.R
import com.example.contributors.model.Contributor
import com.example.contributors.repository.ContributorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ContributorRepository,
                                        @ApplicationContext private val context: Context)  : ViewModel() {
    private val _items = MutableLiveData<List<Contributor>>(ArrayList())
    val items: LiveData<List<Contributor>> = _items

    private val _dataLoading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _snackbarText = MutableLiveData("")
    val snackbarText: LiveData<String> = _snackbarText

    private val _openDetail = MutableLiveData("")
    val openDetail: LiveData<String> = _openDetail

    fun load() {
        _dataLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.createService().fetchAll()
            _dataLoading.postValue(false)
            if (response.isSuccessful) {
                val body = response.body() ?: return@launch
                _items.postValue(body)
                return@launch
            }
            _snackbarText.postValue(context.getString(R.string.deta_load_error_message))
        }
    }

    fun openDetail(taskId: String) {
        _openDetail.value = taskId
    }
}