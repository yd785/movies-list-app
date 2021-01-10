package com.demo.movies.utils

import android.app.DownloadManager
import android.util.Log
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData
import androidx.lifecycle.map
private const val TAG = "DataAccessOperations"


class DataAccessOperations {

    companion object {
        fun <T, A> getAndSaveDataOperation(
            dbQuery: () -> LiveData<T>,
            networkCall: suspend () -> Resource<A>,
            saveCall: suspend (A) -> Unit
        ): LiveData<Resource<T>> =
            liveData(Dispatchers.IO) {
                emit(Resource.loading())
                val source: LiveData<Resource<T>> = dbQuery.invoke().map { Resource.success(it) }

                val responseStatus = networkCall.invoke()
                if (responseStatus.status == Resource.Status.SUCCESS) {
                    saveCall(responseStatus.data!!)
                    emit(Resource.success(null))
                } else if (responseStatus.status == Resource.Status.ERROR) {
                    emit(Resource.error(responseStatus.message!!))
                    Log.d(TAG, "getAndSaveDataOperation: ${responseStatus.message!!}")
                    emitSource(source)
                }
            }

        fun <T> getDataOperation(dbQuery: () -> LiveData<T>): LiveData<Resource<T>> =
            liveData(Dispatchers.IO) {
                emit(Resource.loading())
                val source: LiveData<Resource<T>> = dbQuery.invoke().map { Resource.success(it) }
                emitSource(source)
            }


    }
}