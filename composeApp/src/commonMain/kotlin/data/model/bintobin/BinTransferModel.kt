package data.model.bintobin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class BinTransferModel {

    @SerialName("EWMWarehouse")
    var warehouse: String = ""

    @SerialName("EWMWorkCenter")
    var workCenter:String=""

    @SerialName("Product")
    var product: String = ""

    @SerialName("TargetQuantityInBaseUnit")
    var targetQuantityInAltvUnit: Double = 0.0

    @SerialName("BaseUnit")
    var alternativeUnit: String = ""

    @SerialName("SourceStorageType")
    var sourceStorageType: String = ""

    @SerialName("SourceStorageBin")
    var sourceStorageBin: String = ""

    @SerialName("DestinationStorageType")
    var destinationStorageType: String = ""

    @SerialName("DestinationStorageBin")
    var destinationStorageBin: String = ""

    @SerialName("IsAutoPutaway")
    var isAutoPutaway: Boolean = false

    @SerialName("Batch")
    var batch:String=""

    @SerialName("EWMDelivery")
    var ewmDelivery:String=""

    @SerialName("EWMDeliveryItem")
    var ewmDeliveryLine:String=""

    @SerialName("UserId")
    var userId: String?=null

    @SerialName("WarehouseProcessType")
    var warehouseProcessType: String ?=null

    @SerialName("EWMStockType")
    var eWMStockType: String?=null

    @SerialName("EWMStockOwner")
    var eWMStockOwner: String?=null

    @SerialName("EntitledToDisposeParty")
    var entitledToDisposeParty: String ?=null

    @SerialName("EWMDocumentCategory")
    var ewmdocumentCategory:String=""

}