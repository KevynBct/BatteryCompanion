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
import companion.battery.ady.models.Device
import companion.battery.ady.ui.composables.MainContent
import companion.battery.ady.ui.theme.BatteryCompanionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//region Variables

    private val viewModel: MainViewModel by viewModels()
    private val broadcastReceiver = BluetoothBroadcastReceiver()

    private val bluetoothBroadcastFilter = IntentFilter().apply {
        addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
        addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)
        addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
    }

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
        registerReceiver(broadcastReceiver, bluetoothBroadcastFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

//endregion

//region Observers

//endregion

//region Permissions

    private fun getPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED)
            viewModel.getBluetoothDevices()
        else
            requestPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)

    }

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->

        if (isGranted)
            getPermissions()
        else
            Toast.makeText(this, "Permission non accordée", Toast.LENGTH_SHORT).show()

    }

//endregion

//region Broadcast receiver

    class BluetoothBroadcastReceiver: BroadcastReceiver() {

        @SuppressLint("MissingPermission")
        override fun onReceive(context: Context?, intent: Intent?) {

            val action = intent?.action
            val bluetoothDevice: BluetoothDevice = intent?.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) ?: return

            val device = Device(
                name = bluetoothDevice.name,
                isConnected = false,
                macAddress = bluetoothDevice.address
            )

            when {
                BluetoothDevice.ACTION_FOUND == action -> {
                    //Device found
                }
                BluetoothDevice.ACTION_ACL_CONNECTED == action -> {
                    device.isConnected = true
                    //Device is now connected
                }
                BluetoothDevice.ACTION_ACL_DISCONNECTED == action -> {
                    device.isConnected = false
                    //Device has disconnected
                }
            }

        }

    }

//endregion

}