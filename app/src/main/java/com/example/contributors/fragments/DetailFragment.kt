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
import androidx.navigation.fragment.navArgs
import com.example.contributors.R
import com.example.contributors.databinding.DetailFragmentBinding
import com.example.contributors.util.EventObserver
import com.example.contributors.viewModel.DetailViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private val LOGIN_KEY = "login-key"

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val args: DetailFragmentArgs by navArgs()

    private lateinit var login: String
    private lateinit var binding: DetailFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        login = args.login
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
        detailViewModel.snackbarText.observe(viewLifecycleOwner, EventObserver {
            if (it == "") {
                return@EventObserver
            }
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        })
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.load()
    }
}