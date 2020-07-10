package com.emmanuelamet.animal_info.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.emmanuelamet.animal_info.model.Animal
import com.emmanuelamet.animal_info.model.AnimalApiService
import com.emmanuelamet.animal_info.model.ApiKey
import com.emmanuelamet.animal_info.util.SharedPreferencesHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel(application: Application) : AndroidViewModel(application) {
    val animal by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    private val apiService = AnimalApiService()
    private val prefs = SharedPreferencesHelper(getApplication())
    private var invalidApiKey = false

    fun refresh(){
        invalidApiKey = false
        loading.value = true
        val key = prefs.getApiKey()
        if(key.isNullOrEmpty()){
            getKey()
        }else{
            getAnimal(key)
        }
    }

    fun hardRefresh(){
        loading.value = true
        getKey()
    }

    private fun getKey(){
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiKey>(){
                    override fun onSuccess(key: ApiKey) {
                        if(key.key.isNullOrEmpty()){
                            loadError.value = true
                            loading.value = false
                        }else{
                            prefs.saveApiKey(key.key)
                            getAnimal(key.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                    }

                })
        )
    }

    private fun getAnimal(key: String){
        disposable.add(
            apiService.getAnimal(key)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Animal>>(){
                override fun onSuccess(list: List<Animal>) {
                    loadError.value = false
                    animal.value = list
                    loading.value = false
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    if(!invalidApiKey){
                        invalidApiKey = true
                        getKey()
                    }else{
                        loading.value = false
                        loadError.value = true
                        animal.value = null
                    }

                }

            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}