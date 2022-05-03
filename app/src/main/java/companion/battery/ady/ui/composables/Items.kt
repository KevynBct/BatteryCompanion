package companion.battery.ady.ui.composables

import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import companion.battery.ady.model.Device

@ExperimentalMaterial3Api
@SuppressLint("MissingPermission")
@Composable
fun DeviceWithBatteryItem(
    modifier: Modifier,
    device: Device
) {

    Card(
        modifier = Modifier
            .then(modifier)
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
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
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
                    strokeWidth = 8.dp
                )

                SurfaceText(
                    text = "${device.battery} %",
                    fontWeight = FontWeight.SemiBold
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

            SurfaceText(
                text = device.name,
                fontSize = 17.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

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

@ExperimentalMaterial3Api
@Composable
@Preview
fun DevicePreview() {

    val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2) - 10.dp

    val devices = listOf(
        Device(
            name = "Phone",
            id = "A",
            battery = 76,
            isConnected = true,
            majorDeviceClass = BluetoothClass.Device.Major.PHONE
        ),
        Device(
            name = "Headphones",
            id = "B",
            battery = 18,
            isConnected = true,
            majorDeviceClass = BluetoothClass.Device.Major.AUDIO_VIDEO
        )
    )
    FlowRow(
        mainAxisSize = SizeMode.Expand,
        mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
    ) {

        devices.forEach {

            DeviceWithBatteryItem(
                modifier = Modifier.width(itemSize),
                device = it
            )


        }

    }

}