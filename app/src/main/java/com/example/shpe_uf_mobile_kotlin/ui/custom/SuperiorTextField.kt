package com.example.shpe_uf_mobile_kotlin.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R

/**
 * @author Andrei Ursu & google.com
 * @date September 6, 2024
 *
 * A custom text field with optional leading and trailing icons, label, and visual transformation.
 *
 * @param label The text displayed above the input field.
 * @param labelModifier Modifier for styling the label text.
 * @param value The current value of the text field.
 * @param onValueChange Callback invoked when the text field value changes.
 * @param leadingIcon Resource ID of the leading icon (optional).
 * @param trailingIcon Resource ID of the trailing icon (optional).
 * @param visualTransformation Optional transformation applied to the displayed text.
 * @param trailingIconOnClick Callback invoked when the trailing icon is clicked (optional).
 * @param keyboardOptions Options that control the keyboard behavior.
 */

@Composable
fun SuperiorTextField(
    label: String,
    labelModifier: Modifier,
    width: Dp = 310.dp,
    height: Dp = 38.dp,
    value: String,
    onValueChange: (String) -> Unit = {},
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    iconTrailingIcon: @Composable() (() -> Unit)? = null,
    visualTransformation: ((text: AnnotatedString) -> VisualTransformation)? = null,
    trailingIconOnClick: () -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    readOnly: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    leadingIconModifier: Modifier = Modifier,
) {

    //var text by remember { mutableStateOf("") }

//    val labelColor = if (isSystemInDarkTheme()) {
//        Color.White
//    } else {
//        Color.Black
//    }

    val labelColor = Color.White

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

        BasicTextField(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .width(width)
                .height(height),
            value = value,
            onValueChange = { onValueChange(it) },
            cursorBrush = SolidColor(Color.Black),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            textStyle = TextStyle(fontSize = 16.sp),
            readOnly = readOnly,
            visualTransformation = visualTransformation?.invoke(AnnotatedString(value)) ?: VisualTransformation.None,
            decorationBox = { innerTextField ->
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 1.83.dp, horizontal = 1.83.dp),
                    content = {
                        if (leadingIcon != null) {
                            Icon(
                                painter = painterResource(leadingIcon),
                                contentDescription = "Leading Icon",
                                modifier = leadingIconModifier
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 10.dp, end = 10.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            innerTextField()
                        }

                        if(iconTrailingIcon != null){
                            iconTrailingIcon()
                        }

                        if (trailingIcon != null) {
                            IconButton(
                                onClick = { trailingIconOnClick() }
                            ) {
                                Icon(
                                    painter = painterResource(trailingIcon),
                                    contentDescription = "Trailing Icon",
                                )
                            }

                        }
                    })
            })

        if(isError && errorMessage != null){
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = errorMessage,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.universltstd)),
                    fontWeight = FontWeight(400)
                ),
                color = MaterialTheme.colorScheme.error,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
        }
    }


}

