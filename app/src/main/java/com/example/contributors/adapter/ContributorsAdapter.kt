package com.example.contributors.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contributors.databinding.ContributorItemBinding
import com.example.contributors.fragments.MainFragmentDirections
import com.example.contributors.model.Contributor
import com.example.contributors.viewModel.MainViewModel

private val DIFF_CALLBACK: DiffUtil.ItemCallback<Contributor> =
    object : DiffUtil.ItemCallback<Contributor>() {
        override fun areItemsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem.id != newItem.id
        }

        override fun areContentsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem == newItem
        }
    }

class ContributorsAdapter(private val viewModel: MainViewModel, private val parentLifecycleOwner: LifecycleOwner) :
    ListAdapter<Contributor, ContributorsHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorsHolder {
        return ContributorsHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ContributorsHolder, position: Int) {
        val item = getItem(position)
        holder.bind(createOnClickListener(item.login), parentLifecycleOwner, item)
    }

    private fun createOnClickListener(login: String): View.OnClickListener {
        return View.OnClickListener {
            val action = MainFragmentDirections.actionMainToDetail(login)
            it.findNavController().navigate(action)
        }
    }
}

class ContributorsHolder(private var binding: ContributorItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        clickListener: View.OnClickListener,
        lifecycleOwner: LifecycleOwner,
        item: Contributor
    ) {
        binding.item = item
        binding.lifecycleOwner = lifecycleOwner
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): ContributorsHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ContributorItemBinding.inflate(layoutInflater, parent, false)
            return ContributorsHolder(binding)
        }
    }
}