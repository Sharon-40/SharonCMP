package data.preferences

import com.russhwolf.settings.Settings

actual class KVaultFactory actual constructor(context: Any?) {

    actual fun createStoreInstance(): Settings {
        return Settings()
    }
}