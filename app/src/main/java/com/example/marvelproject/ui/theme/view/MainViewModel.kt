package com.example.marvelproject.ui.theme.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvelproject.R
import com.example.marvelproject.domain.interfaces.IApiRepository
import com.example.marvelproject.domain.model.CharacterDomain
import com.example.marvelproject.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
//It was injecting the Impl instead of the repository
class MainViewModel @Inject constructor(private val marvelApiRepository: IApiRepository) : ViewModel() {
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

    private var _error = mutableStateOf<String>("")
    val error : State<String>
        get() = _error

    init{
        getCharacters()
    }

    private fun getCharacters() {
        _loading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)

            when(val response = marvelApiRepository.getCharacters()){
                is Resource.Success -> response.data.let {
                    _list.clear()
                    _list.addAll(it)
                    _loading.value = false
                }
                is Resource.Failure ->{
                    _loading.value = false
                    _error.value = "Error, couldn't load the MARVEL characters."
                }
            }

           /* if(response.isNotEmpty()){
                _loading.value = false
                _list.clear()
                _list.addAll(response)
            }else{
                print("Error in API call")
            }*/
        }
    }

    fun setCurrentCharacter(character: CharacterDomain) {
        _currentCharacter.value = character
    }

    fun setButtonSelected(btn: String) {
        _buttonSelected.value = btn
    }

    fun setErrorAsEmpty(){
        _error.value = ""
    }
}