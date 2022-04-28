package companion.battery.ady.ui.composables

import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import companion.battery.ady.MainViewModel
import companion.battery.ady.model.Device
import companion.battery.ady.ui.theme.BatteryCompanionTheme

@Composable
@ExperimentalMaterial3Api
fun MainContent(viewModel: MainViewModel = viewModel()) {

    BatteryCompanionTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            if (viewModel.devicesWithInfo.isEmpty()) {

                EmptyContent()

            } else {

                DevicesList()


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
@ExperimentalMaterial3Api
fun DevicesList(viewModel: MainViewModel = viewModel()) {

    LazyColumn(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 8.dp)
            .fillMaxSize(),
    ) {

        item {

            val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2) - 10.dp

            FlowRow(
                mainAxisSize = SizeMode.Expand,
                mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
            ) {

                viewModel.devicesWithInfo.forEach { DeviceWithBatteryItem(device = it, width = itemSize) }

            }

        }

        item {

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                text = "Autres appareils",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.size(10.dp))

            viewModel.devicesWithoutInfo.forEach { DeviceWithoutBatteryItem(device = it) }

            Spacer(modifier = Modifier.size(40.dp))

            ColorItemList()

            Spacer(modifier = Modifier.navigationBarsPadding())

        }

    }

}

@ExperimentalMaterial3Api
@SuppressLint("MissingPermission")
@Composable
fun DeviceWithBatteryItem(width: Dp, device: Device) {

    Card(
        modifier = Modifier
            .width(width)
            .padding(vertical = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            
            SurfaceText(
                text = device.name,
                fontSize = 17.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {

                val color = if (device.battery <= 20) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    progress = device.battery / 100f,
                    color = color,
                    strokeWidth = 4.dp
                )

                SurfaceText(
                    text = "${device.battery} %"
                )


            }

        }

    }

}

@ExperimentalMaterial3Api
@SuppressLint("MissingPermission")
@Composable
fun DeviceWithoutBatteryItem(device: Device) {

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {

                Text(
                    text = device.name,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = device.id,
                    color = MaterialTheme.colorScheme.onSurface
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
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    contentDescription = null
                )

            }

        }

    }

}

@Composable
fun ColorItemList() {

    Column(horizontalAlignment = Alignment.Start) {

        ColorItem(color = MaterialTheme.colorScheme.primary, text = "primary")
        ColorItem(color = MaterialTheme.colorScheme.onPrimary, text = "onPrimary")
        ColorItem(color = MaterialTheme.colorScheme.primaryContainer, text = "primaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.onPrimaryContainer, text = "onPrimaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.inversePrimary, text = "inversePrimary")
        ColorItem(color = MaterialTheme.colorScheme.secondary, text = "secondary")
        ColorItem(color = MaterialTheme.colorScheme.onSecondary, text = "onSecondary")
        ColorItem(color = MaterialTheme.colorScheme.secondaryContainer, text = "secondaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.onSecondaryContainer, text = "onSecondaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.tertiary, text = "tertiary")
        ColorItem(color = MaterialTheme.colorScheme.onTertiary, text = "onTertiary")
        ColorItem(color = MaterialTheme.colorScheme.tertiaryContainer, text = "tertiaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.onTertiaryContainer, text = "onTertiaryContainer")
        ColorItem(color = MaterialTheme.colorScheme.background, text = "background")
        ColorItem(color = MaterialTheme.colorScheme.onBackground, text = "onBackground")
        ColorItem(color = MaterialTheme.colorScheme.surface, text = "surface")
        ColorItem(color = MaterialTheme.colorScheme.onSurface, text = "onSurface")
        ColorItem(color = MaterialTheme.colorScheme.surfaceVariant, text = "surfaceVariant")
        ColorItem(color = MaterialTheme.colorScheme.onSurfaceVariant, text = "onSurfaceVariant")
        ColorItem(color = MaterialTheme.colorScheme.surfaceTint, text = "surfaceTint")
        ColorItem(color = MaterialTheme.colorScheme.inverseSurface, text = "inverseSurface")
        ColorItem(color = MaterialTheme.colorScheme.inverseOnSurface, text = "inverseOnSurface")
        ColorItem(color = MaterialTheme.colorScheme.error, text = "error")
        ColorItem(color = MaterialTheme.colorScheme.onError, text = "onError")
        ColorItem(color = MaterialTheme.colorScheme.errorContainer, text = "errorContainer")
        ColorItem(color = MaterialTheme.colorScheme.onErrorContainer, text = "onErrorContainer")
        ColorItem(color = MaterialTheme.colorScheme.outline, text = "outline")

    }

}

@Composable
fun ColorItem(color: Color, text: String) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Box(modifier = Modifier
            .size(80.dp)
            .background(color = color))

        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground
        )

    }


}