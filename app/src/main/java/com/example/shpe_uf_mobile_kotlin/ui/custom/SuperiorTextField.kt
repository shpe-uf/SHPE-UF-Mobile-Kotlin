package com.example.shpe_uf_mobile_kotlin.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE

@Composable
fun TextFields() {
    Column(
        modifier = Modifier
            .background(OrangeSHPE)
            .fillMaxSize()
            .padding(top = 250.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UsernameTextField()
        PasswordTextField()
    }
}

@Preview(showBackground = true)
@Composable
fun UsernameTextField() {

    SuperiorTextField(
        label = "Username",
        labelModifier = Modifier.padding(horizontal = 11.dp, vertical = 6.53.dp),
        value = "",
        leadingIcon = R.drawable.profile_circle,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Preview(showBackground = true)
@Composable
fun PasswordTextField() {

    var isPasswordVisible by remember { mutableStateOf(false) }
    val image = if(isPasswordVisible)
        R.drawable.state_selected
    else
        R.drawable.state_default

    SuperiorTextField(
        label = "Password",
        labelModifier = Modifier.padding(horizontal = 9.22.dp, vertical = 5.53.dp),
        value = "",
        leadingIcon = R.drawable.lock_3,
        trailingIcon = image,
        trailingIconOnClick = {isPasswordVisible = !isPasswordVisible},
        visualTransformation = {if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation() },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )

}

@Composable
fun SuperiorTextField(
    label: String,
    labelModifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
    visualTransformation: ((text: AnnotatedString) -> VisualTransformation)? = null,
    trailingIconOnClick: () -> Unit = {},
    keyboardOptions: KeyboardOptions,
) {

    //var text by remember { mutableStateOf("") }

    val labelColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }

    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(400)
            ),
            color = labelColor,
            modifier = labelModifier
        )

        BasicTextField(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(10.dp))
                .width(310.dp)
                .height(38.dp),
            value = value,
            onValueChange = { onValueChange(it) },
            cursorBrush = SolidColor(Color.Black),
            singleLine = true,
            keyboardOptions = keyboardOptions,
            textStyle = TextStyle(fontSize = 16.sp),
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
                                modifier = Modifier.padding(start = 12.dp)
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
    }


}

