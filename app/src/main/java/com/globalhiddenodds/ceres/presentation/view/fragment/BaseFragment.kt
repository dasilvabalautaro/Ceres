package com.globalhiddenodds.ceres.presentation.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.globalhiddenodds.ceres.App
import com.globalhiddenodds.ceres.dagger.PresenterModule
import com.globalhiddenodds.ceres.presentation.presenter.UrlVideoPresenter
import javax.inject.Inject

abstract class BaseFragment: Fragment() {

    private val Fragment.app
        get() = activity!!.application as App

    private val component by lazy { app.
            getAppComponent().plus(PresenterModule())}
    @Inject
    lateinit var urlVideoPresenter: UrlVideoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)

    }

    fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()


}