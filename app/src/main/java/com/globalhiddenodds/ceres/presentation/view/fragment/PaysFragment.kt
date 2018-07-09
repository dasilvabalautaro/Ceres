package com.globalhiddenodds.ceres.presentation.view.fragment

import android.os.Build
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.globalhiddenodds.ceres.R
import com.globalhiddenodds.ceres.models.persistent.preferences.PreferenceHelper
import com.globalhiddenodds.ceres.models.persistent.preferences.PreferenceHelper.get
import com.globalhiddenodds.ceres.presentation.components.ItemPayAdapter
import com.globalhiddenodds.ceres.presentation.components.ItemPayView
import com.globalhiddenodds.ceres.presentation.components.ItemSaleAdapter
import com.globalhiddenodds.ceres.presentation.data.PayModel
import com.globalhiddenodds.ceres.presentation.data.SaleModel
import com.globalhiddenodds.ceres.presentation.interfaces.ILoadDataView
import com.globalhiddenodds.ceres.tools.Constants
import com.globalhiddenodds.ceres.tools.UtilsFormat
import kotlinx.coroutines.experimental.async

class PaysFragment: BaseFragment(), ILoadDataView {

    private var adapter: ItemPayAdapter? = null
    private var listPay: ArrayList<PayModel> = ArrayList()
    private var payModel: PayModel? = null
    @BindView(R.id.rv_pays)
    @JvmField var rvPays: RecyclerView? = null
    @BindView(R.id.sr_pays)
    @JvmField var srPays: SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.view_pays,
                container,false)
        ButterKnife.bind(this, root)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getPaysPresenter.view = this
        setupRecyclerView()
        setupSwipeRefresh()
        getPaysDay()
    }

    private fun getPaysDay(){
        getPaysPresenter.executeGetList(loadPack())
    }

    private fun loadPack(): MutableMap<String, Any>{
        val pack: MutableMap<String, Any> = HashMap()
        val prefs = PreferenceHelper.customPrefs(context!!,
                Constants.preference_ceres)
        val token = prefs[Constants.token, ""]

        pack[Constants.token] = token!!

        return pack
    }

    private fun setupRecyclerView(){
        rvPays!!.setHasFixedSize(true)
        rvPays!!.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UtilsFormat.addDecorationRecycler(rvPays!!, context!!)
        }
        adapter = ItemPayAdapter{
            this.payModel = it

        }
        rvPays!!.adapter = adapter
    }

    private fun setupSwipeRefresh() = srPays!!.setOnRefreshListener(
            this::refreshData)

    private fun refreshData(){
        getPaysDay()
        srPays!!.isRefreshing = false
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
        if (objList.isNotEmpty()){
            async {
                listPay = ArrayList(objList
                        .filterIsInstance<PayModel>() as ArrayList)
                adapter!!.setObjectList(listPay)
                activity!!.runOnUiThread {
                    rvPays!!.scrollToPosition(0)
                }
            }

        }else{
            context!!.toast(context!!.getString(R.string.list_not_found))
        }
    }

}