package com.example.appcontact

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appcontact.model.Contact
import com.example.appcontact.model.ContactDataBase
import com.example.appcontact.model.UiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivityViewModel(val dataBase: ContactDataBase):ViewModel() {
    private val _uiState = MutableStateFlow(UiState(listOf<Contact>()))
    val uiState = _uiState.asStateFlow()
    fun insert(contacts: Contact) {
        viewModelScope.launch {
            dataBase.contactDao().insertAll(contacts)
            val list = dataBase.contactDao().getListContact()
            list.sortedBy { it.name[0] }
            _uiState.update {
                it.copy(list)
            }
        }


    }
    fun getContacts() {
        viewModelScope.launch {
            val list = dataBase.contactDao().getListContact()
            list.sortedBy { it.name[0] }
            _uiState.update {
                it.copy(list)
            }
        }
    }

    fun find(name: String) {
        viewModelScope.launch {
            if (name.isEmpty()) {
                getContacts()
            }
            else {
                val list = dataBase.contactDao().findByName(name)
                list.sortedBy { it.name[0] }
                _uiState.update {
                    it.copy(list)
                }
            }
        }
    }

    init {
        getContacts()
    }

}