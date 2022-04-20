package companion.battery.ady

import android.Manifest
import android.app.Application
import android.bluetooth.BluetoothManager
import androidx.annotation.RequiresPermission
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import companion.battery.ady.model.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application
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

        val bondedDevices = bluetoothManager?.adapter?.bondedDevices.orEmpty()

        val filteredDevices = bondedDevices
            .map { Device(it) }
            .filter { d -> devices.none { d.address == it.address } }
            .sortedByDescending { it.isConnected }

        devices.addAll(filteredDevices)
    }

    fun updateDevice(device: Device) {

        devices.removeIf { it.address == device.address }
        devices.add(device)
        devices.sortByDescending { it.isConnected }

    }

//endregion

}