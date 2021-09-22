package com.liyihuanx.myjetpack

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper
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
        val listItem = ArrayList<HashMap<String, Any>>()
        for (i in 0..99) {
            val map = HashMap<String, Any>()
            map["ItemTitle"] = "第" + i + "行"
            map["ItemImage"] = R.drawable.icon_load_fail
            listItem.add(map)
        }


        /**
         * 步骤4:根据数据列表,创建对应的LayoutHelper
         * */

        /**
         * 设置1拖N布局
         */
        val onePlusNLayoutHelper = OnePlusNLayoutHelper(3)
        // 公共属性
        // 在构造函数里传入显示的Item数
        // 最多是1拖4,即5个
//        onePlusNLayoutHelper.itemCount = 2 // 设置布局里Item个数

        onePlusNLayoutHelper.setPadding(20, 20, 20, 20) // 设置LayoutHelper的子元素相对LayoutHelper边缘的距离

        onePlusNLayoutHelper.setMargin(20, 20, 20, 20) // 设置LayoutHelper边缘相对父控件（即RecyclerView）的距离

        onePlusNLayoutHelper.bgColor = Color.GRAY // 设置背景颜色

        onePlusNLayoutHelper.aspectRatio = 3f // 设置设置布局内每行布局的宽与高的比


        val onePlusNAdapter = object : MyAdapter(this, onePlusNLayoutHelper, 3, listItem) {
            // 设置需要展示的数据总数,此处设置是5,即1拖4
            // 为了展示效果,通过重写onBindViewHolder()将布局的第一个数据设置为onePlus
            override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
                super.onBindViewHolder(holder, position)
                if (position == 0) {
                    holder.Text.text = "onePlus"
                }
            }
        }

        /**
         *步骤5:将生成的LayoutHelper 交给Adapter，并绑定到RecyclerView 对象
         **/

        // 1. 设置Adapter列表（同时也是设置LayoutHelper列表）
        val adapters: MutableList<DelegateAdapter.Adapter<*>> = LinkedList()

        // 2. 将上述创建的Adapter对象放入到DelegateAdapter.Adapter列表里
        adapters.add(onePlusNAdapter)


        // 3. 创建DelegateAdapter对象 & 将layoutManager绑定到DelegateAdapter
        val delegateAdapter = DelegateAdapter(layoutManager)

        // 4. 将DelegateAdapter.Adapter列表绑定到DelegateAdapter
        delegateAdapter.setAdapters(adapters)


        // 5. 将delegateAdapter绑定到recyclerView
        recycler.adapter = delegateAdapter

    }
}