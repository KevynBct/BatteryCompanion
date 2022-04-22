package companion.battery.ady

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothManager
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import companion.battery.ady.model.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@SuppressLint("MissingPermission")
class MainViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

//region Properties

    private val bluetoothAdapter by lazy {
        val bluetoothManager = ContextCompat.getSystemService(BatteryCompanionApp.context, BluetoothManager::class.java) as BluetoothManager
        bluetoothManager.adapter
    }

//endregion

//region Lifecycle

    val devices = mutableStateListOf<Device>()

//endregion

//region Public Methods

    fun getBluetoothDevices() {

        val bondedDevices = bluetoothAdapter.bondedDevices.orEmpty()

        val filteredDevices = bondedDevices
            .map { Device(it) }
            .filter { it.isAvailable }
            .sortedBy { it.name }

        devices.clear()
        devices.addAll(filteredDevices)
    }

    fun updateDevice(device: Device) {

        devices.removeIf { it.address == device.address }

        if (device.isAvailable) {
            devices.add(device)
            devices.sortedBy { it.name }
        }

    }

//endregion

}