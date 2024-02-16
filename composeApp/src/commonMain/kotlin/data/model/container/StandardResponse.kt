package data.model.container

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class StandardResponse<T> {

    @SerialName("status")
    var status: Boolean = false

    @SerialName("message")
    var message: String = ""

    @SerialName("data")
    var data: T? = null

}