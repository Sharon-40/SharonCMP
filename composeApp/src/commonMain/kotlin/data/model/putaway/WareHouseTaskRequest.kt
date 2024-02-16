package data.model.putaway

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WareHouseTaskRequest(
    @SerialName("DestinationStorageBin")
    val destinationStorageBin: String?=null,
    @SerialName("Quantity")
    val quantity: Double?=null,
    @SerialName("WarehouseTask")
    val warehouseTask: String?=null
)