package com.example.weatherus.viewmodel

/*import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    weatherViewModelProdiver: Provider<WeatherViewModel>
): ViewModelProvider.Factory {

    private val providers = mapOf<Class<*>, Provider<out ViewModel>>(
        WeatherViewModel::class.java to weatherViewModelProdiver
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(providers.contains(modelClass)) {
            return providers[modelClass]!!.get() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: \${modelClass.name}")
    }
}*/
