package com.example.shpe_uf_mobile_kotlin.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R

/**
 * @author Anthony Zurita & google.com
 * @date March 3, 2024
 * A custom dropdown field that matches the SuperiorTextField styling.
 *
 * @param label The text displayed above the dropdown field.
 * @param labelModifier Modifier for styling the label text.
 * @param width Width of the dropdown field.
 * @param height Height of the dropdown field.
 * @param selectedOption The currently selected option.
 * @param options List of available options to display in the dropdown.
 * @param onOptionSelected Callback invoked when an option is selected.
 * @param leadingIcon Resource ID of the leading icon (optional).
 * @param isError Whether the field is in an error state.
 * @param errorMessage Error message to display (if isError is true).
 * @param leadingIconModifier Modifier for styling the leading icon.
 */
@Composable
fun SuperiorDropdownMenu(
    label: String,
    labelModifier: Modifier,
    width: Dp = 310.dp,
    height: Dp = 38.dp,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit,
    leadingIcon: Int? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIconModifier: Modifier = Modifier,
) {
    val labelColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.universltstd)),
                fontWeight = FontWeight(400)
            ),
            color = labelColor,
            modifier = labelModifier
        )

        Box(
            modifier = Modifier
                .width(width)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                    .height(height)
                    .fillMaxWidth()
                    .clickable { expanded = true }
                    .padding(vertical = 7.dp, horizontal = 7.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    if (leadingIcon != null) {
                        Icon(
                            painter = painterResource(leadingIcon),
                            contentDescription = "Leading Icon",
                            modifier = leadingIconModifier,
                            tint = Color.Gray
                        )
                    }

                    Text(
                        text = selectedOption.ifEmpty { "" },
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Black
                        ),
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }

                // Use arrow up when expanded, arrow down when collapsed
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                    contentDescription = if (expanded) "Collapse" else "Expand",
                    tint = Color.Black,
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(width)
                    .background(Color(0xFFEEEEEE)) // Light gray background for menu
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            )
                        },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp) // Make items taller to match your screenshot
                    )
                }
            }
        }

        if(isError && errorMessage != null){
            Text(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(width),
                text = errorMessage,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.universltstd)),
                    fontWeight = FontWeight(400)
                ),
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}