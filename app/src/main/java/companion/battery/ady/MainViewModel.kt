package companion.battery.ady

import android.Manifest
import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {

//region Properties

    private val bluetoothManager: BluetoothManager = application.applicationContext.getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter

//endregion

//region Lifecycle

    val devices = mutableListOf<BluetoothDevice>()

//endregion

//region Public Methods

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun getBluetoothDevices() {

        val bluetoothManager: BluetoothManager? = ContextCompat.getSystemService(BatteryCompanionApp.context, BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter = bluetoothManager?.adapter ?: return

        devices.clear()
        devices.addAll(bluetoothAdapter.bondedDevices)

    }

//endregion

}