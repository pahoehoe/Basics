package cn.lvsong.lib.net.response


import androidx.annotation.Keep

@Keep
data class Response<T>(
    // 请求的数据内容, Object/List
        val data: T?,
    // 请求状态码
        val code: Int,
    // 提示信息
        val msg: String=""
)