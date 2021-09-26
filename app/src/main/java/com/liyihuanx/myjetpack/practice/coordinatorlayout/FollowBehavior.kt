package com.liyihuanx.myjetpack.practice.coordinatorlayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout

/**
 * @author created by liyihuanx
 * @date 2021/9/26
 * @description: 类的描述
 */
class FollowBehavior(context: Context, attrs: AttributeSet? = null) :
    CoordinatorLayout.Behavior<TextView>(context, attrs) {
    /**
     * 判断child的布局是否依赖dependency
     *
     * 根据逻辑来判断返回值，返回false表示不依赖，返回true表示依赖
     *
     * 在一个交互行为中，dependent view的变化决定了另一个相关View的行为。
     * 在这个例子中，Button就是dependent view，因为TextView跟随着它。
     * 实际上dependent view就相当于我们前面介绍的被观察者
     *
     */
    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {
        return dependency is Button
    }


    /**
     * 当 Dependent View 发生变化时，这个方法会被调用，
     * 参数中的child相当于本次交互行为中的观察者，
     * 观察者可以在这个方法中对被观察者的变化做出响应，从而完成一次交互行为。
     */
    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {
        child.x = dependency.x
        child.y = dependency.y + 100
        return true
    }
}