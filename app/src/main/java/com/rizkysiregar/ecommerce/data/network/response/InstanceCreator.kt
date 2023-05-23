package com.rizkysiregar.ecommerce.data.network.response

import com.google.gson.InstanceCreator
import java.lang.reflect.Type

class RegisterResponseInstanceCreator : InstanceCreator<RegisterResponse> {
    override fun createInstance(type: Type): RegisterResponse {
        return RegisterResponse(0, Data("", 0, ""), "")
    }
}



