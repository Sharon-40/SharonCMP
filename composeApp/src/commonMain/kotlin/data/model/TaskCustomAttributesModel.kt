package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskCustomAttributesModel(

    @SerialName("label")
    var label: String?=null,

    @SerialName("dataType")
    var dataType: String = "",

    @SerialName("labelValue")
    var labelValue:String?=null,

    @SerialName("isMandatory")
    var isMandatory: Boolean = false
)
