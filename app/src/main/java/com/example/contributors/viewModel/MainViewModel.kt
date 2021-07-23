package com.example.contributors.viewModel

import androidx.lifecycle.ViewModel
import com.example.contributors.repository.ContributorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ContributorsRepository)  : ViewModel() {
    fun load(){
        repository.createService().fetch()
    }
}