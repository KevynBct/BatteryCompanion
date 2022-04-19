package companion.battery.ady

import android.Manifest
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import companion.battery.ady.extensions.isConnected
import companion.battery.ady.models.Device
import javax.inject.Inject

class MainRepository @Inject constructor() {

    var devices = arrayListOf<Device>()

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun getBluetoothDevices(manager: BluetoothManager?) {

        devices.addAll(

            manager?.adapter?.bondedDevices?.map {

                Device(
                    name = it.name,
                    isConnected = it.isConnected,
                    macAddress = it.address
                )

            }?.sortedByDescending { it.isConnected } ?: emptyList()

        )

    }

    fun updateDeviceStatus(device: Device) {

        devices.removeIf { it.macAddress == device.macAddress }
        devices.add(device)
        devices.sortByDescending { it.isConnected }

    }

}