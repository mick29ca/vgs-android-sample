package com.verygoodsecurity.samples.views

import android.text.TextUtils
import android.text.Editable
import android.text.TextWatcher


/**
 * Formats the watched EditText to a credit card number.
 */
class FourDigitCardFormatWatcher : TextWatcher {

    override fun onTextChanged(txt: CharSequence, start: Int, before: Int, count: Int) {}

    override fun beforeTextChanged(txt: CharSequence, start: Int, count: Int, after: Int) {}

    override fun afterTextChanged(txt: Editable) {
        if (txt.isNotEmpty() && txt.length % 5 == 0) {
            val ch = txt[txt.length - 1]
            if (space == ch) {
                txt.delete(txt.length - 1, txt.length)
            }
        }
        if (txt.isNotEmpty() && txt.length % 5 == 0) {
            val c = txt[txt.length - 1]
            if (Character.isDigit(c) && TextUtils.split(txt.toString(), space.toString()).size <= 3) {
                txt.insert(txt.length - 1, space.toString())
            }
        }
    }

    companion object {
        const val space = ' '
    }
}