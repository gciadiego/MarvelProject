package com.example.marvelproject.presentation.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelproject.data.implementation.MarvelApiRepository
import com.example.marvelproject.domain.model.CharacterDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val marvelApiRepository: MarvelApiRepository) : ViewModel() {
    private var _list = mutableStateListOf<CharacterDomain>()
    val list: SnapshotStateList<CharacterDomain>
        get() = _list

    private var _currentCharacter = mutableStateOf<CharacterDomain?>(null)
    val currentCharacter: State<CharacterDomain?>
        get() = _currentCharacter

    private var _buttonSelected = mutableStateOf<String>("")
    val buttonSelected: State<String>
        get() = _buttonSelected

    private var _loading = mutableStateOf<Boolean>(true)
    val loading: State<Boolean>
        get() = _loading

    fun getCharacters() {
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)

            val response = marvelApiRepository.getCharacters()

            if(response.isNotEmpty()){
                _loading.value = false
                _list.clear()
                _list.addAll(response)
            }else{
                print("Error in API call")
            }
        }
    }

    fun setCurrentCharacter(character: CharacterDomain) {
        _currentCharacter.value = character
    }

    fun setButtonSelected(btn: String) {
        _buttonSelected.value = btn
    }
}