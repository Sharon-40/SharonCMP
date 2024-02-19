package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StorageTypeModel(


    @SerialName("Warehouse")
    var warehouse: String = "",

    @SerialName("StorageType")
    var storageType: String = "",

    @SerialName("FixedBin")
    var fixedBin: Boolean = false

)
