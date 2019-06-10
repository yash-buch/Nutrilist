package com.binc.nutrilist.viewmodels

import androidx.lifecycle.ViewModel

class RestrictionListViewModel: ViewModel() {
    var restrictionList: ArrayList<String> = ArrayList()

    fun addToList(item: String) {
        restrictionList.add(item)
    }

    fun removeFromList(position: Int) {
        restrictionList.removeAt(position)
    }

    fun getList(): ArrayList<String> {
        return restrictionList
    }

}