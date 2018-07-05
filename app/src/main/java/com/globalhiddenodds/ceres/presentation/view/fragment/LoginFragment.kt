package com.globalhiddenodds.ceres.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import com.globalhiddenodds.ceres.App
import com.globalhiddenodds.ceres.R
import com.globalhiddenodds.ceres.models.persistent.preferences.PreferenceHelper
import com.globalhiddenodds.ceres.models.persistent.preferences.PreferenceHelper.set
import com.globalhiddenodds.ceres.presentation.data.UserModel
import com.globalhiddenodds.ceres.presentation.interfaces.ILoadDataView
import com.globalhiddenodds.ceres.presentation.view.activity.ScrollingActivity
import com.globalhiddenodds.ceres.presentation.view.activity.WebActivity
import com.globalhiddenodds.ceres.tools.Constants
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Cancellable
import io.reactivex.schedulers.Schedulers

class LoginFragment: BaseFragment(), ILoadDataView {
    @BindView(R.id.et_user_sign_in)
    @JvmField var etUserSignIn: EditText? = null
    @BindView(R.id.et_password_sign_in)
    @JvmField var etPasswordSignIn: EditText? = null
    @BindView(R.id.bt_send_sign_in)
    @JvmField var btSendSignIn: Button? = null

    private var userModel: UserModel? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.view_sign_in,
                container,false)
        ButterKnife.bind(this, root)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginPresenter.view = this
    }

    override fun onStart() {
        super.onStart()
        disposable.add( actionSingUpButtonClickObservable()
                .observeOn(Schedulers.newThread())
                .map { validate ->
                    run{
                        if (validate){

                            if ((activity!!.application as App)
                                            .connectionNetwork
                                            .checkConnect()){
                                this.loginPresenter.executeVerifyLogin(loadPack())

                            }else{


                            }

                            return@map resources
                                    .getString(R.string.data_sent)

                        }else{
                            return@map resources
                                    .getString(R.string.invalid_form)
                        }
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result -> context!!.toast(result)})
    }
    private fun loadPack(): MutableMap<String, Any>{
        val pack: MutableMap<String, Any> = HashMap()
        pack[Constants.user_name] = etUserSignIn?.text!!.trim().toString()
        pack[Constants.password] = etPasswordSignIn?.text!!.trim().toString()
        return pack
    }

    private fun validate(): Boolean{
        return (etUserSignIn!!.text.isNotEmpty() &&
                etPasswordSignIn!!.text.isNotEmpty())
    }

    private fun actionSingUpButtonClickObservable(): Observable<Boolean> {
        return Observable.create {
            e: ObservableEmitter<Boolean>? ->
            btSendSignIn!!.setOnClickListener {
                e!!.onNext(validate())
            }
            e!!.setCancellable { Cancellable{
                btSendSignIn!!.setOnClickListener(null)
            } }
        }
    }

    override fun showMessage(message: String) {
        context!!.toast(message)
    }

    override fun showError(message: String) {
        context!!.toast(message)
    }

    override fun <T> executeTask(obj: T) {
        if (obj != null){
            this.userModel = (obj as UserModel)
            val prefs = PreferenceHelper.customPrefs(context!!,
                    Constants.preference_ceres)
            prefs[Constants.token] = this.userModel!!.token
            println("token: ${this.userModel!!.token}")
            activity!!.navigate<ScrollingActivity>()
            activity!!.finish()
        }
    }

    override fun <T> executeTask(objList: List<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}