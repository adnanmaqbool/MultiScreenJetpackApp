package com.axelliant.wearhouse.rowItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axelliant.wearhouse.R
import com.axelliant.wearhouse.component.CustomButton
import com.axelliant.wearhouse.component.MediumTextView
import com.axelliant.wearhouse.component.SmallTextView
import com.axelliant.wearhouse.constants.KeyConst
import com.axelliant.wearhouse.model.order.OrderListDetail
import com.axelliant.wearhouse.ui.theme.AppColor
import com.axelliant.wearhouse.ui.theme.GreyColor
import com.axelliant.wearhouse.ui.theme.WhiteColor
import com.axelliant.wearhouse.ui.theme.dimens
import com.axelliant.wearhouse.utils.Utils

@Composable
fun ListItemView(orderDetail: OrderListDetail) {
    val uiColor = if (isSystemInDarkTheme()) GreyColor else WhiteColor


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.dimens.d15) // Adds spacing from the top
            .shadow(
                elevation = MaterialTheme.dimens.d5, // Shadow elevation
                shape = RoundedCornerShape(
                    topStart = MaterialTheme.dimens.d10,
                    topEnd = MaterialTheme.dimens.d10,
                    bottomStart = MaterialTheme.dimens.d10,
                    bottomEnd = MaterialTheme.dimens.d10
                ),
                clip = false // To prevent shadow from being clipped
            )
            .clip(
                RoundedCornerShape(
                    topStart = MaterialTheme.dimens.d10,
                    topEnd = MaterialTheme.dimens.d10,
                    bottomStart = MaterialTheme.dimens.d10,
                    bottomEnd = MaterialTheme.dimens.d10
                )
            )
            .background(uiColor)

    ) {

        Column {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.dimens.d10,
                        bottom = MaterialTheme.dimens.d10,
                        start = MaterialTheme.dimens.d5,
                        end = MaterialTheme.dimens.d5
                    ),
                verticalAlignment = Alignment.Top,

                ) {
                Image(
                    modifier = Modifier
                        .width(MaterialTheme.dimens.d30)
                        .height(MaterialTheme.dimens.d30),
                    painter = painterResource(id = R.drawable.order_icon),
                    contentDescription = "Order icon"
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    MediumTextView(
                        textString = orderDetail.title.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = MaterialTheme.dimens.d5)
                    )
                    SmallTextView(
                        textString = "#".plus(orderDetail.id.toString()),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = MaterialTheme.dimens.d5)
                    )
                }

                CustomButton(
                    onClick = {
                    },
                    text = if (orderDetail.inBound == true) "IN Bound" else "Out Bound",  // Correct syntax for conditional assignment
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColor,
                        contentColor = WhiteColor
                    ),
                    shape = RoundedCornerShape(MaterialTheme.dimens.d20) // Custom shape
                    ,
                    modifier = Modifier
                        .weight(1f)// Wrap content width to fit the button
                        .padding(start = MaterialTheme.dimens.d10)
                )// Add padding to avoid overlap


            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = MaterialTheme.dimens.d5,
                        bottom = MaterialTheme.dimens.d10,
                        start = MaterialTheme.dimens.d10,
                        end = MaterialTheme.dimens.d10
                    ),
                thickness = 1.dp, // Set the thickness of the divider
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f) // Color of the divider
            )

            Row(modifier = Modifier.fillMaxWidth().padding(MaterialTheme.dimens.d10))
            {
                Column(modifier = Modifier
                    .weight(1f)) {

                    SmallTextView(
                        textString = Utils.unixToDateTime(orderDetail.shipping_time, KeyConst.DATE_),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = MaterialTheme.dimens.d5)
                    )

                    MediumTextView(
                        textString = orderDetail.shipper?.location?.address.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = MaterialTheme.dimens.d5)
                    )

                }

                Box(
                    modifier = Modifier
                        .width(1.dp) // Width of the divider line
                        .fillMaxHeight() // Make sure the line takes up the full height of the Row
                        .background(GreyColor) // Color of the divider // Color of the vertical line
                )

                Column(modifier = Modifier
                    .weight(1f)
                    ) {
                    SmallTextView(
                        textString = Utils.unixToDateTime(orderDetail.receiving_time, KeyConst.DATE_),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = MaterialTheme.dimens.d5)
                    )

                    MediumTextView(
                        textString = orderDetail.recipient?.location?.address.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = MaterialTheme.dimens.d5)
                    )
                }


            }


        }

    }


}


@Preview(showBackground = true)
@Composable
fun ListItemViewPreview() {
    ListItemView(OrderListDetail())
}