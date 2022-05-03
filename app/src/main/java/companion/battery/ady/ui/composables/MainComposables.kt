package companion.battery.ady.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import companion.battery.ady.MainViewModel
import companion.battery.ady.R
import companion.battery.ady.ui.theme.BatteryCompanionTheme

@Composable
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
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
@ExperimentalFoundationApi
@Composable
@ExperimentalMaterial3Api
fun DevicesList(viewModel: MainViewModel = viewModel()) {

    val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp / 2) - 12.dp

    LazyColumn(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 40.dp)
            .fillMaxSize(),
    ) {

        item {

            BackgroundText(
                text = stringResource(id = R.string.connected_devices),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.size(10.dp))

        }

        item {


            FlowRow(
                mainAxisSize = SizeMode.Expand,
                mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
            ) {

                viewModel.devicesWithInfo.forEach {

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

            Spacer(modifier = Modifier.size(40.dp))

            BackgroundText(
                text = stringResource(id = R.string.other_devices),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.size(10.dp))

            FlowRow(
                mainAxisSize = SizeMode.Expand,
                mainAxisAlignment = FlowMainAxisAlignment.SpaceBetween
            ) {

                previewDevices.forEach {

                    DeviceWithoutBatteryItem(
                        modifier = Modifier
                            .width(itemSize)
                            .animateItemPlacement(),
                        device = it
                    )


                }

            }

            ColorItemList()

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