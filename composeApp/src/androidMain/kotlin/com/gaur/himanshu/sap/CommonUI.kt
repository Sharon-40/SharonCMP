package com.gaur.himanshu.sap

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sap.cloud.mobile.fiori.compose.dialog.FioriAlertDialog
import androidx.compose.ui.window.DialogProperties
import com.gaur.himanshu.R

@Composable
fun AlertDialogComponent(
    title: String? = null,
    text: String,
    onPositiveButtonClick: () -> Unit,
    positiveButtonText: String? = null,
    onNegativeButtonClick: (() -> Unit)? = null,
    negativeButtonText: String? = null,
    properties: DialogProperties = DialogProperties(),
) {
    FioriAlertDialog(
        text = text,
        title = title,
        confirmButtonText = positiveButtonText ?: stringResource(R.string.ok),
        onConfirmButtonClick = onPositiveButtonClick,
        onDismissButtonClick = onNegativeButtonClick ?: {},
        dismissButtonText = onNegativeButtonClick?.let {
            negativeButtonText ?: stringResource(R.string.cancel)
        },
        properties = properties
    )
}
