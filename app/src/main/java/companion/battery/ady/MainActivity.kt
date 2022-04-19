package companion.battery.ady

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import companion.battery.ady.extensions.batteryLevel
import companion.battery.ady.extensions.isConnected
import companion.battery.ady.models.Device
import companion.battery.ady.ui.composables.MainContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity(), BluetoothBroadcastListener {

//region Variables

    private val viewModel: MainViewModel by viewModels()
    private val broadcastReceiver = BluetoothBroadcastReceiver()

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

        getPermissions()

        setContent { MainContent(onRetryButtonTap = { getPermissions() }) }

    }

    override fun onStart() {
        super.onStart()
        broadcastReceiver.listener = this
        registerReceiver(broadcastReceiver, BluetoothBroadcastReceiver.filters)
    }

    override fun onStop() {
        super.onStop()
        broadcastReceiver.listener = null
        unregisterReceiver(broadcastReceiver)
    }

//endregion

//region Observers

//endregion

//region Permissions

    private fun getPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED)
                viewModel.getBluetoothDevices()
            else
                requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH)

        } else {

            requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)

        }

    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->

        if (isGranted)
            getPermissions()
        else
            Toast.makeText(this, "Permission non accordée", Toast.LENGTH_SHORT).show()

    }

    override fun onBroadcastReceive(device: Device) {
        viewModel.updateDevice(device = device)
    }

//endregion

//region Broadcast receiver

    /*inner class BluetoothBroadcastReceiver: BroadcastReceiver() {

        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {

            val bluetoothDevice: BluetoothDevice = intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) ?: return

            val device = Device(
                name = bluetoothDevice.name,
                isConnected = bluetoothDevice.isConnected,
                macAddress = bluetoothDevice.address,
                battery = bluetoothDevice.batteryLevel
            )

            viewModel.updateDeviceStatus(device = device)

        }

    }*/

//endregion

}


interface BluetoothBroadcastListener {
    fun onBroadcastReceive(device: Device)
}

class BluetoothBroadcastReceiver: BroadcastReceiver() {

    var listener: BluetoothBroadcastListener? = null

    companion object {

        val filters = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
            addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
        }

    }

    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {

        val bluetoothDevice: BluetoothDevice = intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) ?: return

        val device = Device(
            name = bluetoothDevice.name,
            isConnected = bluetoothDevice.isConnected,
            macAddress = bluetoothDevice.address,
            battery = bluetoothDevice.batteryLevel
        )

        listener?.onBroadcastReceive(device = device)

    }

}