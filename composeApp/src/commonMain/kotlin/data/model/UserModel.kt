package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class UserModel(

    @SerialName("UserName")
    var userName: String = "",

    @SerialName("OutputDevice")
    var outputDevice: String = "",

    @SerialName("LogOnLanguage")
    var language: String = "",

    @SerialName("_UserParameters")
    var userParameters: ArrayList<UserParametersModel>? = null
)