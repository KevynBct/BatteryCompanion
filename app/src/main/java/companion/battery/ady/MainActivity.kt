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
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import companion.battery.ady.broadcasts.BluetoothBroadcastListener
import companion.battery.ady.broadcasts.BluetoothBroadcastReceiver
import companion.battery.ady.model.Device
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

        setContent { MainContent() }

    }

    override fun onResume() {
        super.onResume()

        getBluetoothDevices()

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

//region Private Methods

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
            Toast.makeText(this, "Permissions requises", Toast.LENGTH_SHORT).show()

    }

    private val requiredPermissions = buildList {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            add(Manifest.permission.BLUETOOTH_CONNECT)
        else
            add(Manifest.permission.ACCESS_FINE_LOCATION)

    }.toTypedArray()


//endregion

//region Broadcast receiver

    override fun onBroadcastReceive(device: Device) {
        viewModel.updateDevice(device = device)
    }

//endregion

}