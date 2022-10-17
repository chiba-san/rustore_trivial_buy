package com.sample.android.trivialbuy.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.rustore.sdk.billingclient.RuStoreBillingClient
import ru.rustore.sdk.billingclient.model.product.Product
import ru.rustore.sdk.billingclient.model.purchase.PaymentResult
import ru.rustore.sdk.billingclient.model.purchase.Purchase

class MainViewModel : ViewModel() {

    private val _stateProducts = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _stateProducts

    private val _purchasesState = MutableStateFlow<List<Purchase>>(emptyList())
    val purchases: StateFlow<List<Purchase>> = _purchasesState

    private val _resultText = MutableSharedFlow<String>()
    val resultText: SharedFlow<String> = _resultText

    private val _ruStoreException = MutableSharedFlow<Exception>()
    val ruStoreException: SharedFlow<Exception> = _ruStoreException

    init {
        viewModelScope.launch {
            RuStoreBillingClient.purchases.resultObserver()
                .collect {
                    val resultText = when (it) {
                        is PaymentResult.PurchaseResult -> it.finishCode.name
                        is PaymentResult.InvalidPurchase -> it.errorCode.toString()
                        else  -> "error"
                    }
                    _resultText.emit(resultText)
                }
        }
    }

    fun loadProducts() {
        viewModelScope.launch {
            try {
                val productsResponse = withContext(Dispatchers.IO) {
                    RuStoreBillingClient.products.getProducts(
                        language = "ru-RU",
                        productIds = listOf("id1", "id2")
                    )
                }
                _stateProducts.value = productsResponse.products.orEmpty()
            } catch (ex: Exception) {
                _ruStoreException.emit(ex)
            }
        }
    }

    fun purchaseProduct(productId: String, context: Context) {
        viewModelScope.launch {
            try {
                RuStoreBillingClient.purchases.purchaseProduct(
                    context = context,
                    productId = productId,
                    quantity = 1,
                )
            } catch (ex: Exception) {
                _ruStoreException.emit(ex)
            }
        }
    }

    fun loadPurchases() {
        viewModelScope.launch {
            try {
                val purchasesResponse = withContext(Dispatchers.IO) {
                    RuStoreBillingClient.purchases.getPurchases(
                        language = "ru-RU",
                    )
                }
                _purchasesState.value = purchasesResponse.purchases.orEmpty()
            } catch (ex: Exception) {
                _ruStoreException.emit(ex)
            }
        }
    }

    fun confirmPurchase(purchaseId: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RuStoreBillingClient.purchases.confirmPurchase(purchaseId)
                }
                val resultText = if (response.code == 0) "confirmed" else "error"
                _resultText.emit(resultText)
            } catch (ex: Exception) {
                _ruStoreException.emit(ex)
            }
        }
    }
}