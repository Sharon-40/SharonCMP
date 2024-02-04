package data

import java.net.URI

actual class Utils actual constructor(context: Any?) {

    actual fun makeToast(message: String) {
        print(message)
    }

    actual fun getQueryParameter(url: String, name: String): String? {
        val uri = URI.create(url)

        val queryMap = uri.query
            ?.split("&")
            ?.associate { entry ->
                val (key, value) = entry.split("=")
                key to value
            }

        return queryMap?.get(name)

    }
}