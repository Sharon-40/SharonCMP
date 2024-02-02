package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmTaskResponse(

    @SerialName("Status")
    var status:Boolean,

    @SerialName("ResponseMsg")
    var responseMsg:String,

    @SerialName("DestinationStorageBin")
    var destinationStorageBin:String,

    @SerialName("Quantity")
    var quantity:Double,

    @SerialName("Product")
    var product:String,

    @SerialName("SourceStorageBin")
    var sourceStorageBin:String,

    )
