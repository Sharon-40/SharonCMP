package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserParametersModel (

    @SerialName("ParameterID")
    var parameterID:String="",

    @SerialName("ParameterValue")
    var parameterValue:String=""
)