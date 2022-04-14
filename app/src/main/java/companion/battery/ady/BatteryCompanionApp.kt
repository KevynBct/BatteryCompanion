package companion.battery.ady

import android.app.Application
import android.content.Context

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