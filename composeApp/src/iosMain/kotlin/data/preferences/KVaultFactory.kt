package data.preferences

import com.liftric.kvault.KVault

actual class KVaultFactory actual constructor(context: Any?) {

    actual fun createStoreInstance(): KVault {
        return KVault()
    }
}