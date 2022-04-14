package companion.battery.ady

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class BluetoothBroadcastReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action
        val device: BluetoothDevice? = intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)

        when {
            BluetoothDevice.ACTION_FOUND == action -> {
                //Device found
            }
            BluetoothDevice.ACTION_ACL_CONNECTED == action -> {
                //Device is now connected
            }
            BluetoothAdapter.ACTION_DISCOVERY_FINISHED == action -> {
                //Done searching
            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED == action -> {
                //Device has disconnected
            }
        }

    }

    companion object {

        val BluetoothBroadcastFilter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        }

    }

}