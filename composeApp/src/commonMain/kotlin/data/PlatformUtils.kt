package data

import data.model.enums.Platform
import io.github.alexzhirkevich.cupertino.adaptive.Theme

expect class PlatformUtils(context: Any?) {
    fun makeToast(message:String)

    fun getQueryParameter(url:String,name: String): String?

    fun isTablet():Boolean

    fun determineTheme(): Theme

    fun getPlatform(): Platform

}