package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WarehouseTaskModel(

    @SerialName("EWMWarehouse")
    var warehouse: String,

    @SerialName("WarehouseOrder")
    var wo: String = "",

    @SerialName("WarehouseTask")
    var woTaskId: String = "",

    @SerialName("WarehouseTaskItem")
    var woline: String = "",

    @SerialName("ProductName")
    var product: String = "",

    @SerialName("ProductDescription")
    var productDesc: String = "",

    @SerialName("TargetQuantityInBaseUnit")
    var qty: String = "",

    @SerialName("BaseUnit")
    var uom: String = "",


    @SerialName("SourceStorageType")
    var sourceStorageType: String = "",

    @SerialName("SourceStorageBin")
    var sourceBin: String = "",

    @SerialName("DestinationStorageType")
    var destStorageType: String = "",

    @SerialName("DestinationStorageBin")
    var destBin: String = "",

    @SerialName("IsHandlingUnitWarehouseTask")
    var isHandlingUnitWarehouseTask: Boolean = false,

    @SerialName("WarehouseTaskStatus")
    var status: String = "",

    @SerialName("CreatedByUser")
    var createdBy: String = "",

    @SerialName("PurchasingDocument")
    var purchasingDocument:String="",

    @SerialName("PurchasingDocumentItem")
    var purchasingDocumentItem:String="",

    @SerialName("EWMDelivery")
    var ewmDelivery:String="",

    @SerialName("EWMDeliveryItem")
    var ewmDeliveryItem:String="",

    @SerialName("RefDelivery")
    var refDelivery:String="",

    @SerialName("RefDeliveryItem")
    var refDeliveryItem:String="",

    @SerialName("Batch")
    var batch: String = "",

    @SerialName("ConfirmedByUser")
    var confirmedByUser: String = "",

    @SerialName("EWMStockType")
    var stockType: String = "",

    @SerialName("SerializedProduct")
    var serializedProduct:String="",

    var isSelected: Boolean = false,

    var selectedDestStorageType: String? = null,

    var selectedDestBin: String? = null,

    var enteredQty: String? = null,

    var isQtyValidated: Boolean = false,

    var isValidated: Boolean = false,

    var serialNumsValidated:Boolean=false

    )
