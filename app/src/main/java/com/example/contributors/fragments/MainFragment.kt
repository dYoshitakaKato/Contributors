package com.example.contributors.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.contributors.adapter.ContributorsAdapter
import com.example.contributors.databinding.MainFragmentBinding
import com.example.contributors.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var binding: MainFragmentBinding

    private lateinit var listAdapter: ContributorsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        MainFragmentBinding.inflate(layoutInflater)
        binding = MainFragmentBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        listAdapter = ContributorsAdapter(viewModel, this)
        binding.categoryRecycle.adapter = listAdapter
    }
}