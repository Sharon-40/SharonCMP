package data.model.putaway

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    var destinationStorageBin: String=""

    @SerialName("DestinationStorageBinDummy")
    var destinationStorageBinDummy: String=""

    @SerialName("WhseTaskExCodeDestStorageBin")
    var whseTaskExCodeDestStorageBin:String=""

    @SerialName("ActualQuantityInAltvUnit")
    var actualQuantityInAltvUnit: String=""

    @SerialName("AlternativeUnit")
    var alternativeUnit: String = ""

    @SerialName("DifferenceQuantityInAltvUnit")
    var differenceQuantityInAltvUnit: String = ""

    @SerialName("WhseTaskExceptionCodeQtyDiff")
    var whseTaskExceptionCodeQtyDiff: String = ""

    @SerialName("SourceHandlingUnit")
    var sourceHandlingUnit: String = ""

    @SerialName("IsHandlingUnitWarehouseTask")
    var isHandlingUnitWarehouseTask:Boolean=false

    var status:Boolean=false

    var message:String=""

    @SerialName("Batch")
    var batch:String=""

    @SerialName("EWMSerialNumberArray")
    var serialNumbers:String=""

}
