package data.logs


actual object LogUtils {

    actual fun logDebug(key: String, value: String) {
        print(value)
    }

    actual fun logWarning(key: String, value: String) {
        print(value)
    }

    actual fun logError(key: String, value: String) {
        print(value)
    }

    actual fun logException(e:Exception) {
        e.printStackTrace()
    }
}
