package com.globalhiddenodds.ceres.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.globalhiddenodds.ceres.R
import com.globalhiddenodds.ceres.presentation.interfaces.ILoadDataView

class SalesFragment: BaseFragment(), ILoadDataView {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.view_sales,
                container,false)
        ButterKnife.bind(this, root)
        return root
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