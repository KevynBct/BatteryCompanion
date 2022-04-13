package companion.battery.ady

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import companion.battery.ady.ui.theme.BatteryCompanionTheme

fun Context.getActivity(): ComponentActivity? = when (this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            WindowCompat.setDecorFitsSystemWindows(window, false)
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }

        super.onCreate(savedInstanceState)

        setContent {

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
    }

}

@Composable
fun Content() {

    val activity = LocalContext.current.getActivity() ?: return

    val windowInsets = ViewCompat.getRootWindowInsets(activity.window.decorView)
    val systemBarHeight = with(LocalDensity.current) { (windowInsets?.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.systemBars())?.top ?: 0).toDp() }
    val navigationBarHeight = with(LocalDensity.current) { (windowInsets?.getInsetsIgnoringVisibility(WindowInsetsCompat.Type.navigationBars())?.bottom ?: 0).toDp() }

    Column(
        modifier = Modifier
            .padding(top = systemBarHeight)
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

        Spacer(modifier = Modifier.height(navigationBarHeight))

    }


}
