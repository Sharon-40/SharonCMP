package data.prefrences

import android.content.Context
import com.liftric.kvault.KVault

actual class LocalSharedStorageFactory actual constructor(context: Any?) {

    private var context: Context

    init {
        this.context = context as Context
    }

    actual fun createStoreInstance(): KVault {
        return KVault(context)
    }

}