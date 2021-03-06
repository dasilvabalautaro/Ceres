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
import com.globalhiddenodds.ceres.presentation.components.ItemSaleAdapter
import com.globalhiddenodds.ceres.presentation.data.SaleModel
import com.globalhiddenodds.ceres.presentation.interfaces.ILoadDataView
import com.globalhiddenodds.ceres.tools.Constants
import com.globalhiddenodds.ceres.tools.UtilsFormat
import kotlinx.coroutines.experimental.async

class SalesFragment: BaseFragment(), ILoadDataView {

    private var adapter: ItemSaleAdapter? = null
    private var listSale: ArrayList<SaleModel> = ArrayList()
    private var saleModel: SaleModel? = null
    @BindView(R.id.rv_sales)
    @JvmField var rvSales: RecyclerView? = null
    @BindView(R.id.sr_sales)
    @JvmField var srSales: SwipeRefreshLayout? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.view_sales,
                container,false)
        ButterKnife.bind(this, root)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getSalesPresenter.view = this
        setupRecyclerView()
        setupSwipeRefresh()
        getSalesDay()
    }

    private fun getSalesDay(){
        getSalesPresenter.executeGetList(loadPack())
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
        rvSales!!.setHasFixedSize(true)
        rvSales!!.layoutManager = LinearLayoutManager(activity,
                LinearLayoutManager.VERTICAL, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            UtilsFormat.addDecorationRecycler(rvSales!!, context!!)
        }
        adapter = ItemSaleAdapter{
            this.saleModel = it

        }
        rvSales!!.adapter = adapter
    }

    private fun setupSwipeRefresh() = srSales!!.setOnRefreshListener(
            this::refreshData)

    private fun refreshData(){
        getSalesDay()
        srSales!!.isRefreshing = false
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
                listSale = ArrayList(objList
                        .filterIsInstance<SaleModel>() as ArrayList)
                adapter!!.setObjectList(listSale)
                activity!!.runOnUiThread {
                    rvSales!!.scrollToPosition(0)
                }
            }

        }else{
            context!!.toast(context!!.getString(R.string.list_not_found))
        }
    }
}