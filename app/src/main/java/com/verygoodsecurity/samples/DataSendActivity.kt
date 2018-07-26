package com.verygoodsecurity.samples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_data_send.*
import android.app.DatePickerDialog
import com.verygoodsecurity.samples.views.FourDigitCardFormatWatcher
import kotlinx.android.synthetic.main.content_pii.*
import java.util.*
import java.text.SimpleDateFormat


class DataSendActivity: AppCompatActivity() {

    companion object {
        const val PII_ACTION = "PII_ACTION"
        const val CC_ACTION = "CC_ACTION"
        const val BANK_ACTION = "BANK_ACTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_data_send)

        when (intent.action) {
            PII_ACTION -> initPiiViews()
            CC_ACTION -> initCcViews()
            BANK_ACTION -> initBankViews()
            else -> throw IllegalStateException("Invalid action passed " + intent.action)
        }
    }

    private fun initPiiViews() {
        contentStub.layoutInflater.inflate(R.layout.content_pii, contentContainer, true)

        val myCalendar = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            editDob.setText(SimpleDateFormat("MM/dd/yy", Locale.US).format(myCalendar.time))
        }

        editDob.setOnClickListener {
            DatePickerDialog(this@DataSendActivity, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        editCc.addTextChangedListener(FourDigitCardFormatWatcher())
    }

    private fun initCcViews() {
        contentStub.layoutInflater.inflate(R.layout.content_card, contentContainer, true)

        editCc.addTextChangedListener(FourDigitCardFormatWatcher())
    }

    private fun initBankViews() {
        contentStub.layoutInflater.inflate(R.layout.content_bank, contentContainer, true)

        editCc.addTextChangedListener(FourDigitCardFormatWatcher())
    }
}