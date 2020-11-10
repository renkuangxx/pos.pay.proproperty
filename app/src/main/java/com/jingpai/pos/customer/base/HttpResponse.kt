package com.jingpai.pos.customer.base

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

/**
 * http响应参数实体类
 * 通过Gson解析属性名称需要与服务器返回字段对应,或者使用注解@SerializedName
 * 备注:这里与服务器约定返回格式
 */
class HttpResponse {
    /**
     * 描述信息
     */
    @SerializedName("timestamp")
    var timestamp: String? = null
        private set
    /**
     * 描述信息
     */
    @SerializedName("returnMsg")
    var msg: String? = null
    @SerializedName("message")
    var message: String? = null
    /**
     * 状态码
     */
    @SerializedName("returnCode")
    var code: String? = null
    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    @SerializedName("data")
    var result: JsonElement? = null

    /**
     * 是否成功(这里约定200)
     *
     * @return
     */
    val isSuccess: Boolean
        get() = if (Integer.parseInt(code!!) == 200 || Integer.parseInt(code!!) == 0) true else false

    override fun toString(): String {
        return "[http response]" + "{\"code\": " + code + ",\"timestamp\":" + timestamp + ",\"msg\":" + msg + ",\"result\":" + Gson().toJson(result) + "}"
    }

    fun setTimestamp(timestamp: String?): HttpResponse {
        this.timestamp = timestamp
        return this
    }
}