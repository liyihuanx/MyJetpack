package com.liyihuanx.myjetpack

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.liyihuanx.module_base.utils.asToast
import com.liyihuanx.module_base.adapter.BaseDelegateAdapter
import com.liyihuanx.myjetpack.adapter.ColumnAdapter
import com.liyihuanx.module_base.adapter.OnItemClickListener
import com.liyihuanx.myjetpack.adapter.OnePlusNAdapter
import com.liyihuanx.myjetpack.adapter.ScrollFixLayoutAdapter
import com.liyihuanx.myjetpack.adapter.SingleLayoutAdapter
import kotlinx.android.synthetic.main.activity_layout.*
import java.util.*


/**
 * @author created by liyihuanx
 * @date 2021/9/22
 * @description: 类的描述
 */
class VLayoutActivity : AppCompatActivity() {

    // 存放各个模块的适配器
    private var mAdapters: MutableList<DelegateAdapter.Adapter<*>?> = LinkedList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout)

        /**
         * 步骤1：创建RecyclerView & VirtualLayoutManager 对象并进行绑定
         * */
        // 创建RecyclerView对象
        var layoutManager = VirtualLayoutManager(this)
        recycler.layoutManager = layoutManager

        /**
         * 步骤2：设置组件复用回收池
         * */
        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View）：
        val viewPool = RecycledViewPool()
        recycler.setRecycledViewPool(viewPool)
        viewPool.setMaxRecycledViews(0, 10)

        /**
         * 步骤3:设置需要存放的数据
         * */

        /**
         * 步骤4:根据数据列表,创建对应的LayoutHelper
         * */

        /**
         * 设置1拖N布局
         */
        val newAdapter = OnePlusNAdapter()
        val OnePlusN = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val newOnePlusN = arrayListOf(1, 2, 3, 4, 5, 6)

        newAdapter.setNewInstance(OnePlusN)
        newAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(
                adapter: BaseDelegateAdapter<*, *>,
                view: View,
                position: Int
            ) {
                "$position".asToast()
            }
        })

        /**
        设置栏格布局
         */
        val columnAdapter = ColumnAdapter()
        val columnAdapterList = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        columnAdapter.setNewInstance(columnAdapterList)
        columnAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(
                adapter: BaseDelegateAdapter<*, *>,
                view: View,
                position: Int
            ) {
                "$position".asToast()
            }
        })

        /**
         * 固定布局
         */
        val singleLayoutAdapter = SingleLayoutAdapter()
        val singleLayoutAdapterList = arrayListOf(1,9)
        singleLayoutAdapter.setNewInstance(singleLayoutAdapterList)
        singleLayoutAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(
                adapter: BaseDelegateAdapter<*, *>,
                view: View,
                position: Int
            ) {
                "$position".asToast()
            }
        })




        /**
         * 固定布局
         */
        val scrollFixLayoutAdapter = ScrollFixLayoutAdapter()
        val scrollFixLayoutAdapterList = arrayListOf(3)
        scrollFixLayoutAdapter.setNewInstance(scrollFixLayoutAdapterList)
        scrollFixLayoutAdapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(
                adapter: BaseDelegateAdapter<*, *>,
                view: View,
                position: Int
            ) {
                "$position".asToast()
            }
        })



        /**
         *步骤5:将生成的LayoutHelper 交给Adapter，并绑定到RecyclerView 对象
         **/

        // 1. 设置Adapter列表（同时也是设置LayoutHelper列表）
        val adapters: MutableList<DelegateAdapter.Adapter<*>> = LinkedList()

        // 2. 将上述创建的Adapter对象放入到DelegateAdapter.Adapter列表里
        adapters.add(newAdapter)
        adapters.add(columnAdapter)
        adapters.add(singleLayoutAdapter)
        adapters.add(scrollFixLayoutAdapter)

        // 3. 创建DelegateAdapter对象 & 将layoutManager绑定到DelegateAdapter
        val delegateAdapter = DelegateAdapter(layoutManager)

        // 4. 将DelegateAdapter.Adapter列表绑定到DelegateAdapter
        delegateAdapter.setAdapters(adapters)

        // 5. 将delegateAdapter绑定到recyclerView
        recycler.adapter = delegateAdapter



        btnRefresh.setOnClickListener {
            newAdapter.setNewInstance(newOnePlusN)
        }
    }
}