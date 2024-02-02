import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.preferences.LocalSharedStorage
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.inject

fun main() = application {

    initKoin()

    val localSharedStorage: LocalSharedStorage = koinInject()

    localSharedStorage.saveUserId("JN")
    localSharedStorage.saveUserName("JN")
    localSharedStorage.savePlant("AW02")
    localSharedStorage.saveWareHouse("OS00")
    localSharedStorage.savePrinter("LP01")

    Window(onCloseRequest = ::exitApplication, title = StringResources.AppName) {
        App()
    }
}
