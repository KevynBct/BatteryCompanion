package companion.battery.ady

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
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
    fun getBluetoothDevices(bluetoothAdapter: BluetoothAdapter?) : List<BluetoothDevice> {

        return bluetoothAdapter?.bondedDevices?.toList() ?: emptyList()

    }


}