package companion.battery.ady.ui.composables

import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import companion.battery.ady.MainViewModel
import companion.battery.ady.model.Device
import companion.battery.ady.ui.theme.BatteryCompanionTheme

@Composable
fun MainContent(viewModel: MainViewModel = viewModel()) {

    BatteryCompanionTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            if (viewModel.devices.isEmpty()) {

                EmptyContent()

            } else {

                DevicesList(devices = viewModel.devices)


            }

        }
    }

}

@Composable
fun EmptyContent() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Aucun appareil disponible",
            color = MaterialTheme.colorScheme.tertiary
        )

    }

}
@Composable
fun DevicesList(devices: List<Device>) {

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {

        devices.forEach { BluetoothDeviceItem(device = it) }

        Spacer(modifier = Modifier.navigationBarsPadding())

    }

}

@SuppressLint("MissingPermission")
@Composable
fun BluetoothDeviceItem(device: Device) {

    val isConnected = device.isConnected

    Row(modifier = Modifier
        .alpha(if (isConnected) 1f else .5f)
        .fillMaxWidth()
        .padding(all = 8.dp)
        .clip(RoundedCornerShape(8.dp))
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.tertiary,
            shape = RoundedCornerShape(8.dp)
        )
        .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {

            Text(
                text = device.name,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp
            )

            Text(
                text = device.id,
                color = MaterialTheme.colorScheme.secondary
            )

        }

        Column {

            val icon = when (device.majorDeviceClass) {
                BluetoothClass.Device.Major.AUDIO_VIDEO -> Icons.Outlined.Headphones
                BluetoothClass.Device.Major.PHONE -> Icons.Outlined.Phone
                BluetoothClass.Device.Major.WEARABLE -> Icons.Outlined.Watch
                BluetoothClass.Device.Major.HEALTH -> Icons.Outlined.HealthAndSafety
                BluetoothClass.Device.Major.COMPUTER -> Icons.Outlined.Computer
                else -> Icons.Outlined.Bluetooth
            }

            Icon(
                modifier = Modifier.size(15.dp),
                imageVector = icon,
                tint = if (isConnected) Color.Green else Color.Gray,
                contentDescription = null
            )

            if (isConnected && device.battery >= 0) {

                Text(
                    text = "${device.battery} %",
                    color = MaterialTheme.colorScheme.secondary
                )

            }

        }

    }

}