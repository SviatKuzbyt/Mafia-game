package com.sviatkuzbyt.mafia.ui.help.item

import android.app.Application
import androidx.lifecycle.*
import com.sviatkuzbyt.mafia.data.help.HelpItemData
import com.sviatkuzbyt.mafia.data.help.repositories.HelpDBRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HelpViewModel(application: Application, id: Int): AndroidViewModel(application){
    val helpData = MutableLiveData<HelpItemData>()

    init {
        viewModelScope.launch(Dispatchers.IO){
            helpData.postValue(HelpDBRepository(application).getHelpItem(id))
        }
    }
}

@Suppress("UNCHECKED_CAST")
class HelpViewModelFactory(
    private val application: Application,
    private val id: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HelpViewModel::class.java)) {
            return HelpViewModel(application, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}