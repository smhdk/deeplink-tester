package com.kodmap.deeplinktester.ui.deeplink

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kodmap.deeplinktester.R
import com.kodmap.deeplinktester.base.AdapterClickListener
import com.kodmap.deeplinktester.base.Constants
import com.kodmap.deeplinktester.core.BaseActivity
import com.kodmap.deeplinktester.databinding.ActivityDeeplinkBinding
import com.kodmap.deeplinktester.db.entities.AppEntity
import com.kodmap.deeplinktester.db.entities.DeeplinkEntity
import com.kodmap.deeplinktester.ui.deeplink.deeplinkList.DeeplinkListAdapter
import com.kodmap.deeplinktester.utils.callback.SwipeToDeleteCallback
import com.kodmap.deeplinktester.utils.extensions.toast
import org.jetbrains.anko.doAsync

class DeeplinkActivity : BaseActivity<DeeplinkActivityViewModel,ActivityDeeplinkBinding>(DeeplinkActivityViewModel::class.java),AdapterClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolbar()
        initAdapter()
        initSwipeDelete()
        initIntent()
    }

    private fun initSwipeDelete() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deeplinkEntity = (binding.rvDeeplink.adapter as DeeplinkListAdapter).getItemFromPosition(viewHolder.adapterPosition)
                doAsync{
                    binding.viewModel?.db?.deeplinkDao()?.deleteDeeplink(deeplinkEntity)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvDeeplink)
    }

    private fun initIntent() {
        val appEntity = intent.getParcelableExtra<AppEntity>(Constants.IntentName.App)
        if (appEntity != null){
            binding.viewModel?.appEntity?.set(appEntity)
            binding.viewModel?.getLinkList()?.observe(this, Observer {
                (binding.rvDeeplink.adapter as DeeplinkListAdapter).submitList(it)
                binding.rvDeeplink.setLoadingFinished(true)
            })
        }

    }

    private fun initAdapter() {
        val adapter = DeeplinkListAdapter(this)
        binding.rvDeeplink.adapter = adapter
        binding.rvDeeplink.setEmptyView(binding.emptyLayout)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_deeplink,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_add_deeplink ->{
                showAddLinkDialog()
            }
            android.R.id.home ->{
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAddLinkDialog() {
        val builder = AlertDialog.Builder(this@DeeplinkActivity)
        val inflater = layoutInflater

        val layout = inflater.inflate(R.layout.dialog_add,null)
        val tv_dialog_title = layout.findViewById<TextView>(R.id.tv_title)
        val et_link = layout.findViewById<EditText>(R.id.et_name)

        tv_dialog_title.text = getString(R.string.add_deeplink)
        et_link.hint = getString(R.string.write_deeplink)

        builder.setView(layout)
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.add),null)
        builder.setNegativeButton(getString(R.string.cancel),null)

        val dialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val link = layout.findViewById<EditText>(R.id.et_name).text
                if (link.isNotEmpty()){
                    doAsync {
                        binding.viewModel?.db?.deeplinkDao()?.insertDeeplink(
                                DeeplinkEntity(appId = binding.viewModel?.appEntity?.get()?.id,link = link.toString()))
                        layout.findViewById<EditText>(R.id.et_name).setText("")
                        layout.findViewById<EditText>(R.id.et_name).hint = getString(R.string.write_deeplink)
                        toast("$link added")
                    }
                }else{
                    toast(getString(R.string.write_deeplink_error))
                }
            }
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show()
    }

    override fun getLayoutRes() = R.layout.activity_deeplink

    override fun initViewModel(viewModel: DeeplinkActivityViewModel) {
        binding.viewModel = viewModel
    }

    override fun onClick(item: Any?, type: Int?) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse((item as DeeplinkEntity).link))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            this.toast(getString(R.string.intent_sent),Toast.LENGTH_SHORT)
        }catch (ex : Exception){
            toast(getString(R.string.not_found_activity))
        }
    }

    override fun onLongClick(item: Any?, type: Int?) {
        showRenameDialog(item as DeeplinkEntity)
    }

    private fun showRenameDialog(deeplinkEntity: DeeplinkEntity) {
        val builder = AlertDialog.Builder(this@DeeplinkActivity)
        val inflater = layoutInflater

        val layout = inflater.inflate(R.layout.dialog_add,null)
        val tv_dialog_title = layout.findViewById<TextView>(R.id.tv_title)
        val et_link = layout.findViewById<EditText>(R.id.et_name)

        tv_dialog_title.text = getString(R.string.rename)
        et_link.setText(deeplinkEntity.link)
        et_link.setSelection(deeplinkEntity.link?.length!!)

        builder.setView(layout)
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.save),null)
        builder.setNegativeButton(getString(R.string.cancel),null)

        val dialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val link = et_link.text
                if (link.isNotEmpty()){
                    doAsync {
                        val newDeeplink = deeplinkEntity.copy(link = link.toString())
                        binding.viewModel?.db?.deeplinkDao()?.insertDeeplink(newDeeplink)
                        dialog.dismiss()
                        toast("$link saved")
                    }
                }else{
                    toast(getString(R.string.write_deeplink_error))
                }
            }
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show()
    }
}