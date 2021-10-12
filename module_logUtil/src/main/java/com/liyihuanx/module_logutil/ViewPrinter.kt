package com.liyihuanx.module_logutil

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.log

/**
 * @author created by liyihuanx
 * @date 2021/10/8
 * @description: 可视化打印器
 */
class ViewPrinter(activity: Activity) : LogPrinter, LifecycleObserver {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LogAdapter

    private val rootView: FrameLayout by lazy {
        activity.findViewById(android.R.id.content)
    }

    private val viewPrinter by lazy {
        LayoutInflater.from(activity).inflate(R.layout.view_printer, null, false)
    }

    init {
        initRecyclerView(viewPrinter)
        initViewPrinterLayout(viewPrinter)
        rootView.addView(viewPrinter)
    }

    private fun initViewPrinterLayout(viewPrinter: View) {
        val tvOperate: TextView = viewPrinter.findViewById(R.id.tvOperate)
        val rlLogTitle: RelativeLayout = viewPrinter.findViewById(R.id.rlLogTitle)
        val tvClean: TextView = viewPrinter.findViewById(R.id.tvClean)

        tvOperate.setOnClickListener {
            if (tvOperate.text == "Close") {
                recyclerView.visibility = View.GONE
                rlLogTitle.visibility = View.GONE
                tvOperate.text = "Open"
            } else {
                recyclerView.visibility = View.VISIBLE
                rlLogTitle.visibility = View.VISIBLE
                tvOperate.text = "Close"
            }
        }

        tvClean.setOnClickListener {
            adapter.clearLog()
        }
    }

    private fun initRecyclerView(viewPrinter: View) {
        recyclerView = viewPrinter.findViewById(R.id.rvViewPrinter)
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)

        adapter = LogAdapter(recyclerView.context)
        recyclerView.adapter = adapter
    }


    override fun print(config: LogConfig, level: Int, tag: String?, printString: String) {
        adapter.addItem(LogBean(System.currentTimeMillis(), level, tag, printString))
        recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun removeView() {
        rootView.removeView(viewPrinter)
        LogManager.removeLogPrinter(this)
    }
}




