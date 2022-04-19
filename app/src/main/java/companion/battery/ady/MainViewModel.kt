package companion.battery.ady

import android.Manifest
import android.app.Application
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import companion.battery.ady.models.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val repository: MainRepository
) : AndroidViewModel(application) {

//region Properties

    private val bluetoothManager: BluetoothManager? = ContextCompat.getSystemService(BatteryCompanionApp.context, BluetoothManager::class.java)

//endregion

//region Lifecycle

    val devices = mutableStateListOf<Device>()

//endregion

//region Public Methods

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun getBluetoothDevices() {

        repository.getBluetoothDevices(manager = bluetoothManager)
        updateDevices()

    }

    fun updateDevice(device: Device) {
        repository.updateDeviceStatus(device = device)
        updateDevices()
    }

//endregion

//region Private Methods

    private fun updateDevices() {

        devices.clear()
        devices.addAll(repository.devices)
    }

//endregion

}