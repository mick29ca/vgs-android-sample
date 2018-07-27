package com.verygoodsecurity.samples.http

data class HttpbinResponse<out T> (val json: T)