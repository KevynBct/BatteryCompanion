package companion.battery.ady

import android.Manifest
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import companion.battery.ady.model.Device
import javax.inject.Inject

class MainRepository @Inject constructor() {

    var devices = arrayListOf<Device>()

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun getBluetoothDevices(manager: BluetoothManager?) {

        val bondedDevices = manager?.adapter?.bondedDevices.orEmpty()

        devices.addAll(

            bondedDevices
                .map { Device(it) }
                .sortedByDescending { it.isConnected }

        )

    }

    fun updateDeviceStatus(device: Device) {

        devices.removeIf { it.address == device.address }
        devices.add(device)
        devices.sortByDescending { it.isConnected }

    }

}