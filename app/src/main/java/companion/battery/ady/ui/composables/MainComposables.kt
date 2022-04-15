package companion.battery.ady.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import companion.battery.ady.Device
import companion.battery.ady.MainViewModel
import companion.battery.ady.ui.theme.BatteryCompanionTheme

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