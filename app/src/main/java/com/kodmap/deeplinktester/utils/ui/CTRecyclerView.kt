package com.kodmap.deeplinktester.utils.ui

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kodmap.deeplinktester.R

class CTRecyclerView : RecyclerView {

    private var emptyView: View? = null
    private var loadingView: View? = null
    private var countTextView: TextView? = null
    private var isCountTextAdd: Boolean = false
    private var isLoadingFinished = false
    private val emptyObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    fun setLoadingFinished(loadingFinished: Boolean) {
        isLoadingFinished = loadingFinished
        checkIfEmpty()
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CTRecyclerView, 0, 0)

        val emptyViewResourceId = ta.getResourceId(R.styleable.CTRecyclerView_emptyView, -1)
        if (emptyViewResourceId != -1)
            emptyView = findViewById(emptyViewResourceId)

        val loadingViewResourceId = ta.getResourceId(R.styleable.CTRecyclerView_loadingView, -1)
        if (loadingViewResourceId != -1)
            loadingView = findViewById(loadingViewResourceId)

        ta.recycle()

        setAdapter(null)
    }

    private fun checkIfEmpty() {
        if (context is Activity)
            (context as Activity).runOnUiThread {
                val count: Int
                if (adapter == null || !isLoadingFinished) {
                    if (loadingView != null) {
                        loadingView!!.visibility = View.VISIBLE
                        visibility = View.GONE
                    }
                } else if (adapter != null && adapter!!.itemCount == 0) {
                    if (loadingView != null)
                        loadingView!!.visibility = View.GONE
                    count = adapter!!.itemCount
                    if (this@CTRecyclerView.countTextView != null) {
                        if (isCountTextAdd) {
                            countTextView!!.text = String.format("%s Adet", count)
                        } else {
                            countTextView!!.text = count.toString()
                        }
                    }
                    if (emptyView != null) {
                        val emptyViewVisible = count == 0
                        emptyView!!.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
                        if (!emptyViewVisible) {
                            visibility = View.VISIBLE
                        } else
                            visibility = View.GONE
                    }
                } else if (adapter != null && adapter!!.itemCount != 0) {
                    if (loadingView != null) {
                        loadingView!!.visibility = View.GONE
                    }
                    if (emptyView != null) {
                        emptyView!!.visibility = View.GONE
                    }
                    visibility = View.VISIBLE
                } else {
                    if (loadingView != null) {
                        loadingView!!.visibility = View.VISIBLE
                        visibility = View.GONE
                    }
                }
            }
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(emptyObserver)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(emptyObserver)
        checkIfEmpty()
        emptyObserver.onChanged()
    }

    fun setEmptyView(emptyView: View?) {
        if (emptyView == null)
            return
        this.emptyView = emptyView
        checkIfEmpty()
    }

    fun setLoadingView(loadingView: View?) {
        if (loadingView == null)
            return
        this.loadingView = loadingView
        checkIfEmpty()
    }

    fun disableLoadingView() {
        if (loadingView == null)
            return
        loadingView!!.visibility = View.GONE
    }

    fun setCountTextView(countTextView: TextView, isCountTextAdd: Boolean) {
        this.isCountTextAdd = isCountTextAdd
        this.countTextView = countTextView
        checkIfEmpty()
    }
}
