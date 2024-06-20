package com.soul.basekotlinui.error

/**
 * Description: 接口请求错误
 * Author: 祝明
 * CreateDate: 2023/6/1 19:49
 * UpdateUser:
 * UpdateDate: 2023/6/1 19:49
 * UpdateRemark:
 */
class ApiException(var code: Int = -1, var msg: String) : RuntimeException(msg) {


}