package companion.battery.ady.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
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
                fontSize = 17.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold
            )

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {

                val (indicator, icon, text) = createRefs()

                BatteryIndicator(
                    modifier = Modifier.constrainAs(indicator) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    battery = device.battery
                )

                SurfaceText(
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    text = "${device.battery}%",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                )

                Icon(
                    modifier = Modifier
                        .constrainAs(icon) {
                            bottom.linkTo(text.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                        .alpha(if (device.isCharging) 1f else 0f),
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                
            }

        }

    }

}

@ExperimentalMaterial3Api
@SuppressLint("MissingPermission")
@Composable
fun DeviceWithoutBatteryItem(
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            SurfaceText(
                modifier = Modifier.weight(1f),
                text = device.name,
                fontSize = 17.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
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
fun BatteryIndicator(
    modifier: Modifier = Modifier,
    battery: Int
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        val color = if (battery <= 20) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary

        CircularProgressIndicator(
            modifier = Modifier
                .alpha(.1f)
                .fillMaxWidth(.9f)
                .aspectRatio(1f),
            progress = 1f,
            color = color,
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