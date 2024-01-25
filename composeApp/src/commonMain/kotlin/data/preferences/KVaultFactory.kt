package data.preferences

import com.liftric.kvault.KVault

expect class KVaultFactory(context: Any?) {
    fun createStoreInstance(): KVault
}