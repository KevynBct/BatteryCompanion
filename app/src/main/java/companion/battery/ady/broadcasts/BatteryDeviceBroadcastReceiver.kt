package companion.battery.ady.broadcasts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

interface BatteryDeviceBroadcastListener {
    fun onChargingStatusChange(isCharging: Boolean)
}

class BatteryDeviceBroadcastReceiver: BroadcastReceiver() {

    var listener: BatteryDeviceBroadcastListener? = null

    companion object {

        val filters = IntentFilter().apply {
            addAction("android.intent.action.ACTION_POWER_CONNECTED")
            addAction("android.intent.action.ACTION_POWER_DISCONNECTED")
        }

    }

    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {

            Intent.ACTION_POWER_CONNECTED ->
                listener?.onChargingStatusChange(isCharging = true)

            Intent.ACTION_POWER_DISCONNECTED ->
                listener?.onChargingStatusChange(isCharging = false)

        }

    }

}