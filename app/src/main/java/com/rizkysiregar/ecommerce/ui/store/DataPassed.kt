package com.rizkysiregar.ecommerce.ui.store

import com.rizkysiregar.ecommerce.data.model.FilterModel

interface DataPassed {
    fun onDataPassed(data: FilterModel)
}