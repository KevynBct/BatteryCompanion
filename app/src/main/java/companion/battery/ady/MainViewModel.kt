package companion.battery.ady

import android.Manifest
import android.app.Application
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
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

    val devices = liveData {
        emitSource(repository.devices)
    }

//endregion

//region Public Methods

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun getBluetoothDevices() {

        repository.getBluetoothDevices(manager = bluetoothManager)

    }

    fun updateDeviceStatus(device: Device) {
        repository.updateDeviceStatus(device = device)
    }

//endregion

}