package companion.battery.ady

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
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
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import companion.battery.ady.ui.theme.BatteryCompanionTheme

class MainActivity : ComponentActivity() {

//region Variables

    val viewModel: MainViewModel by viewModels()

    private val requestBluetoothPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->

        if (isGranted)
            getBlueToothDevices()
        else
            Toast.makeText(this, "Permission non accordée", Toast.LENGTH_SHORT).show()

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

        setContent { MainContent() }

        getBluetoothPermission()

    }

    private fun getBluetoothPermission() {

        when (PackageManager.PERMISSION_GRANTED) {

            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) -> getBlueToothDevices()
            else -> {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
                    requestBluetoothPermissionLauncher.launch(Manifest.permission.BLUETOOTH_CONNECT)

            }
        }

    }

    private fun getBlueToothDevices() {

        val bluetoothManager: BluetoothManager? = ContextCompat.getSystemService(BatteryCompanionApp.context, BluetoothManager::class.java)
        val bluetoothAdapter: BluetoothAdapter = bluetoothManager?.adapter ?: return

        Toast.makeText(this, "getBlueToothDevices", Toast.LENGTH_SHORT).show()

    }

//endregion

}

//region Composable

@Composable
fun MainContent() {

    BatteryCompanionTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            Content()

        }
    }

}

@Composable
fun Content() {

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        for (i in 0..100) {

            Text(
                text = "Hello M3 theming : primary",
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Hello M3 theming : secondary",
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "Hello M3 theming : tertiary",
                color = MaterialTheme.colorScheme.tertiary
            )

        }

        Spacer(modifier = Modifier.navigationBarsPadding())

    }


}

//endregion