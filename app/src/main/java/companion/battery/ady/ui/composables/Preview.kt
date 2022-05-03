package companion.battery.ady.ui.composables

import android.bluetooth.BluetoothClass
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import companion.battery.ady.model.Device

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
        ),
        Device(
            name = "Wear",
            id = "C",
            battery = 45,
            isConnected = true,
            majorDeviceClass = BluetoothClass.Device.Major.WEARABLE
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