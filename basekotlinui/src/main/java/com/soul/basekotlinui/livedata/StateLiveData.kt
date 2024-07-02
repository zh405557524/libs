package com.soul.basekotlinui.livedata

import androidx.lifecycle.MutableLiveData

/**
 * Description: 可以传递数据状态的LiveData
 * Author: 祝明
 * CreateDate: 2023/5/4 15:25
 * UpdateUser:
 * UpdateDate: 2023/5/4 15:25
 * UpdateRemark:
 */
class StateLiveData<T> : MutableLiveData<StateData<T>>() {

    var state = StateData.DataStatus.CREATED

    /**
     * Use this to put the Data on a LOADING Status
     */
    fun postLoading() {
        postValue(StateData<T>().loading())
        state = StateData.DataStatus.LOADING
    }

    /**
     * Use this to put the Data on a ERROR DataStatus
     * @param throwable the error to be handled
     */
    fun postError(code: Int, throwable: String?) {
        postValue(StateData<T>().error(code, throwable))
        state = StateData.DataStatus.ERROR
    }

    /**
     * Use this to put the Data on a SUCCESS DataStatus
     * @param data
     */
    fun postSuccess(data: T) {
        postValue(StateData<T>().success(data))
        state = StateData.DataStatus.SUCCESS
    }

    /**
     * Use this to put the Data on a COMPLETE DataStatus
     */
    fun postComplete() {
        postValue(StateData<T>().complete())
        state = StateData.DataStatus.LOADING
    }

    fun setSuccess(data: T) {
        state = StateData.DataStatus.SUCCESS
        value = StateData<T>().success(data)
    }

}