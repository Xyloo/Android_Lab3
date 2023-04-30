package pl.pollub.s95408.lab3

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class PhoneViewModel(private val repository: PhoneRepository): ViewModel() {

val allElements : LiveData<List<Phone>> = repository.getAll().asLiveData()

    fun insert(phone: Phone) = viewModelScope.launch {
        repository.insert(phone)
    }

    fun update(phone: Phone) = viewModelScope.launch {
        repository.update(phone)
    }

    fun delete(phone: Phone) = viewModelScope.launch {
        repository.delete(phone)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

}

class PhoneViewModelFactory(private val repository: PhoneRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhoneViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhoneViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}