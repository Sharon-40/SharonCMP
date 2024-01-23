package data.prefrences

import com.liftric.kvault.KVault

actual class LocalSharedStorageFactory actual constructor(context: Any?) {

    actual fun createStoreInstance(): KVault {
        return KVault()
    }
}