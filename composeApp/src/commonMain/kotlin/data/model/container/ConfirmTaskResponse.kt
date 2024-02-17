package data.model.container

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmTaskResponse(

    @SerialName("Status")
    var status:Boolean,

    @SerialName("ResponseMsg")
    var responseMsg:String,

    @SerialName("DestinationStorageBin")
    var destinationStorageBin:String?=null,

    @SerialName("Quantity")
    var quantity:Double,

    @SerialName("Product")
    var product:String?=null,

    @SerialName("SourceStorageBin")
    var sourceStorageBin:String?=null,

    @SerialName("WarehouseTask")
    var warehouseTask:String?=null,

    )
