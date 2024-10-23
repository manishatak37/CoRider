package com.example.corider.model




data class UserModel(
    val userName:String? =null,
    val email:String? =null,
    val userPassword:String? =null,
    val aboutMe : String? = null,
    val age: Number? = null,
    val vehicles:List<VehicleDetail>? = null,
    val rideShared:Number?=0,
    val travelPreferences : String? = null
)











