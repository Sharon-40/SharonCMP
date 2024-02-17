package data

expect class PlatformUtils(context: Any?) {
    fun makeToast(message:String)

    fun getQueryParameter(url:String,name: String): String?

    fun isTablet():Boolean

}