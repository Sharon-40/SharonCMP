package data.preferences

import com.russhwolf.settings.Settings

expect class KVaultFactory(context: Any?) {
    fun createStoreInstance(): Settings
}