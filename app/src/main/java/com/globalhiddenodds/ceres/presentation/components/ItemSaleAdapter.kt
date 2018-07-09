package com.globalhiddenodds.ceres.presentation.components

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.globalhiddenodds.ceres.R
import com.globalhiddenodds.ceres.presentation.data.SaleModel

class ItemSaleAdapter (private val listener:
        (SaleModel) -> Unit): RecyclerView.Adapter<ItemSaleAdapter.ViewHolder>(){
    private val items: ArrayList<SaleModel> = ArrayList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(items[position], listener)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view: ItemSaleView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_row_sale,
                        parent, false) as ItemSaleView
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    fun setObjectList(itemList: ArrayList<SaleModel>){
        val diffResult: DiffUtil.DiffResult = DiffUtil
                .calculateDiff(DataListDiffCallback(this.items, itemList))
        this.items.clear()
        this.items.addAll(itemList)
        diffResult.dispatchUpdatesTo(this)

    }


    class ViewHolder(private val itemSaleView: ItemSaleView):
            RecyclerView.ViewHolder(itemSaleView){

        fun bind(item: SaleModel?, listener:
        (SaleModel) -> Unit) = with(itemSaleView){
            if (item != null){
                tvId!!.text = item.id
                tvDate!!.text = item.date
                tvClient!!.text = item.client
                tvMount!!.text = item.mount
                tvPrice!!.text = item.price
                tvWeight!!.text = item.weight
                tvProduct!!.text = item.product
                tvOperator!!.text = item.operator

            }

        }
    }
}
