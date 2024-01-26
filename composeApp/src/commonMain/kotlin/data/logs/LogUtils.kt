package data.logs

expect object LogUtils {
    fun logDebug(key: String, value: String)

    fun logWarning(key: String, value: String)

    fun logError(key: String, value: String)

    fun logException(e:Exception)
}