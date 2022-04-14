package companion.battery.ady

import android.Manifest
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import companion.battery.ady.ui.theme.BatteryCompanionTheme

class MainActivity : ComponentActivity() {

//region Variables

    val viewModel: MainViewModel by viewModels()

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

    }

    private fun getBluetoothPermission() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH_CONNECT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

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