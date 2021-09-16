package com.liyihuanx.module_base.utils

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.*
import com.liyihuanx.module_base.viewmodel.BaseViewModel
import java.lang.reflect.InvocationTargetException

/**
 * @author created by liyihuanx
 * @date 2021/9/15
 * @description: 类的描述
 */


/**** Activity创建ViewModel的方式 ****/
class ActivityVmFac(
    private val application: Application,
    private val bundle: Bundle?,
    private val act: FragmentActivity
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            val constructor1 = modelClass.getConstructor(
                Application::class.java,
                Bundle::class.java
            )
            val vm: T = constructor1.newInstance(application, bundle)

            if (vm is BaseViewModel) {
                vm.finishedActivityCall = { act.finish() }
                vm.getFragmentManagerCall = { act.supportFragmentManager }
//                if (act is LoadingObserverView) {
//                    vm.showLoadingCall = {
//                        act.showLoading(it)
//                    }
//                }
                act.lifecycle.addObserver(vm)
            }
            vm

        } catch (e: NoSuchMethodException) {
            val vm2 = super.create(modelClass)

            if (vm2 is BaseViewModel) {
                vm2.finishedActivityCall = { act.finish() }
                vm2.getFragmentManagerCall = { act.supportFragmentManager }
//                if (act is LoadingObserverView) {
//                    vm2.showLoadingCall = {
//                        act.showLoading(it)
//                    }
//                }
                act.lifecycle.addObserver(vm2)
            }
            vm2
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}


inline fun <reified VM : BaseViewModel> FragmentActivity.createVm(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        ActivityVmFac(application, intent.extras, this);
    }
    val vm = ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
    val act = this
    lifecycleScope.launchWhenCreated {
        Log.d("createVm", "vm.value.mData==null  ${vm.value.javaClass} ${vm.value.mData == null}")
    }

    return vm
}


inline fun <reified VM : BaseViewModel> FragmentActivity.lazyVm(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val factoryPromise = factoryProducer ?: {
        ActivityVmFac(application, intent.extras, this);
    }
    val vm = ViewModelLazy(VM::class, { viewModelStore }, factoryPromise)
    return vm
}



/**** Fragment创建ViewModel的方式 ****/
class FragmentVmFac(
    private val application: Application,
    private val bundle: Bundle?,
    private val f: Fragment
) : ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return try {
            val constructor1 = modelClass.getConstructor(
                Application::class.java,
                Bundle::class.java
            )
            val vm: T = constructor1.newInstance(application, bundle)

            if (vm is BaseViewModel) {
                vm.finishedActivityCall = { f.activity?.finish() }
                vm.getFragmentManagerCall = { f.childFragmentManager }
//                if (f is LoadingObserverView) {
//                    vm.showLoadingCall = {
//                        f.showLoading(it)
//                    }
//                }
                f.lifecycle.addObserver(vm)
            }
            vm

        } catch (e: NoSuchMethodException) {
            val vmodel = super.create(modelClass)
            if (vmodel is BaseViewModel) {
                vmodel.finishedActivityCall = { f.activity?.finish() }
                vmodel.getFragmentManagerCall = { f.childFragmentManager }
//                if (f is LoadingObserverView) {
//                    vmodel.showLoadingCall = {
//                        f.showLoading(it)
//                    }
//                }
                f.lifecycle.addObserver(vmodel)
            }

            vmodel as T
        } catch (e: IllegalAccessException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InstantiationException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException("Cannot create an instance of $modelClass", e)
        }
    }
}



@MainThread
inline fun <reified VM : BaseViewModel> Fragment.createVm(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = {
        FragmentVmFac(
            activity!!.application,
            arguments,
            this
        )
    }
): Lazy<VM> {
    val vm = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer);
    lifecycleScope.launchWhenCreated {
        Log.d("createVm", "vm.value.mData==null  ${vm.value.javaClass} ${vm.value.mData == null}")
    }
    return vm
}



@MainThread
inline fun <reified VM : BaseViewModel> Fragment.lazyVm(
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = {
        FragmentVmFac(
            activity!!.application,
            arguments,
            this
        )
    }
): Lazy<VM> {
    val vm = createViewModelLazy(VM::class, { ownerProducer().viewModelStore }, factoryProducer);

    return vm
}


@MainThread
inline fun <reified VM : BaseViewModel> Fragment.activityVm(
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(VM::class, { requireActivity().viewModelStore },
    factoryProducer ?: {

        val act = requireActivity()
        ActivityVmFac(act.application, act.intent.extras, act);
    }
)