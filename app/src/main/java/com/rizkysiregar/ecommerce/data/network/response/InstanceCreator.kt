package com.rizkysiregar.ecommerce.data.network.response

import com.google.gson.InstanceCreator
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class RegisterResponseInstanceCreator : InstanceCreator<RegisterResponse> {
    override fun createInstance(type: Type): RegisterResponse {
        return RegisterResponse(0,Data("",0,""),"")
    }
}



