package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class TaskDetailsResponseModel {

    @SerialName("taskEventDto")
    var taskEventDto: TaskDetailsModel?=null

    @SerialName("customAttr")
    var customAttr: ArrayList<TaskCustomAttributesModel> = arrayListOf()

}