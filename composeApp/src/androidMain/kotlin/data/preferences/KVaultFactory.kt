package data.preferences

import android.content.Context
import com.russhwolf.settings.Settings

actual class KVaultFactory actual constructor(context: Any?) {

    private var context: Context

    init {
        this.context = context as Context
    }

    actual fun createStoreInstance(): Settings {
        return Settings()
        /*val factory: Settings.Factory = SharedPreferencesSettings.Factory(context)
        return factory.create("shared_preferences")*/
    }

}