package com.globalhiddenodds.ceres.presentation.view.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.globalhiddenodds.ceres.tools.Constants

class WebActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openWebPage(Constants.web_rtc)
    }

    private fun openWebPage(url: String) {
        val webPage = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, webPage)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.navigate<ScrollingActivity>()
        this.finish()

    }

    private inline fun <reified T : Activity> Activity.navigate() {
        val intent = Intent(this@WebActivity, T::class.java)
        startActivity(intent)
    }
}