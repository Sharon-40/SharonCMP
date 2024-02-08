package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TaskDetailsModel(

    @SerialName("taskId")
    var taskId: String?=null,

    @SerialName("processId")
    var processId: String?=null,

    @SerialName("subject")
    var subject:String?=null,

    @SerialName("description")
    var description: String? = null,

    @SerialName("createdByDisplay")
    var createdBy: String ? =null
)
