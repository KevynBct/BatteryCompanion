package companion.battery.ady

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import companion.battery.ady.broadcasts.BatteryDeviceBroadcastListener
import companion.battery.ady.broadcasts.BatteryDeviceBroadcastReceiver
import companion.battery.ady.broadcasts.BluetoothBroadcastListener
import companion.battery.ady.broadcasts.BluetoothBroadcastReceiver
import companion.battery.ady.model.Device
import companion.battery.ady.ui.composables.MainContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
class MainActivity : ComponentActivity(), BluetoothBroadcastListener, BatteryDeviceBroadcastListener {

//region Variables

    private val viewModel: MainViewModel by viewModels()
    private val batteryDeviceBroadcastReceiver = BatteryDeviceBroadcastReceiver()
    private val bluetoothBroadcastReceiver = BluetoothBroadcastReceiver()

//endregion

//region Lifecycle

    override fun onCreate(savedInstanceState: Bundle?) {

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            WindowCompat.setDecorFitsSystemWindows(window, false)
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }

        super.onCreate(savedInstanceState)

        setContent { MainContent() }

    }

    override fun onResume() {
        super.onResume()

        getBluetoothDevices()
    }

    override fun onStart() {
        super.onStart()

        registerBroadcastReceivers()
    }

    override fun onStop() {
        super.onStop()

        unregisterBroadcastReceivers()
    }

//endregion

//region Private Methods

    private fun registerBroadcastReceivers() {

        batteryDeviceBroadcastReceiver.listener = this
        registerReceiver(batteryDeviceBroadcastReceiver, BatteryDeviceBroadcastReceiver.filters)

        bluetoothBroadcastReceiver.listener = this
        registerReceiver(bluetoothBroadcastReceiver, BluetoothBroadcastReceiver.filters)

    }

    private fun unregisterBroadcastReceivers() {

        batteryDeviceBroadcastReceiver.listener = null
        unregisterReceiver(batteryDeviceBroadcastReceiver)

        bluetoothBroadcastReceiver.listener = null
        unregisterReceiver(bluetoothBroadcastReceiver)

    }

    private fun getBluetoothDevices() {

        val permissionsGranted = requiredPermissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }

        if (permissionsGranted)
            viewModel.getBluetoothDevices()
        else
            requestPermissionLauncher.launch(requiredPermissions)

    }

//endregion

//region Permissions

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

        if (permissions.all { it.value })
            getBluetoothDevices()
        else
            Toast.makeText(this, getString(R.string.permissions_needed), Toast.LENGTH_SHORT).show()

    }

    private val requiredPermissions = buildList {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            add(Manifest.permission.BLUETOOTH_CONNECT)
        else
            add(Manifest.permission.ACCESS_FINE_LOCATION)

    }.toTypedArray()


//endregion

//region Broadcast receiver

    override fun onChargingStatusChange(isCharging: Boolean) {

        viewModel.currentDevice.isCharging = isCharging
        viewModel.updateDevice(device = viewModel.currentDevice)

    }

    override fun onBroadcastReceive(device: Device) {
        viewModel.updateDevice(device = device)
    }

//endregion

}