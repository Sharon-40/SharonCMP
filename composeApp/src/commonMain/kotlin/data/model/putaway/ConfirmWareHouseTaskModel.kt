package data.model.putaway

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
class ConfirmWareHouseTaskModel  {

    @SerialName("UserId")
    var userId: String=""

    @SerialName("EWMWarehouse")
    var warehouse: String = ""

    @SerialName("WarehouseTask")
    var warehouseTask: String=""

    @SerialName("WarehouseOrder")
    var warehouseOrder:String=""

    @SerialName("DestinationStorageBin")
    var destinationStorageBin: String?=null

    @SerialName("DestinationStorageBinDummy")
    var destinationStorageBinDummy: String?=null

    @SerialName("WhseTaskExCodeDestStorageBin")
    var whseTaskExCodeDestStorageBin:String?=null

    @SerialName("ActualQuantityInAltvUnit")
    var actualQuantityInAltvUnit: String?=null

    @SerialName("AlternativeUnit")
    var alternativeUnit: String ?=null

    @SerialName("DifferenceQuantityInAltvUnit")
    var differenceQuantityInAltvUnit: String ?=null

    @SerialName("WhseTaskExceptionCodeQtyDiff")
    var whseTaskExceptionCodeQtyDiff: String ?=null

    @SerialName("SourceHandlingUnit")
    var sourceHandlingUnit: String ?=null

    @SerialName("IsHandlingUnitWarehouseTask")
    var isHandlingUnitWarehouseTask:Boolean=false

    @SerialName("Batch")
    var batch:String?=null

    @SerialName("EWMSerialNumberArray")
    var serialNumbers:String?=null


}
