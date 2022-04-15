package companion.battery.ady

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import companion.battery.ady.ui.theme.BatteryCompanionTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
        registerReceiver(broadcastReceiver, BluetoothBroadcastReceiver.BluetoothBroadcastFilter)
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

}

//region Composable

@Composable
fun MainContent(onRetryButtonTap: () -> Unit) {

    BatteryCompanionTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Content(onRetryButtonTap = onRetryButtonTap)

        }
    }

}

@SuppressLint("MissingPermission")
@Composable
fun Content(
    onRetryButtonTap: () -> Unit,
    viewModel: MainViewModel = viewModel()
) {

    val devices by viewModel.devices.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        if (devices.isEmpty()) {

            Button(
                onClick = { onRetryButtonTap() },
                content = {
                    Text(
                        text = "Actualiser",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )

        } else {

            devices.forEach { BluetoothDeviceItem(device = it) }

        }

        Spacer(modifier = Modifier.navigationBarsPadding())

    }


}

@SuppressLint("MissingPermission")
@Composable
fun BluetoothDeviceItem(device: Device) {

    Column(
        modifier = Modifier
            .alpha(if (device.isConnected) 1f else .5f)
            .fillMaxWidth()
            .padding(all = 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {

        Text(
            text = device.name,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp
        )

        Text(
            text = device.name,
            color = MaterialTheme.colorScheme.secondary
        )

    }

}

//endregion