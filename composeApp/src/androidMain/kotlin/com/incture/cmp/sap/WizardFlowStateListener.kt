package com.incture.cmp.sap

import com.sap.cloud.mobile.flows.compose.core.FlowContext
import com.sap.cloud.mobile.flows.compose.ext.FlowStateListener
import com.sap.cloud.mobile.foundation.model.AppConfig


class WizardFlowStateListener(private val application: SAPWizardApplication) :
    FlowStateListener() {

    override suspend fun onAppConfigRetrieved(appConfig: AppConfig) {
    }

    override suspend fun onApplicationReset() {
        this.application.resetApplication()
    }

    override suspend fun onApplicationLocked() {
        super.onApplicationLocked()
    }

    override suspend fun onFlowFinished(flowName: String?) {
    }


}

fun FlowContext.isUserSwitch(): Boolean {
    return getPreviousUser()?.let {
        getCurrentUser() != it
    } ?: false
}
