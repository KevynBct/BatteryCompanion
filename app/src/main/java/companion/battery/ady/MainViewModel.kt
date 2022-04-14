package companion.battery.ady

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

//region Properties

    private val bluetoothManager: BluetoothManager = BatteryCompanionApp.context.getSystemService(BluetoothManager::class.java)
    val bluetoothAdapter: BluetoothAdapter = bluetoothManager.adapter

//endregion

//region Lifecycle

    private val devices = mutableListOf<BluetoothDevice>()

//endregion

//region Private Methods

    fun getBluetoothDevices() {



    }

//endregion

}