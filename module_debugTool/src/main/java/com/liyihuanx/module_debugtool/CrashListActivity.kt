package com.liyihuanx.module_debugtool

import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_crash_list.*
import java.io.File
import java.lang.StringBuilder

/**
 * @ClassName: CrashListActivity
 * @Description: java类作用描述
 * @Author: liyihuan
 * @Date: 2021/10/12 21:44
 */
class CrashListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crash_list)
        rvCrashList.layoutManager = LinearLayoutManager(this)
        val crashListAdapter = CrashListAdapter(CrashManager.getCrashList() ?: arrayOf())
        crashListAdapter.showFileContent = {
            tvFileContent.text = it
            llFileContent.visibility = View.VISIBLE
        }
        rvCrashList.adapter = crashListAdapter

        tvFileContentClose.setOnClickListener {
            llFileContent.visibility = View.GONE
        }
    }

    inner class CrashListAdapter(val list: Array<File>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var showFileContent: ((String) -> Unit)? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = layoutInflater.inflate(R.layout.item_crash, parent, false)
            return object : RecyclerView.ViewHolder(itemView) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val file = list[position]
            val tvCrashFileName = holder.itemView.findViewById<TextView>(R.id.tvCrashFileName)
            tvCrashFileName.text = file.name
            val readText = file.readText()
            holder.itemView.setOnClickListener {
                val stringBuilder = StringBuilder()
                showFileContent?.invoke(file.readText())
            }
        }

        override fun getItemCount(): Int {
            return list.size
        }

    }

}