package companion.battery.ady

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BatteryCompanionApp : Application() {

    override fun onCreate() {
        super.onCreate()
        current = this
    }

    companion object {

        private lateinit var current: BatteryCompanionApp

        val context: Context
            get() = current.applicationContext

    }
}