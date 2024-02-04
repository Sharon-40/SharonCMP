package data.oauth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenModel (

    @SerialName("access_token")
    val accessToken:String="",

    @SerialName("refresh_token")
    val refreshToken:String="",

    @SerialName("expires_in")
    val expiresIn:Int=0
)