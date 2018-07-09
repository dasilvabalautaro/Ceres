package com.globalhiddenodds.ceres.presentation.components

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.globalhiddenodds.ceres.R
import com.globalhiddenodds.ceres.presentation.data.PayModel

class ItemPayAdapter (private val listener:
                      (PayModel) -> Unit): RecyclerView.Adapter<ItemPayAdapter.ViewHolder>(){
    private val items: ArrayList<PayModel> = ArrayList()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
            holder.bind(items[position], listener)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ViewHolder {
        val view: ItemPayView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_row_pay,
                        parent, false) as ItemPayView
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    fun setObjectList(itemList: ArrayList<PayModel>){
        val diffResult: DiffUtil.DiffResult = DiffUtil
                .calculateDiff(DataListDiffCallback(this.items, itemList))
        this.items.clear()
        this.items.addAll(itemList)
        diffResult.dispatchUpdatesTo(this)

    }


    class ViewHolder(private val itemPayView: ItemPayView):
            RecyclerView.ViewHolder(itemPayView){

        fun bind(item: PayModel?, listener:
        (PayModel) -> Unit) = with(itemPayView){
            if (item != null){
                tvId!!.text = item.id
                tvDate!!.text = item.date
                tvClient!!.text = item.client
                tvMount!!.text = item.mount
                tvOperator!!.text = item.operator

            }

        }
    }
}