package data.prefrences

import com.liftric.kvault.KVault

expect class LocalSharedStorageFactory(context: Any?) {

    fun createStoreInstance(): KVault

}