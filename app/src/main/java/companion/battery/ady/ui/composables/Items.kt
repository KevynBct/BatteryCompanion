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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import companion.battery.ady.extensions.iconsFor
import companion.battery.ady.extensions.mirror
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
            .padding(vertical = 4.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 24.dp, vertical = 16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {

            SurfaceText(
                text = device.name,
                fontSize = 20.sp,
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

                BatteryIndicator(battery = device.battery)

                SurfaceText(
                    text = "${device.battery}%",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            SurfaceText(
                text = device.name,
                fontSize = 20.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )

            Icon(
                modifier = Modifier.size(15.dp),
                imageVector = iconsFor(device.majorDeviceClass),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                contentDescription = null
            )

        }

    }

}

@Composable
fun BatteryIndicator(battery: Int) {

    Box(contentAlignment = Alignment.Center) {

        val color = if (battery <= 20) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
        val backgroundColor = if (battery <= 20) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer

        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth(.9f)
                .aspectRatio(1f),
            progress = 1f,
            color = backgroundColor,
            strokeWidth = 8.dp
        )

        CircularProgressIndicator(
            modifier = Modifier
                .mirror()
                .fillMaxWidth(.9f)
                .aspectRatio(1f),
            progress = battery / 100f,
            color = color,
            strokeWidth = 8.dp
        )

    }

}