package companion.battery.ady

import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.provider.Settings
import androidx.compose.runtime.mutableStateListOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import companion.battery.ady.model.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.roundToInt

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

//region LiveData

    private val _devices = mutableStateListOf<Device>()
    val devicesWithInfo: List<Device> get() = _devices.filter { it.batteryAvailable }.sortedBy { it.name }
    val devicesWithoutInfo: List<Device> get() = _devices.filter { it.batteryUnavailable }.sortedBy { it.name }

//endregion

//region Public Methods

    fun getBluetoothDevices() {

        val bondedDevices = bluetoothAdapter.bondedDevices.orEmpty()

        val filteredDevices = bondedDevices
            .map { Device(it) }
            .filter { it.isConnected }
            .sortedBy { it.name }

        _devices.clear()
        _devices.addAll(filteredDevices)

        getCurrentInfo()
    }

    fun updateDevice(device: Device) {

        _devices.removeIf { it.id == device.id }

        if (device.isConnected)
            _devices.add(device)

    }

//endregion

//region Private Methods

    @SuppressLint("HardwareIds")
    private fun getCurrentInfo() {

        val deviceName  = Settings.Global.getString(BatteryCompanionApp.context.contentResolver, "device_name")

        val currentDevice = Device(
            name = deviceName,
            id = Settings.Secure.getString(BatteryCompanionApp.context.contentResolver, Settings.Secure.ANDROID_ID),
            battery = getCurrentBattery(),
            isConnected = true,
            majorDeviceClass = BluetoothClass.Device.Major.PHONE
        )

        updateDevice(currentDevice)

    }

    private fun getCurrentBattery() : Int {

        val batteryStatus: Intent? = IntentFilter(Intent.ACTION_BATTERY_CHANGED).let { filter ->
            BatteryCompanionApp.context.registerReceiver(null, filter)
        }

        val batteryPct: Float? = batteryStatus?.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level * 100 / scale.toFloat()
        }

        return batteryPct?.roundToInt() ?: -1

    }

//endregion

}