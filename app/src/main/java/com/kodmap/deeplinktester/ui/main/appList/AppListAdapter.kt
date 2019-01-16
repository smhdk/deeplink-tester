package com.kodmap.deeplinktester.ui.main.appList

import android.app.Activity
import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import com.kodmap.deeplinktester.R
import com.kodmap.deeplinktester.base.AdapterClickListener
import com.kodmap.deeplinktester.core.BaseAdapter
import com.kodmap.deeplinktester.databinding.ItemAppListBinding
import com.kodmap.deeplinktester.db.entities.AppItem


class AppListAdapter(val callback : AdapterClickListener) : BaseAdapter<AppItem>(diffCallback){
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val mBinding = DataBindingUtil.inflate<ItemAppListBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_app_list,
                parent,
                false
                )
        mBinding.viewModel = AppListItemViewModel(parent.context.applicationContext as Application)
        mBinding.viewModel?.initListener(listener = callback)
        return mBinding
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        (binding as ItemAppListBinding).viewModel?.setModel(getItem(position))
        binding.executePendingBindings()
    }

    fun getItemFromPosition(position: Int) : AppItem{
        return getItem(position)
    }

    companion object {
        val diffCallback = object :DiffUtil.ItemCallback<AppItem>(){
            override fun areItemsTheSame(oldItem: AppItem, newItem: AppItem) =
                    newItem.app?.id == oldItem.app?.id

            override fun areContentsTheSame(oldItem: AppItem, newItem: AppItem)=
                    newItem == oldItem
        }
    }
}