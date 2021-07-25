package com.example.contributors.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.contributors.adapter.ContributorsAdapter
import com.example.contributors.databinding.MainFragmentBinding
import com.example.contributors.util.EventObserver
import com.example.contributors.viewModel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: MainFragmentBinding

    private lateinit var listAdapter: ContributorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.load()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = mainViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        listAdapter = ContributorsAdapter(mainViewModel, this)
        binding.categoryRecycle.adapter = listAdapter
        mainViewModel.items.observe(viewLifecycleOwner, Observer{
            listAdapter.submitList(it)
        })
        mainViewModel.snackbarText.observe(viewLifecycleOwner, EventObserver {
            if (it == "") {
                return@EventObserver
            }
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        })
        mainViewModel.openDetail.observe(viewLifecycleOwner, EventObserver {
            if (it == "") {
                return@EventObserver
            }
            navigateDetail(it)
        })
    }

    private fun navigateDetail(login: String) {
        val action = MainFragmentDirections.actionMainToDetail(login)
        findNavController().navigate(action)
    }
}