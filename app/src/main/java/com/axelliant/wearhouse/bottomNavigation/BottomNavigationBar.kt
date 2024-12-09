package com.axelliant.wearhouse.bottomNavigation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.axelliant.wearhouse.ui.theme.GreyColor
import com.axelliant.wearhouse.ui.theme.WhiteColor
import com.axelliant.wearhouse.ui.theme.dimens

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    onItemClick: (BottomNavItem) -> Unit,
    currentItemRoute:String
) {
    val uiColor = if (isSystemInDarkTheme()) GreyColor else WhiteColor


    BottomNavigation(
        modifier = Modifier.fillMaxWidth().clip(
            RoundedCornerShape(
                topStart = MaterialTheme.dimens.d50, // Top left corner
                topEnd = MaterialTheme.dimens.d50    // Top right corner
            )
        ).height(MaterialTheme.dimens.d40),
        backgroundColor = uiColor,
        elevation = MaterialTheme.dimens.d30
    ) {

        items.forEach { item ->

      /*      selectedContentColor = AppColor,
            unselectedContentColor = GreyColor,*/

            BottomNavigationItem(

                alwaysShowLabel = false, // save from icon tinting
                selected = item.itemName == currentItemRoute,
                onClick = { onItemClick(item) },
                icon = {
                    if(item.itemName == currentItemRoute){
                        item.selectedIcon?.let {
                            Icon(
                                modifier = Modifier.width(MaterialTheme.dimens.d20).height(MaterialTheme.dimens.d20),
                                painter = it, // Use the painterResource
                                contentDescription = item.itemName,
                                tint = Color.Unspecified // Prevent tinting

                            )
                        }
                    }else{
                        item.unSelectedIcon?.let {
                            Icon(
                                modifier = Modifier.width(MaterialTheme.dimens.d20).height(MaterialTheme.dimens.d20),
                                painter = it, // Use the painterResource
                                contentDescription = item.itemName,
                                tint = Color.Unspecified // Prevent tinting

                            )
                        }
                    }


                })

        }

    }

}

/*@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    currentItemRoute: String,
    onItemClick: (BottomNavItem) -> Unit
) {
    val uiColor = if (isSystemInDarkTheme()) GreyColor else WhiteColor

    // Wrap the BottomNavigation in a Box to simulate a shadow with grey color

    val dim = MaterialTheme.dimens.d50
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(dim) // Slightly increase height to account for shadow
            .graphicsLayer {
                shadowElevation = 8f // Set elevation for shadow
                shape = RoundedCornerShape(
                    topStart = dim,
                    topEnd = dim
                )
                clip = true // Apply clipping to match rounded corners
            }
            .shadow(
                elevation = MaterialTheme.dimens.d30,
                shape = RoundedCornerShape(
                    topStart = dim,
                    topEnd = dim
                ),
                clip = false // Ensure shadow is outside the box shape
            )
    ) {
        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = dim,
                        topEnd = dim
                    )
                )
                .height(MaterialTheme.dimens.d40),
            backgroundColor = uiColor,
            elevation = MaterialTheme.dimens.d0 // Disable default elevation
        ) {
            items.forEach { item ->
                BottomNavigationItem(
                    selected = item.itemName == currentItemRoute,
                    onClick = { onItemClick(item) },
                    icon = {
                        if (item.itemName == currentItemRoute) {
                            item.selectedIcon?.let {
                                Icon(
                                    painter = it,
                                    contentDescription = item.itemName,
                                    modifier = Modifier
                                        .width(MaterialTheme.dimens.d20)
                                        .height(MaterialTheme.dimens.d20),
                                    tint = Color.Unspecified // Prevent tinting
                                )
                            }
                        } else {
                            item.unSelectedIcon?.let {
                                Icon(
                                    painter = it,
                                    contentDescription = item.itemName,
                                    modifier = Modifier
                                        .width(MaterialTheme.dimens.d20)
                                        .height(MaterialTheme.dimens.d20),
                                    tint = Color.Unspecified // Prevent tinting
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}*/

