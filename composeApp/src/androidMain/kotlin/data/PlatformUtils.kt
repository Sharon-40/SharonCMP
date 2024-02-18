package data

import android.content.Context
import android.net.Uri
import android.widget.Toast
import data.model.enums.Platform
import io.github.alexzhirkevich.cupertino.adaptive.Theme

actual class PlatformUtils actual constructor(context: Any?) {

    private var context: Context

    init {
        this.context = context as Context
    }

    actual fun makeToast(message:String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

    actual fun getQueryParameter(url:String,name: String): String?{
        return Uri.parse(url).getQueryParameter(name)
    }

    actual fun isTablet():Boolean{
        return context.resources.configuration.smallestScreenWidthDp >= 600
    }

    actual fun determineTheme(): Theme = Theme.Material3

    actual fun getPlatform(): Platform{
        return Platform.Android
    }

}