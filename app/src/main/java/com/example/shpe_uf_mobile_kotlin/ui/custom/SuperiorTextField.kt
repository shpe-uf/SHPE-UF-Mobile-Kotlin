package com.example.shpe_uf_mobile_kotlin.ui.custom

import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.ui.theme.OrangeSHPE


@Preview(showBackground = true)
@Composable
fun SuperiorTextFieldPreview() {
    Column(
        modifier = Modifier
            .background(OrangeSHPE)
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SuperiorTextField(
            leadingIcon = R.drawable.profile_circle,
            trailingIcon = R.drawable.state_selected
        )
    }
}

@Composable
fun SuperiorTextField(
    leadingIcon: Int? = null,
    trailingIcon: Int? = null,
) {

    var text by remember { mutableStateOf("") }

    BasicTextField(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(10.dp))
            .width(310.dp)
            .height(38.dp),
        value = text,
        onValueChange = { text = it },
        cursorBrush = SolidColor(Color.Black),
        singleLine = true,
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
                        modifier = Modifier.fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.CenterStart,
                        content = {
                            Text(
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 25.dp),
                                text = text,
                                color = Color.Black
                            )

                        }

                    )
                    //innerTextField()
                    if (trailingIcon != null) {
                        Icon(
                            painter = painterResource(trailingIcon),
                            contentDescription = "Trailing Icon",
                            modifier = Modifier.padding(end = 11.dp)
                        )
                    }


                })
        })
}

