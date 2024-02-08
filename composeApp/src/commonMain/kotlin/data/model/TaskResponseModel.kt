package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TaskResponseModel {

    @SerialName("pageCount")
    var pageCount: Int = 0

    @SerialName("taskList")
    var taskList: ArrayList<TaskModel> = arrayListOf()

}