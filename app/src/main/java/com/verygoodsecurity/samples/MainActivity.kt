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
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            // TODO send email to vgs
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        piiSampleBtn.setOnClickListener {
            startActivity(Intent(this, DataSendActivity::class.java))
        }

        ccSampleBtn.setOnClickListener {
            startActivity(Intent(this, DataSendActivity::class.java))
        }

        bankSampleBtn.setOnClickListener {
            startActivity(Intent(this, DataSendActivity::class.java))
        }
    }
}
