package companion.battery.ady.extensions

import android.bluetooth.BluetoothClass
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

fun iconsFor(deviceClass: Int) : ImageVector = when (deviceClass) {
    BluetoothClass.Device.Major.AUDIO_VIDEO -> Icons.Outlined.Headphones
    BluetoothClass.Device.Major.PHONE -> Icons.Outlined.Phone
    BluetoothClass.Device.Major.WEARABLE -> Icons.Outlined.Watch
    BluetoothClass.Device.Major.HEALTH -> Icons.Outlined.HealthAndSafety
    BluetoothClass.Device.Major.COMPUTER -> Icons.Outlined.Computer
    else -> Icons.Outlined.Bluetooth
}