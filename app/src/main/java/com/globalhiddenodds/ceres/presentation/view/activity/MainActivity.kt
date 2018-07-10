package com.globalhiddenodds.ceres.presentation.view.activity

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.globalhiddenodds.ceres.R
import com.globalhiddenodds.ceres.presentation.view.fragment.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportActionBar!!.setBackgroundDrawable(this.getDrawable(R.drawable.head_back))
        }else{
            supportActionBar!!.setBackgroundDrawable(this.resources
                    .getDrawable(R.drawable.head_back))
        }
        val loginFragment = LoginFragment()
        addFragment(loginFragment)
    }

    @SuppressLint("PrivateResource")
    private fun addFragment(newFragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in,
                        R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.flContent, newFragment, newFragment.javaClass.simpleName)
                .commit()
    }


}
