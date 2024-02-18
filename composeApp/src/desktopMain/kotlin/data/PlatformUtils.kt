package data

import data.model.enums.Platform
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import java.net.URI
import javax.swing.JOptionPane


actual class PlatformUtils actual constructor(context: Any?) {

    actual fun makeToast(message: String) {
        JOptionPane.showMessageDialog(null, message, null, JOptionPane.INFORMATION_MESSAGE)
    }

    actual fun getQueryParameter(url: String, name: String): String? {
        val uri = URI.create(url)

        val queryMap = uri.query
            ?.split("&")
            ?.associate { entry ->
                val (key, value) = entry.split("=")
                key to value
            }

        return queryMap?.get(name)

    }

    actual fun isTablet():Boolean{
       return true
    }

    actual fun determineTheme(): Theme {
        return when(getPlatform()){
            Platform.Windows -> Theme.Material3
            Platform.MacoS -> Theme.Cupertino
            Platform.Linux -> Theme.Cupertino
            else -> Theme.Material3
        }
    }

    actual fun getPlatform(): Platform {
        val osName = System.getProperty("os.name")
        return when {
            osName.startsWith("Windows") -> Platform.Windows
            osName.startsWith("Linux") -> Platform.Linux
            osName.startsWith("Mac") || osName.startsWith("Darwin") -> Platform.MacoS
            else -> Platform.Windows
        }
    }
}

