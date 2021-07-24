package com.example.contributors.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.contributors.R
import com.example.contributors.databinding.DetailFragmentBinding
import com.example.contributors.databinding.MainFragmentBinding
import com.example.contributors.viewModel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private val LOGIN_KEY = "login-key"

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    companion object {
        fun newInstance(login: String): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putString(LOGIN_KEY, login)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var login: String
    private lateinit var binding: DetailFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login = requireArguments().getString(LOGIN_KEY, "")
    }

    @Inject
    lateinit var viewModelFactory: DetailViewModel.Factory
    private val detailViewModel: DetailViewModel by viewModels {
        DetailViewModel.provideFactory(viewModelFactory, login)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = detailViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        detailViewModel.snackbarText.observe(viewLifecycleOwner, Observer {
            if (it == "") {
                return@Observer
            }
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.load()
    }
}