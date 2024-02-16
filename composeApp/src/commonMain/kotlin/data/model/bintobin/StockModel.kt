package data.model.bintobin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockModel(

    @SerialName("ewmwarehouse")
    var warehouse: String,

    @SerialName("product")
    var product: String = "",

    @SerialName("productDescription")
    var productDesc: String = "",

    @SerialName("availableQuantity")
    var qty: String = "",

    @SerialName("baseUnit")
    var uom: String = "",

    @SerialName("storageType")
    var storageType: String = "",

    @SerialName("storageBin")
    var storageBin: String = "",

    @SerialName("stockType")
    var stockType: String = "",

    @SerialName("batch")
    var batch: String = "",

    @SerialName("serializedProduct")
    var serializedProduct:String="",

    @SerialName("ewmstockOwner")
    var ewmstockOwner: String = "",

    @SerialName("entitledToDisposeParty")
    var entitledToDisposeParty: String = "",

    @SerialName("ewmdocumentCategory")
    var ewmdocumentCategory:String="",

    var isSelected: Boolean = false,

    var selectedDestStorageType: String? = null,

    var selectedDestBin: String? = null,

    var enteredQty: String? = null,

    var isQtyValidated: Boolean = false,

    var isValidated: Boolean = false,

    var serialNumsValidated:Boolean=false

    )
