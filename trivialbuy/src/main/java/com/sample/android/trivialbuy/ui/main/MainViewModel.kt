package com.sample.android.trivialbuy.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val stateProducts = MutableStateFlow<List<String>>(emptyList())
    val products: StateFlow<List<String>> = stateProducts

    private val purchasesState = MutableStateFlow<List<String>>(emptyList())
    val purchases: StateFlow<List<String>> = purchasesState

    private val _resultText = MutableSharedFlow<String>()
    val resultText: SharedFlow<String> = _resultText

    private val _ruStoreException = MutableSharedFlow<String>()
    val ruStoreException: SharedFlow<String> = _ruStoreException

    init {
        // TODO Получить и обработать результат оплаты через RuStore
    }

    fun loadProducts() {
        // TODO Загрузить продукты (InApp'ы) из RuStore
    }

    fun purchaseProduct(productId: String, context: Context) {
        // TODO Купить продукт через RuStore
    }

    fun loadPurchases() {
        // TODO Загрузить покупки из RuStore
    }

    fun confirmPurchase(purchaseId: String) {
        // TODO Потребить покупку для consumable продуктов
    }
}
