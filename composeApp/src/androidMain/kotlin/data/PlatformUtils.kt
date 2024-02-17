package data

import android.content.Context
import android.net.Uri
import android.widget.Toast

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

}