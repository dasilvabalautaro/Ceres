package com.globalhiddenodds.ceres.presentation.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.globalhiddenodds.ceres.R

class ItemPayView: FrameLayout {

    constructor(context: Context): super(context)
    constructor(context: Context, attributeSet: AttributeSet):
            super(context, attributeSet)

    @BindView(R.id.tv_id)
    @JvmField var tvId: TextView? = null
    @BindView(R.id.tv_date)
    @JvmField var tvDate: TextView? = null

    @BindView(R.id.tv_mount)
    @JvmField var tvMount: TextView? = null
    @BindView(R.id.tv_client)
    @JvmField var tvClient: TextView? = null
    @BindView(R.id.tv_operator)
    @JvmField var tvOperator: TextView? = null
    init {
        LayoutInflater.from(context)
                .inflate(R.layout.view_item_pay, this, true)
        ButterKnife.bind(this)
    }


}