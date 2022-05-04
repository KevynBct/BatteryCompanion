package companion.battery.ady.ui.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .padding(horizontal = 8.dp)
            .fillMaxSize(),
    ) {

        item { Spacer(modifier = Modifier.size(40.dp)) }

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

                viewModel.devicesWithoutInfo.forEach {

                    DeviceWithoutBatteryItem(
                        modifier = Modifier
                            .width(itemSize)
                            .animateItemPlacement(),
                        device = it
                    )


                }

            }

        }

        item { Spacer(modifier = Modifier.size(40.dp)) }

    }

}