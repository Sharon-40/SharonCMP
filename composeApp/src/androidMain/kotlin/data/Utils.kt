package data

import android.content.Context
import android.widget.Toast

actual class Utils actual constructor(context: Any?) {

    private var context: Context

    init {
        this.context = context as Context
    }

    actual fun makeToast(message:String){
        Toast.makeText(context,message, Toast.LENGTH_LONG).show()
    }

}