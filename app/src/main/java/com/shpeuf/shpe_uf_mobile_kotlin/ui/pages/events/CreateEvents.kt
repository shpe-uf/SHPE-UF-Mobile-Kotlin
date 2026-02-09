package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.events

import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import android.text.Html
import com.shpeuf.shpe_uf_mobile_kotlin.R
import com.shpeuf.shpe_uf_mobile_kotlin.ui.theme.ThemeColors
import android.content.res.Configuration

@Composable
fun CreateEventsScreen(
    isDarkMode: Boolean,
    viewModel: CreateEventViewModel,
    onSuccess: () -> Unit,
    onCancel: () -> Unit,
    onBack: () -> Unit = {},
    initial: EventDraft = EventDraft()
) {

    var title by remember { mutableStateOf(initial.title) }
    var code by remember { mutableStateOf(initial.code) }
    var category by remember { mutableStateOf(initial.category) }
    var points by remember { mutableStateOf(initial.points) }
    var expiresIn by remember { mutableStateOf(initial.expiresIn) }

    val createEventState by viewModel.createEventState.collectAsState()
    val context = LocalContext.current

    // Handle state changes with custom toasts
    LaunchedEffect(createEventState) {
        when (val state = createEventState) {
            is CreateEventState.Success -> {
                showCustomToast(context, "Event <b>${title.trim()}</b> created successfully!", isError = false)
                viewModel.resetState()
                onSuccess()
            }
            is CreateEventState.Error -> {
                showCustomToast(context, "Error: ${state.message}", isError = true)
            }
            else -> { /* Idle or Loading */ }
        }
    }

    val textColor = if (isDarkMode) Color.White else Color.Black
    val containerColor = if (!isDarkMode) Color(0xFFD25917) else Color(0xFF001627)
    val bgSection = if (isDarkMode) Color(0xFF002139) else Color(0xFFF5F5F5)
    val isLoading = createEventState is CreateEventState.Loading

    val orangeHeight = 150.dp

    Box(modifier = Modifier.fillMaxSize()) {
        // Orange top
        Box(
            Modifier
                .fillMaxWidth()
                .height(orangeHeight)
                .background(Color(0xFFD25917))
        )

        // Header with Back Button + Title + Gator
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 28.dp)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 8.dp)
                    .offset(x = (-8).dp, y = (-10).dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            Text(
                text = "CREATE EVENTS",
                color = Color.White,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.viga)),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 24.dp)
            )

            Image(
                painter = painterResource(
                    id = if (isDarkMode) R.drawable.gator_dark_mode else R.drawable.gator_light_mode
                ),
                contentDescription = "SHPE GATOR",
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 0.dp)
                    .offset(y = 80.dp)
                    .width(90.dp)
                    .height(90.dp)
            )
        }

        Box(
            Modifier
                .fillMaxSize()
                .padding(top = orangeHeight)
                .background(if (isDarkMode) ThemeColors.Night.background else ThemeColors.Day.background)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = orangeHeight)
        ) {
            // Title
            item {
                FieldBlock(
                    bg = bgSection,
                    label = "TITLE",
                    icon = R.drawable.profile_circle_orange,
                    textColor = textColor
                ) {
                    EditTextField(
                        value = title,
                        onValueChange = { title = it },
                        isDarkMode = isDarkMode,
                        enabled = !isLoading
                    )
                }
            }

            // Code
            item {
                FieldBlock(
                    bg = bgSection,
                    label = "CODE",
                    icon = R.drawable.lock,
                    textColor = textColor
                ) {
                    EditTextField(
                        value = code,
                        onValueChange = { code = it },
                        isDarkMode = isDarkMode,
                        enabled = !isLoading
                    )
                }
            }

            // Category dropdown
            item {
                FieldBlock(
                    bg = bgSection,
                    label = "CATEGORY",
                    icon = R.drawable.category,
                    iconSize = 20.dp,
                    textColor = textColor
                ) {
                    DropdownField(
                        value = category,
                        onValueChange = { category = it },
                        isDarkMode = isDarkMode,
                        enabled = !isLoading,
                        options = listOf(
                            "General Body Meeting",
                            "Cabinet Meeting",
                            "Workshop",
                            "Form/Survey"
                        )
                    )
                }
            }

            // Points
            item {
                FieldBlock(
                    bg = bgSection,
                    label = "POINTS",
                    icon = R.drawable.points,
                    textColor = textColor
                ) {
                    EditTextField(
                        value = points,
                        onValueChange = { points = it.filter { ch -> ch.isDigit() } },
                        isDarkMode = isDarkMode,
                        enabled = !isLoading
                    )
                }
            }

            // Expires In dropdown
            item {
                FieldBlock(
                    bg = bgSection,
                    label = "EXPIRES IN",
                    icon = R.drawable.profile_year,
                    textColor = textColor
                ) {
                    DropdownField(
                        value = expiresIn,
                        onValueChange = { expiresIn = it },
                        isDarkMode = isDarkMode,
                        enabled = !isLoading,
                        options = listOf("1 hour", "2 hours", "3 hours", "4 hours")
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Buttons
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 36.dp, vertical = 28.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
                ) {
                    Button(
                        onClick = {
                            viewModel.createEvent(
                                EventDraft(
                                    title = title.trim(),
                                    code = code.trim(),
                                    category = category.trim(),
                                    points = points.trim(),
                                    expiresIn = expiresIn.trim()
                                )
                            )
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = Color.White),
                        modifier = Modifier.weight(1f),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(18.dp),
                                color = Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Save", fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.viga)))
                        }
                    }

                    Button(
                        onClick = onCancel,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = Color.White),
                        modifier = Modifier.weight(1f),
                        enabled = !isLoading
                    ) { Text("Cancel", fontSize = 18.sp, fontFamily = FontFamily(Font(R.font.viga))) }
                }
            }

            item { Spacer(modifier = Modifier.height(45.dp)) }
        }
    }
}

// Custom toast function matching QR scanner pattern
private fun showCustomToast(context: android.content.Context, message: String, isError: Boolean = false) {
    val layoutInflater = android.view.LayoutInflater.from(context)
    val view = layoutInflater.inflate(R.layout.custom_toast, null)

    val textView = view.findViewById<TextView>(R.id.toast_message)
    val boldedText = Html.fromHtml(message, Html.FROM_HTML_MODE_LEGACY)
    textView.text = boldedText

    val toast = Toast(context)
    // Error messages stay longer (LONG ~3.5 seconds), success messages shorter (SHORT ~2 seconds)
    toast.duration = if (isError) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
    toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)
    toast.view = view
    toast.show()
}

data class EventDraft(
    val title: String = "",
    val code: String = "",
    val category: String = "",
    val points: String = "",
    val expiresIn: String = ""
)

@Composable
private fun FieldBlock(
    bg: Color,
    label: String,
    icon: Int,
    textColor: Color,
    iconSize: Dp = 26.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(bg)
            .padding(top = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 40.dp, vertical = 4.dp)
        ) {
            Image(painter = painterResource(icon), contentDescription = null, modifier = Modifier.size(iconSize))
            Spacer(Modifier.width(10.dp))
            Text(label, color = Color(0xFFD25917), fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.viga)))
        }

        Column(modifier = Modifier.padding(horizontal = 40.dp)) {
            content()
        }

        Divider(color = Color.LightGray.copy(alpha = 0.3f), thickness = 1.dp, modifier = Modifier.fillMaxWidth())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditTextField(
    value: String,
    onValueChange: (String) -> Unit,
    isDarkMode: Boolean,
    enabled: Boolean = true
) {
    val underline = if (isDarkMode) Color.White else Color.Black
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(fontSize = 15.sp, color = if (isDarkMode) Color.White else Color.Black, fontFamily = FontFamily(Font(R.font.universltstd))),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = if (isDarkMode) Color.White else Color.Black,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedBorderColor = underline,
            unfocusedBorderColor = underline,
            cursorColor = if (isDarkMode) Color.White else Color.Black,
            disabledTextColor = if (isDarkMode) Color.White.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.5f),
            disabledBorderColor = underline.copy(alpha = 0.5f)
        )
    )
}

@Composable
private fun DropdownField(
    value: String,
    onValueChange: (String) -> Unit,
    isDarkMode: Boolean,
    enabled: Boolean = true,
    options: List<String>
) {
    var expanded by remember { mutableStateOf(false) }
    val iconTint = if (isDarkMode) Color.White else Color.Black

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = if (value.isBlank()) "Select…" else value,
            color = if (enabled) {
                if (isDarkMode) Color.White else Color.Black
            } else {
                if (isDarkMode) Color.White.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.5f)
            },
            fontSize = 15.sp,
            modifier = Modifier
                .weight(1f)
                .clickable(enabled = enabled) { expanded = true }
        )
        IconButton(onClick = { expanded = !expanded }, enabled = enabled) {
            Icon(
                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = if (enabled) iconTint else iconTint.copy(alpha = 0.5f)
            )
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { opt ->
                DropdownMenuItem(
                    text = { Text(opt) },
                    onClick = {
                        onValueChange(opt)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(
    name = "Events – Light",
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_6"
)
@Composable
private fun EventsCreateScreenPreview_Light() {
    // Preview placeholder
}

@Preview(
    name = "Events – Dark",
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_6",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun EventsCreateScreenPreview_Dark() {
    // Preview placeholder
}