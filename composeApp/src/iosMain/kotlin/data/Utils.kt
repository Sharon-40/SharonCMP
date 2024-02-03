package data

import platform.UIKit.UIAlertAction
import platform.UIKit.UIAlertActionStyleDefault
import platform.UIKit.UIAlertController
import platform.UIKit.UIAlertControllerStyleAlert
import platform.UIKit.UIApplication

actual class Utils actual constructor(context: Any?) {

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
}