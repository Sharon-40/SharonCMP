package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskModel(

    @SerialName("taskId")
    var taskId: String,

    @SerialName("processId")
    var processId: String = "",

    @SerialName("description")
    var description: String = "",

    @SerialName("taskOwner")
    var taskOwner: String = "",

    @SerialName("createdBy")
    var createdBy: String ? =null,

    @SerialName("start_time")
    var startTime: String ?=null,

    @SerialName("origin")
    var origin: String?=null,

    @SerialName("location")
    var location: String ?=null,

    @SerialName("requestId")
    var requestId: Int = 0,

    @SerialName("distance")
    var distance: String? = null,

    @SerialName("status")
    var status: String = ""
)
