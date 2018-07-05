package com.globalhiddenodds.ceres.presentation.view.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.globalhiddenodds.ceres.App
import com.globalhiddenodds.ceres.dagger.PresenterModule
import com.globalhiddenodds.ceres.presentation.presenter.LoginPresenter
import com.globalhiddenodds.ceres.presentation.presenter.UrlVideoPresenter
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseFragment: Fragment() {

    private val Fragment.app
        get() = activity!!.application as App

    private val component by lazy { app.
            getAppComponent().plus(PresenterModule())}
    @Inject
    lateinit var urlVideoPresenter: UrlVideoPresenter

    @Inject
    lateinit var loginPresenter: LoginPresenter

    protected var disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)

    }

    fun Context.toast(message: CharSequence) =
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    inline fun <reified T : Activity> Activity.navigate() {
        val intent = Intent(activity, T::class.java)
        startActivity(intent)
    }
}