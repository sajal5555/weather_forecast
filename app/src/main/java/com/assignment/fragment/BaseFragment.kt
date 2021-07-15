package com.assignment.fragment

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.assignment.viewmodel.BaseViewModel
import com.assignment.R
import com.assignment.interfaces.ToolbarNavInterface
import com.assignment.utilities.HandleOnceLiveEvent

/**
 *
 */
abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment(),
    ToolbarNavInterface {

    // Root view for this fragment
    private var mFragmentView: View? = null
    private var mViewDataBinding: B? = null
    private var mViewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
        observeForAlert(mViewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Reset for every new form instance
        //  FormValidationState.reset()

        mViewDataBinding = DataBindingUtil.inflate(inflater, initializeLayoutId(), container, false)
        mFragmentView = mViewDataBinding?.root
        return mFragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding?.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding?.lifecycleOwner = this
        mViewDataBinding?.executePendingBindings()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initializeViews(savedInstanceState)
        initializeController()
        initObserver()
    }

    private fun initObserver() {
        getViewModel().startActivity.observe(this.viewLifecycleOwner, {
            it?.getString("ClassName")?.let { it1 ->
                startActivity(Intent(context, Class.forName(it1)).apply {
                    putExtras(it)
                })
            }
        })
    }


    /**
     * @return returns layout id of the fragment.
     */
    protected abstract fun initializeLayoutId(): Int

    protected abstract fun getViewModel(): VM

    protected abstract fun getBindingVariable(): Int

    fun getViewDataBinding(): B? {
        return mViewDataBinding
    }

    /**
     * Initialize fragment controller.
     */
    protected abstract fun initializeController()

    /**
     * Initialize fragment views.
     */
    protected abstract fun initializeViews(savedInstanceState: Bundle?)

    /**
     * Observer for common alerts across the app
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

    /**
     *
     */
    private fun showDialog(msg: String) {
        this.context?.let { context ->
            val builder =
                AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.app_name))
            builder.setMessage(msg)
            builder.setCancelable(false)
            builder.setPositiveButton(
                context.getString(android.R.string.ok)
            ) { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.alertObservable()?.removeObserver(alertObserver)
    }

    override fun displayHomeAsUpEnabled(isEnbale: Boolean) {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(isEnbale)
    }

    override fun showOrHideTitle(visibility: Int) {
//        if (activity is HomeActivity) {
//            (activity as HomeActivity).showOrHideTitle(visibility)
//        }
    }


    override fun onStop() {
        super.onStop()
        getViewModel().startActivity.removeObservers(this)
        getViewModel().startActivity.value = null
    }
}