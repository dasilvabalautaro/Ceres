package com.globalhiddenodds.ceres.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import com.globalhiddenodds.ceres.R
import com.globalhiddenodds.ceres.presentation.interfaces.ILoadDataView

class VideoFragment: BaseFragment(), ILoadDataView {
    @OnClick(R.id.bt_check)
    fun check(){
        urlVideoPresenter.getUrlVideo()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.view_video,
                container,false)
        ButterKnife.bind(this, root)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        urlVideoPresenter.view = this

    }

    override fun showMessage(message: String) {
        context!!.toast(message)
    }

    override fun showError(message: String) {
        context!!.toast(message)
    }

    override fun <T> executeTask(obj: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> executeTask(objList: List<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}