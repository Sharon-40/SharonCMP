package data

import platform.Foundation.NSURLComponents
import platform.Foundation.NSURLQueryItem
import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication

actual class PlatformUtils actual constructor(context: Any?) {

    actual fun makeToast(message:String){

        val alertController = UIAlertController.alertControllerWithTitle(
            title = null,
            message = message,
            preferredStyle = UIAlertControllerStyleAlert
        )

        val action = UIAlertAction.actionWithTitle(
            title = "OK",
            style = UIAlertActionStyleDefault,
            handler = {
                // Handle button tap if needed
                alertController.dismissViewControllerAnimated(true, null)
            }
        )
        alertController.addAction(action)


        val presentingViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
        presentingViewController?.presentViewController(alertController, animated = true, completion = {})
    }

    actual fun getQueryParameter(url:String,name: String): String?{

        val uri= NSURLComponents(string = url)

        var queryValue:String?=null

        uri.queryItems?.let {
            for (i in it){
                val item= i as NSURLQueryItem
                if (item.name==name)
                {
                    queryValue=item.value
                    break
                }
            }
        }
        return queryValue

    }

}