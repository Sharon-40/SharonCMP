object StringResources {

    const val AppName = "Smart Warehouse"
    const val WareHouseTransactions = "Warehouse Transactions"
    const val Profile = "Profile"
    const val UserName = "UserName"
    const val Plant = "Plant"
    const val Warehouse = "Warehouse"
    const val Printer = "Printer"
    const val LogOut = "Logout"
    const val Execute = "Execute"
    const val Submit = "Submit"
    const val NoDataFound = "No Data Found"
    const val SelectAtLeastOne = "Select At least One"
    const val InvalidBin = "Invalid Bin"
    const val Enter_one_of_the_below_field = "Enter one of the field"
    const val Open_WHO = "Open WHO"
    const val Select = "Select"
    const val RePrint = "RePrint"

    const val BASEURL = "https://clfdev01-dev-suncor-mob-dev.cfapps.us20.hana.ondemand.com/JAVA"


    const val RESPONSE_SUCCESS = "ResponseS"
    const val RESPONSE_ERROR = "ResponseE"
    const val RESPONSE = "Response"
    const val RESPONDED = "Responded"
    const val RESPONSE_CODE = "Response_code"
    const val COST_CENTER = "VERP"

    enum class Apps { PutAway, BinToBin, GoodsReceiving }


    object WareHouseTechTerms {
        const val Plant = "WRK"
        const val WareHouse = "LGN"

        const val WarehouseOrder = "WareHouseOrder"
        const val WarehouseTask = "WareHouseTask"
        const val PurchaseOrder = "PurchaseOrder"
        const val InboundDelivery = "Inbound Delivery"
        const val Product = "Product"
        const val ProductId = "Product Id"
        const val Bin = "Bin"
        const val ProductDesc = "Product Desc"
        const val Qty = "Qty"
        const val Uom = "Uom"
        const val StockType = "StockType"
        const val Batch = "Batch"
        const val TransferQty = "TransferQty"
        const val DestinationStorageType = "Dest.Str.Type"
        const val DestinationBin = "Dest.Bin"
        const val SourceStorageType = "Src.Str.Type"
        const val SourceBin = "Src.Bin"
        const val CreatedOn = "Created On"
        const val CreatedBy = "Created By"
        const val ConfirmedOn = "Confirmed On"
        const val ConfirmedBy = "Confirmed By"
    }

    object ValidationTypes {
        const val ValidationType_PutAway_WarehouseOrder = "PutAway_WarehouseOrder"
        const val ValidationType_PutAway_WarehouseTask = "PutAway_WarehouseTask"
        const val ValidationType_PutAway_PurchaseOrder = "PutAway_PurchaseOrder"
        const val ValidationType_PutAway_Inbound = "PutAway_Inbound"
        const val ValidationType_PutAway_Product = "PutAway_Product"
    }

    enum class ValidationStatus { VALIDATED, INVALID, CLEAR }

    object WarehouseTaskStatus {
        const val OPEN_TASK: String = ""
        const val COMPLETED_TASK: String = "C"
        const val CANCELLED_TASK: String = "A"
    }

    object PutawayExceptionCodes{
        const val CHBD="CHBD"
    }

}