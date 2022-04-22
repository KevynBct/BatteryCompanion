package companion.battery.ady

import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import companion.battery.ady.model.Device
import javax.inject.Inject

@SuppressLint("MissingPermission")
class MainRepository @Inject constructor() {

    var devices = arrayListOf<Device>()

    fun getBluetoothDevices(manager: BluetoothManager?) {

        val bondedDevices = manager?.adapter?.bondedDevices.orEmpty()

        val filteredDevices = bondedDevices
            .map { Device(it) }
            .filter { d -> devices.none { d.address == it.address } }
            .sortedByDescending { it.status.ordinal }

        devices.addAll(filteredDevices)

    }

    fun updateDeviceStatus(device: Device) {

        devices.removeIf { it.address == device.address }
        devices.add(device)
        devices.sortByDescending { it.status.ordinal }

    }

}