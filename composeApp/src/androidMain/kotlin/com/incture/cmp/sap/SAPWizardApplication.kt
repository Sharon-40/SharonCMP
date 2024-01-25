package com.incture.cmp.sap

import android.app.Application
import com.sap.cloud.mobile.foundation.mobileservices.MobileService
import com.sap.cloud.mobile.foundation.mobileservices.SDKInitializer
import com.sap.cloud.mobile.foundation.settings.SharedDeviceService
import com.sap.cloud.mobile.foundation.theme.ThemeDownloadService
import initKoin
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.dsl.module

class SAPWizardApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initServices()
        koinSetup()
        Napier.base(DebugAntilog())
    }

    private fun koinSetup()
    {
        initKoin {
            it.modules(
                module {
                    factory { this@SAPWizardApplication.applicationContext }
                }
            )
        }

    }

    fun resetApplication() {

    }

    private fun initServices() {
        val services = mutableListOf<MobileService>()
        services.add(ThemeDownloadService(this))
        services.add(SharedDeviceService(OFFLINE_APP_ENCRYPTION_CONSTANT))

        SDKInitializer.start(this, * services.toTypedArray())
    }


    companion object {
        private const val OFFLINE_APP_ENCRYPTION_CONSTANT = "34dab53fc060450280faeed44a36571b"
    }
}
