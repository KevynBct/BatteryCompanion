package companion.battery.ady.ui.composables

import android.bluetooth.BluetoothClass
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import companion.battery.ady.model.Device

val previewDevices = listOf(
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
    ),
    Device(
        name = "Wear",
        id = "C",
        battery = 45,
        isConnected = true,
        majorDeviceClass = BluetoothClass.Device.Major.WEARABLE
    )
)

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
@Preview
fun DevicePreview() {

    val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2) - 10.dp

    LazyColumn(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = 8.dp)
            .fillMaxSize(),
    ) {

        item {

            Spacer(modifier = Modifier.size(40.dp))

        }

        item {


            FlowRow(
                mainAxisSize = SizeMode.Expand,
                mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
            ) {

                previewDevices.forEach {

                    DeviceWithBatteryItem(
                        modifier = Modifier
                            .width(itemSize)
                            .animateItemPlacement(),
                        device = it
                    )


                }

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

            previewDevices.forEach { DeviceWithoutBatteryItem(device = it) }

            Spacer(modifier = Modifier.size(40.dp))

        }

    }

}