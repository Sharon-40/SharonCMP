import io.github.aakira.napier.Napier



object LogUtils {

    const val  RESPONSE_SUCCESS = "ResponseS"
    const val  RESPONSE_ERROR = "ResponseE"
    const val  RESPONSE = "Response"
    const val  RESPONDED = "Responded"
    const val  RESPONSE_CODE = "Response_code"

    fun logDebug(key: String, value: String) {
        Napier.d(tag = key, message = value)
    }

    fun logWarning(key: String, value: String) {
        Napier.w(tag = key, message = value)
    }

    fun logError(key: String, value: String) {
        Napier.e(tag = key, message = value)
    }

    fun logException(e:Exception) {
        e.printStackTrace()
    }
}
