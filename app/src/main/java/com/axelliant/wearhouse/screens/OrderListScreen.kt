package com.axelliant.wearhouse.screens

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.axelliant.wearhouse.R
import com.axelliant.wearhouse.component.HeadingTextView
import com.axelliant.wearhouse.component.MediumTextView
import com.axelliant.wearhouse.datastore.SessionManager
import com.axelliant.wearhouse.dialog.LoadingDialog
import com.axelliant.wearhouse.model.login.LoginRequest
import com.axelliant.wearhouse.model.order.Location
import com.axelliant.wearhouse.model.order.OrderListDetail
import com.axelliant.wearhouse.model.order.Recipient
import com.axelliant.wearhouse.model.order.Shipper
import com.axelliant.wearhouse.network.ApiHandler
import com.axelliant.wearhouse.network.ApiInterface
import com.axelliant.wearhouse.repos.OrderRepo
import com.axelliant.wearhouse.rowItem.ListItemView
import com.axelliant.wearhouse.ui.theme.AppColor
import com.axelliant.wearhouse.ui.theme.GreyColor
import com.axelliant.wearhouse.ui.theme.dimens
import com.axelliant.wearhouse.viewModel.OrderViewModel
import com.axelliant.wearhouse.viewModel.OrderViewModelFactory
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import okhttp3.ResponseBody
import retrofit2.Call



@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun OrderListScreen() {
    val context: Context = LocalContext.current
    val sessionManager = SessionManager(context)

    val orderViewModel: OrderViewModel = viewModel(
        factory = OrderViewModelFactory(
            sessionManager,
            OrderRepo(apiInterface = ApiHandler.getApiInterface()!!)
        )
    )
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    LaunchedEffect(key1 = Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    orderViewModel.getOrderList()

    // State to keep track of which box is selected (0 for none, 1 for first, 2 for second)
    var selectedBox by remember { mutableStateOf(1) }

    // Toggle buttons
    Column(modifier = Modifier.fillMaxSize()) {
        // Row for IN/OUT Bound buttons
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.d10)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(MaterialTheme.dimens.d10))
                    .background(if (selectedBox == 2) GreyColor else AppColor)
                    .clickable {
                        selectedBox = 1
                        orderViewModel.filterOrders(isInBound = true) // Show inbound orders
                    }
                    .padding(MaterialTheme.dimens.d10),
                contentAlignment = Alignment.Center
            ) {
                MediumTextView(textString = "IN Bound")
            }

            Spacer(modifier = Modifier.width(MaterialTheme.dimens.d10))

            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(MaterialTheme.dimens.d10))
                    .background(if (selectedBox == 1) GreyColor else AppColor)
                    .clickable {
                        selectedBox = 2
                        orderViewModel.filterOrders(isInBound = false) // Show outbound orders
                    }
                    .padding(MaterialTheme.dimens.d10),
                contentAlignment = Alignment.Center
            ) {
                MediumTextView(textString = "OUT Bound")
            }
        }

        // Order List
        OrderListRendering(orderViewModel)
    }
}

@Composable
private fun OrderListRendering(orderViewModel: OrderViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(MaterialTheme.dimens.d15),
        contentAlignment = Alignment.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            HeadingTextView(textString = "Order Listing")

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.d20))

            LazyColumn(
                modifier = Modifier.fillMaxHeight()
            ) {
                if (orderViewModel.filteredOrderList.value.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .fillParentMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            MediumTextView(
                                textString = stringResource(id = R.string.no_order_found),
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                } else {
                    items(orderViewModel.filteredOrderList.value) { order ->
                        ListItemView(order)
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.dimens.d20))
        }
    }
}


