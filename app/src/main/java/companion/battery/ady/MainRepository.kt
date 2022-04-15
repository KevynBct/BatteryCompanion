package companion.battery.ady

import android.Manifest
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import companion.battery.ady.extensions.isConnected
import companion.battery.ady.models.Device
import javax.inject.Inject

class MainRepository @Inject constructor() {

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun getBluetoothDevices(manager: BluetoothManager?) : List<Device> {

        return manager?.adapter?.bondedDevices?.map {

            Device(
                name = it.name,
                isConnected = it.isConnected,
                macAddress = it.address
            )

        } ?: emptyList()

    }


}