package com.soul.basekotlinui.livedata

import androidx.annotation.NonNull
import androidx.annotation.Nullable


/**
 * Description: 数据状态
 * Author: 祝明
 * CreateDate: 2023/5/4 15:20
 * UpdateUser:
 * UpdateDate: 2023/5/4 15:20
 * UpdateRemark:
 */
class StateData<T> {


    @NonNull
    private var status: DataStatus = DataStatus.CREATED

    @Nullable
    private var data: T? = null

    @Nullable
    private var error: String? = null

    private var code: Int = -1

    fun loading(): StateData<T> {
        status = DataStatus.LOADING
        data = null
        error = null
        return this
    }

    fun success(@NonNull data: T?): StateData<T> {
        status = DataStatus.SUCCESS
        this.data = data
        error = null
        return this
    }

    fun error(code: Int, @NonNull error: String?): StateData<T> {
        status = DataStatus.ERROR
        data = null
        this.code = code
        this.error = error
        return this
    }

    fun complete(): StateData<T> {
        status = DataStatus.COMPLETE
        return this
    }

    @NonNull
    fun getStatus(): DataStatus {
        return status
    }

    @Nullable
    fun getData(): T? {
        return data
    }

    @Nullable
    fun getError(): String? {
        return error
    }

    fun getErrorCode(): Int {
        return code
    }

    enum class DataStatus {
        CREATED, SUCCESS, ERROR, LOADING, COMPLETE
    }


}