package companion.battery.ady

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class BluetoothBroadcastReceiver: BroadcastReceiver() {

    /*private val _devices = MutableLiveData<List<Device>>(emptyList())
    val devices: LiveData<List<Device>> = _devices*/

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action
        val bluetoothDevice: BluetoothDevice = intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) ?: return

        val device = Device(
            name = bluetoothDevice.name,
            isConnected = false,
            macAddress = bluetoothDevice.address
        )

        when {
            BluetoothDevice.ACTION_FOUND == action -> {
                //Device found
            }
            BluetoothDevice.ACTION_ACL_CONNECTED == action -> {
                device.isConnected = true
                //Device is now connected
            }
            BluetoothDevice.ACTION_ACL_DISCONNECTED == action -> {
                device.isConnected = false
                //Device has disconnected
            }
        }

        Log.i("TEST_", "\ndevice : ${device.name}\nconnected : ${device.isConnected}")

    }

    companion object {

        val BluetoothBroadcastFilter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        }

    }

}