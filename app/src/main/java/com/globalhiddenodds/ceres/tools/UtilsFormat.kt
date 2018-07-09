package com.globalhiddenodds.ceres.tools

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import com.globalhiddenodds.ceres.R

object UtilsFormat {
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun addDecorationRecycler(rv: RecyclerView, context: Context){
        val horizontalDecoration =
                DividerItemDecoration(rv.context,
                        DividerItemDecoration.VERTICAL)
        val horizontalDivider: Drawable = context
                .getDrawable(R.drawable.horizontal_divider)
        horizontalDecoration.setDrawable(horizontalDivider)
        rv.addItemDecoration(horizontalDecoration)
    }
}