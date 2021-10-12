package com.liyihuanx.module_debugtool

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.liyihuanx.module_base.utils.DisplayUtil
import kotlinx.android.synthetic.main.dialog_debug_tool.*
import java.lang.reflect.Method

/**
 * @author created by liyihuanx
 * @date 2021/10/11
 * @description: 类的描述
 */
class DebugToolDialog : AppCompatDialogFragment() {

    private val debugTools = arrayOf(DebugTool::class.java)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val parent = dialog?.window?.findViewById<ViewGroup>(android.R.id.content) ?: container
        val view = inflater.inflate(R.layout.dialog_debug_tool, parent, false)

        dialog?.window?.setLayout(
            (DisplayUtil.getDisplayWidthInPx(view.context) * 0.7).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_ffffff_r15)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val dividerItemDecoration =
            DividerItemDecoration(view.context, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(
            ContextCompat.getDrawable(
                view.context,
                R.drawable.divider_item_decoration
            )!!
        )

        val functions = mutableListOf<DebugFunction>()
        debugTools.forEach {
            val target = it.getConstructor().newInstance()
            val declaredMethods = target.javaClass.declaredMethods
            declaredMethods.forEach { method ->
                var name = ""
                var desc = ""
                var enable = false
                val annotation = method.getAnnotation(DebugAnnotation::class.java)

                if (annotation != null) {
                    name = annotation.name
                    desc = annotation.desc
                    enable = true
                } else {
                    method.isAccessible = true
                    name = method.invoke(target) as String
                }
                val func = DebugFunction(name, desc, method, enable, target)
                functions.add(func)
            }
        }

        rvDebugTool.addItemDecoration(dividerItemDecoration)
        rvDebugTool.layoutManager = LinearLayoutManager(context)
        rvDebugTool.adapter = DebugToolAdapter(functions)
    }

    inner class DebugToolAdapter(val list: List<DebugFunction>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val itemView = layoutInflater.inflate(R.layout.item_debug_tool, parent, false)
            return object : RecyclerView.ViewHolder(itemView){}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val debugFunction = list[position]
            val tvDebugName = holder.itemView.findViewById<TextView>(R.id.tvDebugName)
            val tvDebugDesc = holder.itemView.findViewById<TextView>(R.id.tvDebugDesc)

            tvDebugName.text = debugFunction.name

            if (TextUtils.isEmpty(debugFunction.desc)){
                tvDebugDesc.visibility = View.GONE
            } else {
                tvDebugDesc.visibility = View.VISIBLE
                tvDebugDesc.text = debugFunction.desc

            }
            if (debugFunction.enable){
                holder.itemView.setOnClickListener {
                    debugFunction.invoke()
                }
            }

        }

        override fun getItemCount(): Int {
            return list.size
        }

    }


    data class DebugFunction(
        val name: String,
        val desc: String,
        val method: Method,
        val enable: Boolean, // 能否点击
        val target: Any  // DebugTool.class
    ) {
        fun invoke(){
            method.invoke(target)
        }
    }

}