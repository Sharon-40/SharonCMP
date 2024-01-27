package data.logs

import android.util.Log

actual object LogUtils {

    actual fun logDebug(key: String, value: String) {
        Log.d(key, value)
    }

    actual fun logWarning(key: String, value: String) {
        Log.w(key, value)
    }

    actual fun logError(key: String, value: String) {
        Log.e(key, value)
    }

    actual fun logException(e:Exception) {
        e.printStackTrace()
    }
}
