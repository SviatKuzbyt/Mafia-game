package com.sviatkuzbyt.mafia.ui.help.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sviatkuzbyt.mafia.data.help.HelpListData
import com.sviatkuzbyt.mafia.data.help.repositories.HelpDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HelpListViewModel(application: Application): AndroidViewModel(application) {
    val list = MutableLiveData<Array<HelpListData>>()

    init {
        viewModelScope.launch(Dispatchers.IO){
            list.postValue(HelpDBRepository(application).getHelpList())
        }
    }
}