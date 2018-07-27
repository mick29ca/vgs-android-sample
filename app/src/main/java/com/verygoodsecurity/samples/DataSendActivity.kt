package com.verygoodsecurity.samples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_data_send.*
import android.app.DatePickerDialog
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.Toast
import com.verygoodsecurity.samples.http.*
import com.verygoodsecurity.samples.views.FourDigitCardFormatWatcher
import kotlinx.android.synthetic.main.content_bank.*
import kotlinx.android.synthetic.main.content_card.*
import kotlinx.android.synthetic.main.content_pii.*
import java.util.*
import java.text.SimpleDateFormat
import retrofit2.Retrofit
import kotlin.properties.Delegates
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory


class DataSendActivity : AppCompatActivity() {

    private var httpbinService: HttpbinService by Delegates.notNull()

    companion object {
        const val PII_ACTION = "PII_ACTION"
        const val CC_ACTION = "CC_ACTION"
        const val BANK_ACTION = "BANK_ACTION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_data_send)

        val retrofit = Retrofit.Builder()
                .baseUrl(getString(R.string.vgs_proxy_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        httpbinService = retrofit.create<HttpbinService>(HttpbinService::class.java)

        when (intent.action) {
            PII_ACTION -> initPiiViews()
            CC_ACTION -> initCardViews()
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

            editPiiDob.setText(SimpleDateFormat("MM/dd/yy", Locale.US).format(myCalendar.time))
        }

        editPiiDob.setOnClickListener {
            DatePickerDialog(this@DataSendActivity, date,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        editPiiCc.addTextChangedListener(FourDigitCardFormatWatcher())

        setSubmitClickListener(btnPiiSubmit, {
            httpbinService.post(PiiData(
                    editPiiName.text.toString(),
                    editPiiDob.text.toString(),
                    editPiiSsn.text.toString(),
                    editPiiCc.text.toString()
            ))
        }) {
            textPiiRedactedData.text = getString(R.string.redacted_data)

            textPiiName.text = getString(R.string.piiName, it.name)
            textPiiDob.text = getString(R.string.piiDob, it.dob)
            textPiiSsn.text = getString(R.string.piiSsn, it.ssn)
            textPiiCc.text = getString(R.string.piiCc, it.cc)
        }
    }

    private fun initCardViews() {
        contentStub.layoutInflater.inflate(R.layout.content_card, contentContainer, true)

        editCardCc.addTextChangedListener(FourDigitCardFormatWatcher())

        setSubmitClickListener(btnCardSubmit, {
            httpbinService.post(CardData(
                    editCardName.text.toString(),
                    editCardCc.text.toString(),
                    editCardCvv.text.toString()
            ))
        }) {
            textCardRedactedData.text = getString(R.string.redacted_data)

            textCardName.text = getString(R.string.cardName, it.name)
            textCardCc.text = getString(R.string.cardCc, it.cc)
            textCardCvv.text = getString(R.string.cardCvv, it.cvv)
        }
    }

    private fun initBankViews() {
        contentStub.layoutInflater.inflate(R.layout.content_bank, contentContainer, true)

        setSubmitClickListener(btnBankSubmit, {
            httpbinService.post(BankData(
                    editBankName.text.toString(),
                    editBankAccount.text.toString(),
                    editBankSsn.text.toString()
            ))
        }) {
            textBankRedactedData.text = getString(R.string.redacted_data)

            textBankName.text = getString(R.string.bankName, it.name)
            textBankAccount.text = getString(R.string.bankAccount, it.bankAccount)
            textBankSsn.text = getString(R.string.bankSsn, it.ssn)
        }
    }

    private fun <T> setSubmitClickListener(btn: Button, httpPost: () -> Call<HttpbinResponse<T>>, printFunction: (T) -> Unit) {
        btn.setOnClickListener {
            progressBar.visibility = VISIBLE
            httpPost.invoke()
                    .enqueue(object : Callback<HttpbinResponse<T>> {
                        override fun onResponse(call: Call<HttpbinResponse<T>>?, response: Response<HttpbinResponse<T>>) {
                            printFunction.invoke(response.body()!!.json)
                            progressBar.visibility = GONE
                        }

                        override fun onFailure(call: Call<HttpbinResponse<T>>, t: Throwable?) {
                            Toast.makeText(this@DataSendActivity, "Unexpected Error", Toast.LENGTH_LONG).show()
                            progressBar.visibility = GONE
                        }
                    })
        }
    }
}