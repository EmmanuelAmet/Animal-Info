package com.emmanuelamet.animal_info.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.emmanuelamet.animal_info.model.Animal

class ListViewModel(application: Application) : AndroidViewModel(application) {
    val animal by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    fun refresh(){
        getAnimal()
    }

    private fun getAnimal(){
        val a1 = Animal("alligator")
        val a2 = Animal("bee")
        val a3 = Animal("cat")
        val a4 = Animal("lion")
        val a5 = Animal("goat")
        val a6 = Animal("cow")

        val animalList = arrayListOf(a1, a2, a3, a4, a5, a6)

        animal.value = animalList
        loadError.value = false
        loading.value = false
    }
}