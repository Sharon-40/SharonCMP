import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {

    initKoin()

    Window(onCloseRequest = ::exitApplication, title = "WareHouse") {
        App()
    }
}