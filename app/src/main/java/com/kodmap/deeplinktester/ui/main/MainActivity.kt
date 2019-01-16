package com.kodmap.deeplinktester.ui.main

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.kodmap.deeplinktester.App
import com.kodmap.deeplinktester.R
import com.kodmap.deeplinktester.base.AdapterClickListener
import com.kodmap.deeplinktester.base.Constants
import com.kodmap.deeplinktester.core.BaseActivity
import com.kodmap.deeplinktester.databinding.ActivityMainBinding
import com.kodmap.deeplinktester.db.AppDatabase
import com.kodmap.deeplinktester.db.entities.AppEntity
import com.kodmap.deeplinktester.db.entities.AppItem
import com.kodmap.deeplinktester.ui.deeplink.DeeplinkActivity
import com.kodmap.deeplinktester.ui.main.appList.AppListAdapter
import com.kodmap.deeplinktester.utils.callback.SwipeToDeleteCallback
import com.kodmap.deeplinktester.utils.extensions.toast
import org.jetbrains.anko.doAsync
import javax.inject.Inject


class MainActivity : BaseActivity<MainActivityViewModel, ActivityMainBinding>(MainActivityViewModel::class.java),AdapterClickListener {

    @Inject
    lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as App).component.inject(this)

        initToolbar()
        initAdapter()
        initSwipeDelete()
        initList()
    }

    private fun initList() {
        binding.viewModel?.getAppList()?.observe(this, Observer {
            (binding.rvApp.adapter as AppListAdapter).submitList(it)
            binding.rvApp.setLoadingFinished(true)
        })
    }

    private fun initSwipeDelete() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val appItem = (binding.rvApp.adapter as AppListAdapter).getItemFromPosition(viewHolder.adapterPosition)
                doAsync {
                    db.appDao().deleteApp(appItem.app!!)
                    db.deeplinkDao().deleteDeeplinks(appItem.linkList!!)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.rvApp)
    }

    private fun initAdapter() {
        val adapter = AppListAdapter(this)
        binding.rvApp.adapter = adapter
        binding.rvApp.setEmptyView(binding.emptyLayout)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_add_app ->{
                showAddAppDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAddAppDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        val inflater = layoutInflater

        val layout = inflater.inflate(R.layout.dialog_add,null)
        val tv_dialog_title = layout.findViewById<TextView>(R.id.tv_title)
        val et_app_name = layout.findViewById<EditText>(R.id.et_name)
        tv_dialog_title.text = getString(R.string.app_name)
        et_app_name.hint = getString(R.string.write_app_name)

        builder.setView(layout)
        builder.setCancelable(false)
        builder.setPositiveButton(getString(R.string.add),null)
        builder.setNegativeButton(getString(R.string.cancel),null)

        val dialog = builder.create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val appName = et_app_name.text
                if (appName.isNotEmpty()){
                    doAsync {
                        binding.viewModel?.db?.appDao()?.insertApp(AppEntity(name = appName.toString()))
                        layout.findViewById<EditText>(R.id.et_name).setText("")
                        layout.findViewById<EditText>(R.id.et_name).hint = getString(R.string.write_app_name)
                        toast("$appName added")
                    }
                }else{
                    toast(getString(R.string.write_app_name_error))
                }
            }
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.show()
    }

    fun initToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.app)
    }

    override fun initViewModel(viewModel: MainActivityViewModel) {
        binding.viewModel = viewModel
    }

    override fun getLayoutRes() = R.layout.activity_main

    override fun onClick(item: Any?, type: Int?) {
        val deeplinkIntent = Intent(this,DeeplinkActivity::class.java)
        deeplinkIntent.putExtra(Constants.IntentName.App,(item as AppItem).app)
        deeplinkIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(deeplinkIntent)
    }

    override fun onLongClick(item: Any?, type: Int?) {
    }
}
