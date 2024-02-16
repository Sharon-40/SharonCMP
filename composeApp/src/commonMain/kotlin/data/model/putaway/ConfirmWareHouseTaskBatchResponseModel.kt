package data.model.putaway

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmWareHouseTaskBatchResponseModel(
    @SerialName("LocationURL")
    val locationURL: String?=null,

    @SerialName("Request")
    val request: List<WareHouseTaskRequest>?=null
)