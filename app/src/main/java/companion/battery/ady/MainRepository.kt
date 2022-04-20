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

        val filteredDevices = bondedDevices
            .map { Device(it) }
            .filter { d -> devices.none { d.address == it.address } }
            .sortedByDescending { it.isConnected }

        devices.addAll(filteredDevices)

    }

    fun updateDeviceStatus(device: Device) {

        devices.removeIf { it.address == device.address }
        devices.add(device)
        devices.sortByDescending { it.isConnected }

    }

}