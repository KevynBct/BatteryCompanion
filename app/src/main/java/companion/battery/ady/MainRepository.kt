package companion.battery.ady

import android.Manifest
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import companion.battery.ady.extensions.isConnected
import javax.inject.Inject

class MainRepository @Inject constructor() {

    var devices = arrayListOf<BluetoothDevice>()

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun getBluetoothDevices(manager: BluetoothManager?) {

        devices.addAll(

            manager?.adapter?.bondedDevices?.sortedByDescending { it.isConnected } ?: emptyList()

        )

    }

    fun updateDeviceStatus(device: BluetoothDevice) {

        devices.removeIf { it.address == device.address }
        devices.add(device)
        devices.sortByDescending { it.isConnected }

    }

}