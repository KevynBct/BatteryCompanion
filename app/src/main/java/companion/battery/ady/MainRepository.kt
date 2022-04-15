package companion.battery.ady

import android.Manifest
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import companion.battery.ady.extensions.isConnected
import javax.inject.Inject

class MainRepository @Inject constructor() {

    private val mDevice: MediatorLiveData<Device> = MediatorLiveData<Device>()
    val data: LiveData<Device> = mDevice

    fun addDataSource(data: LiveData<Device>) {
        mDevice.addSource(data) { mDevice.value = it }
    }

    fun removeDataSource(data: LiveData<Device>) {
        mDevice.removeSource(data)
    }

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