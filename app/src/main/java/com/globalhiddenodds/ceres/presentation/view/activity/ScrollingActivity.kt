package com.globalhiddenodds.ceres.presentation.view.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.globalhiddenodds.ceres.R
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import butterknife.OnClick
import com.globalhiddenodds.ceres.presentation.view.fragment.PaysFragment
import com.globalhiddenodds.ceres.presentation.view.fragment.SalesFragment


class ScrollingActivity: AppCompatActivity() {
    @BindView(R.id.design_bottom_sheet)
    @JvmField var bottomSheet: View? = null
    @OnClick(R.id.bt_video)
    fun goVideo(){
        this.navigate<WebActivity>()
        this.finish()
    }
    @OnClick(R.id.bt_pays)
    fun goPays(){
        val paysFragment = PaysFragment()
        addFragment(paysFragment)
    }
    @OnClick(R.id.bt_sales)
    fun goSales(){
        val salesFragment = SalesFragment()
        addFragment(salesFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        ButterKnife.bind(this)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportActionBar!!.setBackgroundDrawable(this.getDrawable(R.drawable.head_back))
        }else{
            supportActionBar!!.setBackgroundDrawable(this.resources
                    .getDrawable(R.drawable.head_back))
        }
        val salesFragment = SalesFragment()
        addFragment(salesFragment)
        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events

                println("Buttom sheet onSlide")

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                println("Buttom sheet nuevo estado")
                // React to state change
            }
        })
    }

    @SuppressLint("PrivateResource")
    private fun addFragment(newFragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in,
                        R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.flContentScroll, newFragment, newFragment.javaClass.simpleName)
                .commit()
    }

    private inline fun <reified T : Activity> Activity.navigate() {
        val intent = Intent(this@ScrollingActivity, T::class.java)
        startActivity(intent)
    }

}