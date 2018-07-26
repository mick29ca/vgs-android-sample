package com.verygoodsecurity.samples

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarMain)

        fab.setOnClickListener { view ->
            // TODO send email to vgs
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        piiSampleBtn.setOnClickListener { startActivity(newDataSendIntent(DataSendActivity.PII_ACTION)) }
        ccSampleBtn.setOnClickListener { startActivity(newDataSendIntent(DataSendActivity.CC_ACTION)) }
        bankSampleBtn.setOnClickListener { startActivity(newDataSendIntent(DataSendActivity.BANK_ACTION)) }
    }

    private fun newDataSendIntent(action: String): Intent {
        val intent = Intent(this, DataSendActivity::class.java)
        intent.action = action
        return intent
    }
}
