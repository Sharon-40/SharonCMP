package data

expect class Utils(context: Any?) {
    fun makeToast(message:String)

    fun getQueryParameter(url:String,name: String): String?
}