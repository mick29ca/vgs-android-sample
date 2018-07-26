package com.verygoodsecurity.samples

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_data_send.*
import android.app.DatePickerDialog
import android.widget.Button
import android.widget.Toast
import com.verygoodsecurity.samples.http.BankData
import com.verygoodsecurity.samples.http.CardData
import com.verygoodsecurity.samples.http.HttpbinService
import com.verygoodsecurity.samples.http.PiiData
import com.verygoodsecurity.samples.views.FourDigitCardFormatWatcher
import kotlinx.android.synthetic.main.content_bank.*
import kotlinx.android.synthetic.main.content_card.*
import kotlinx.android.synthetic.main.content_pii.*
import java.util.*
import java.text.SimpleDateFormat
import retrofit2.Retrofit
import kotlin.properties.Delegates
import okhttp3.ResponseBody
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

        setSubmitClickListener(btnPiiSubmit) {
            httpbinService.post(PiiData(
                    editPiiName.text.toString(),
                    editPiiDob.text.toString(),
                    editPiiSsn.text.toString(),
                    editPiiCc.text.toString()
            ))
        }
    }

    private fun initCardViews() {
        contentStub.layoutInflater.inflate(R.layout.content_card, contentContainer, true)

        editCardCc.addTextChangedListener(FourDigitCardFormatWatcher())

        setSubmitClickListener(btnCardSubmit) {
            httpbinService.post(CardData(
                    editCardName.text.toString(),
                    editCardCc.text.toString(),
                    editCardCvv.text.toString()
            ))
        }
    }

    private fun initBankViews() {
        contentStub.layoutInflater.inflate(R.layout.content_bank, contentContainer, true)

        setSubmitClickListener(btnBankSubmit) {
            httpbinService.post(BankData(
                    editBankName.text.toString(),
                    editBankAccount.text.toString(),
                    editBankSsn.text.toString()
            ))
        }
    }

    private fun setSubmitClickListener(btn: Button, httpPost: () -> Call<ResponseBody>) {
        btn.setOnClickListener {
            httpPost.invoke()
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                            Toast.makeText(this@DataSendActivity, response.body()!!.string(), Toast.LENGTH_LONG).show()
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable?) {
                            Toast.makeText(this@DataSendActivity, "Unexpected Error", Toast.LENGTH_LONG).show()
                        }
                    })
        }
    }
}