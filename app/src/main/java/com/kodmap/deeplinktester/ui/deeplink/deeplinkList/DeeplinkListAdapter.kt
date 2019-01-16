package com.kodmap.deeplinktester.ui.deeplink.deeplinkList

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.kodmap.deeplinktester.R
import com.kodmap.deeplinktester.base.AdapterClickListener
import com.kodmap.deeplinktester.core.BaseAdapter
import com.kodmap.deeplinktester.databinding.ItemAppListBinding
import com.kodmap.deeplinktester.databinding.ItemDeeplinkListBinding
import com.kodmap.deeplinktester.db.entities.AppItem
import com.kodmap.deeplinktester.db.entities.DeeplinkEntity
import com.kodmap.deeplinktester.ui.main.appList.AppListItemViewModel

class DeeplinkListAdapter(val callback : AdapterClickListener) : BaseAdapter<DeeplinkEntity>(diffCallback){
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val mBinding = DataBindingUtil.inflate<ItemDeeplinkListBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_deeplink_list,
                parent,
                false
        )
        mBinding.viewModel = DeeplinkListItemViewModel(parent.context.applicationContext as Application)
        mBinding.viewModel?.initListener(listener = callback)
        return mBinding
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        (binding as ItemDeeplinkListBinding).viewModel?.setModel(getItem(position))
        binding.executePendingBindings()
    }

    fun getItemFromPosition(position: Int): DeeplinkEntity {
        return getItem(position)
    }


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<DeeplinkEntity>(){
            override fun areItemsTheSame(oldItem: DeeplinkEntity, newItem: DeeplinkEntity) =
                    newItem.id == oldItem.id

            override fun areContentsTheSame(oldItem: DeeplinkEntity, newItem: DeeplinkEntity)=
                    newItem == oldItem
        }
    }
}