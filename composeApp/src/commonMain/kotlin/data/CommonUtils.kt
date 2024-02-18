package data

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okio.ByteString.Companion.decodeBase64

object CommonUtils {
    private fun decodeBase64(encoded:String): String {
       return encoded.decodeBase64()?.utf8()?:""
    }

    fun getUserId(token:String): String{
        val userData = decodeBase64(token.split(".")[1])
        return Json.parseToJsonElement(userData).jsonObject["user_name"]?.jsonPrimitive?.content.toString()
    }

    fun getUserName(token:String): String{
        val userData =  decodeBase64(token.split(".")[1])
        return Json.parseToJsonElement(userData).jsonObject["given_name"]?.jsonPrimitive?.content.toString()+" "+ Json.parseToJsonElement(userData).jsonObject["family_name"]?.jsonPrimitive?.content.toString()
    }

    fun getCurrentDate():String
    {
        return Clock.System.now().toString()
    }

    fun getParseTDate(value:String?):String
    {
        return if (value.isNullOrBlank()) {
            ""
        }else{
            val instant= Instant.parse(value).toLocalDateTime(TimeZone.UTC)
            "${instant.year}-${instant.monthNumber}-${instant.dayOfMonth}"
        }
    }

}