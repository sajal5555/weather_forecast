package com.assignment.activity

import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.assignment.viewmodel.BaseViewModel
import com.assignment.R
import com.assignment.utilities.HandleOnceLiveEvent


/**
 * @author Sajal Jain
 * @version 1.0
 * @since 13.07.2021
 *
 * Base activity for the application to have a streamlined functionality across Activities
 */
abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    private var mViewDataBinding: B? = null
    private var mViewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        observeForAlert(mViewModel)
        performDataBinding()
        initializeViews(savedInstanceState)


    }

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun getBindingVariable(): Int

    protected abstract fun initializeViews(bundle: Bundle?)

    abstract fun getViewModel(): VM

    fun getViewDataBinding(): B? {
        return mViewDataBinding
    }

    /**
     *
     */
    private fun performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.executePendingBindings()

        //provide lifecycle owner for binding directly to the view from viewModel
        mViewDataBinding?.lifecycleOwner = this
    }

    /**
     *
     * @param viewModel
     */
    private fun observeForAlert(viewModel: BaseViewModel?) {
        if (viewModel == null) return
        viewModel.alertObservable().observe(this, alertObserver)
    }

    private var alertObserver: Observer<HandleOnceLiveEvent<String>> = Observer {
        //check if content already handled, if its handled then no need to consider
        if (!TextUtils.isEmpty(it.contentIfNotHandled)) {
            //call alert
            showDialog(it.peekContent())
        }
    }

    /*
     *
     */
    private fun showDialog(msg: String) {
        AlertDialog.Builder(this).run {
            setTitle(getString(R.string.app_name))
            setMessage(msg)
            setCancelable(false)
            setPositiveButton(
                getString(android.R.string.ok)
            ) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            create().apply { show() }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.alertObservable()?.removeObserver(alertObserver)
    }


    /**
     * Calls when permission request is accepted
     *
     * @param type
     */
    open fun requestPermissionSuccess(type: Int) {}

    /**
     * Calls when permission request is denied
     *
     * @param type
     */
    open fun requestPermissionFail(type: Int) {}

    /**
     * Calls to ask permission
     *
     * @param permissionConstant type of permission request
     * @param permissions        list of permission
     */
    open fun askPermission(
        permissionConstant: Int,
        vararg permissions: String?
    ) {
        if (!checkPermission(*permissions)) {
            requestPermission(permissionConstant, *permissions)
        } else {
            requestPermissionSuccess(permissionConstant)
        }
    }

    /*Check permission*/
    open fun checkPermission(vararg permissions: String?): Boolean {
        for (element in permissions) {
            when (element?.let { ContextCompat.checkSelfPermission(this, it) }) {
                PackageManager.PERMISSION_DENIED -> return false
                else -> {
                }
            }
        }
        return true
    }

    private var permissionConstant = 0

    open fun requestPermission(
        permissionConstant: Int,
        vararg permissionsArr: String?
    ) {
        this.permissionConstant = permissionConstant
        ActivityCompat.requestPermissions(this, permissionsArr, permissionConstant)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionConstant) {
            var permissionsDenied = false
            for (i in permissions.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    permissionsDenied = true
                    break
                }
            }
            if (!permissionsDenied) {
                requestPermissionSuccess(permissionConstant)
            } else {
                requestPermissionFail(permissionConstant)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}