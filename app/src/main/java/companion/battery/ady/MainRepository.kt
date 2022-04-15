package companion.battery.ady

import android.Manifest
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import companion.battery.ady.extensions.isConnected
import companion.battery.ady.models.Device
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepository @Inject constructor() {

    private val _devices = MutableLiveData<ArrayList<Device>>(arrayListOf())
    val devices: LiveData<ArrayList<Device>> = _devices

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun getBluetoothDevices(manager: BluetoothManager?) {

        _devices.value = ArrayList(manager?.adapter?.bondedDevices?.map {

            Device(
                name = it.name,
                isConnected = it.isConnected,
                macAddress = it.address
            )

        }?.sortedBy { it.isConnected } ?: emptyList())

    }

    fun updateDeviceStatus(device: Device) {

        val tmpDevices = _devices.value ?: arrayListOf()

        tmpDevices.removeIf { it.macAddress == device.macAddress }
        tmpDevices.add(device)
        tmpDevices.sortBy { it.isConnected }

        _devices.value = tmpDevices

    }

}