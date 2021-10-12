package com.liyihuanx.module_logutil

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_printer.*

/**
 * @author created by liyihuanx
 * @date 2021/10/12
 * @description: 想把可视化log做成弹窗，但是没用！ 暂时没思路先放着
 */
class ViewPrinterDialog : DialogFragment(), LogPrinter {

    private val adapter = NewLogAdapter()
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parent = dialog?.window?.findViewById<ViewGroup>(android.R.id.content) ?: container
        val view = inflater.inflate(R.layout.view_printer, parent, false)

        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setGravity(Gravity.BOTTOM)
        recyclerView = view.findViewById(R.id.rvViewPrinter)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }


    override fun print(config: LogConfig, level: Int, tag: String?, printString: String) {
        adapter.addItem(LogBean(System.currentTimeMillis(), level, tag, printString))
        recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
    }


    inner class NewLogAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val list: MutableList<LogBean> = java.util.ArrayList()

        fun addItem(logItem: LogBean) {
            list.add(logItem)
            notifyItemInserted(list.size - 1)
        }

        fun clearLog() {
            list.clear()
            notifyDataSetChanged()
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = layoutInflater.inflate(R.layout.item_view_log, parent, false)
            return object : RecyclerView.ViewHolder(itemView) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val tagView = holder.itemView.findViewById<TextView>(R.id.tag)
            val messageView = holder.itemView.findViewById<TextView>(R.id.message)

            val logItem = list[position]
            val color = getHighlightColor(logItem.level)
            tagView.setTextColor(color)
            tagView.setTextColor(color)
            tagView.text = logItem.getFlattened()
            messageView.text = logItem.log

        }

        override fun getItemCount(): Int {
            return list.size
        }

        private fun getHighlightColor(logLevel: Int): Int {
            return when (logLevel) {
                LogType.V -> -0x444445
                LogType.D -> -0x1
                LogType.I -> -0x9578a7
                LogType.W -> -0x444ad7
                LogType.E -> -0x9498
                else -> -0x100
            }
        }
    }

}