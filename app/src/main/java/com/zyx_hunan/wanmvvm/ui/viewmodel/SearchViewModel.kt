package com.zyx_hunan.wanmvvm.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zyx_hunan.wanmvvm.logic.model.Articledata
import com.zyx_hunan.wanmvvm.logic.net.entrepot.MainRepository
import kotlinx.coroutines.launch

/**
 *
 *
 *
 *@author Administrator
 *
 *@time 2021,2021/7/24 0024,下午 2:00
 */
class SearchViewModel : ViewModel() {

    val searchLiveData = MutableLiveData<List<Articledata>>()

    private val his = MutableLiveData<Int>()

    fun searchData(page: Int, map: Map<String, String>) {
        viewModelScope.launch {
            addHistory(map.getValue("k"))
            val articledata = MainRepository.articleSearch(page, map)
            searchLiveData.postValue(articledata?.data?.articleList)
        }
    }


    fun addHistory(txt: String) {
        Thread { MainRepository.addHistoryRecord(txt) }.start()
    }

    val historyData = Transformations.switchMap(his) {
        MainRepository.findHistory()
    }

    fun findHistory() {
        his.postValue(0)
    }
}