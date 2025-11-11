package com.example.shpe_uf_mobile_kotlin.ui.pages.events

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shpe_uf_mobile_kotlin.R
import com.example.shpe_uf_mobile_kotlin.data.SHPEUFAppViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.profile.ProfileViewModel
import com.example.shpe_uf_mobile_kotlin.ui.pages.profile.StaticProfileScreen
import com.example.shpe_uf_mobile_kotlin.ui.theme.ThemeColors

// for dark-mode preview constant
import android.content.res.Configuration

@Composable
fun CreateEventsScreen(
    isDarkMode: Boolean,
    onSave: (EventDraft) -> Unit,
    onCancel: () -> Unit,
    onBack: () -> Unit = {},
    // optionally pass initial values to "edit" existing. Prob won't need until we include edit
    // event functionality.
    initial: EventDraft = EventDraft() // Initially empty
) {

    var title by remember { mutableStateOf(initial.title) }
    var code by remember { mutableStateOf(initial.code) }
    var category by remember { mutableStateOf(initial.category) }
    var points by remember { mutableStateOf(initial.points) }
    var expiresIn by remember { mutableStateOf(initial.expiresIn) }

    val textColor = if (isDarkMode) Color.White else Color.Black
    val containerColor = if (!isDarkMode) Color(0xFFD25917) else Color(0xFF001627)
    val bgSection = if (isDarkMode) Color(0xFF002139) else Color(0xFFF5F5F5)

    // Header / background
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val orangeHeight = 150.dp

    Box(modifier = Modifier.fillMaxSize()) {
        // Orange top
        Box(
            Modifier
                .fillMaxWidth()
                .height(orangeHeight)
                .background(Color(0xFFD25917))
        )

        // Alligator + "Create Event"
        // Orange Header with Back Button + Title + Gator
        Box(
            Modifier
                .fillMaxWidth()
                .padding(top = 28.dp)
        ) {
            // Back button (top-left) — small padding to match AdminPanel
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

            // Centered title — keep same top padding as before so it remains vertically consistent
            Text(
                text = "CREATE EVENTS",
                color = Color.White,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.viga)),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 24.dp)
            )

            // Gator - positioned to overlap the orange and background
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
                        isDarkMode = isDarkMode
                    )
                }
            }

            // Code (email/code)
            item {
                FieldBlock(
                    bg = bgSection,
                    label = "CODE",
                    icon = R.drawable.profile_email,
                    textColor = textColor
                ) {
                    EditTextField(
                        value = code,
                        onValueChange = { code = it },
                        isDarkMode = isDarkMode
                    )
                }
            }

            // Category dropdown
            item {
                FieldBlock(
                    bg = bgSection,
                    label = "CATEGORY",
                    icon = R.drawable.profile_circle_orange,
                    textColor = textColor
                ) {
                    DropdownField(
                        value = category,
                        onValueChange = { category = it },
                        isDarkMode = isDarkMode,
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
                    icon = R.drawable.profile_year,
                    textColor = textColor
                ) {
                    EditTextField(
                        value = points,
                        onValueChange = { points = it.filter { ch -> ch.isDigit() } },
                        isDarkMode = isDarkMode
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
                        options = listOf("1 hour", "2 hours", "3 hours", "4 hours")
                    )
                }
            }

            // Add space between last field and buttons
            item {
                Spacer(modifier = Modifier.height(20.dp))
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
                            onSave(
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
                        modifier = Modifier.weight(1f)
                    ) { Text("Save", fontSize = 18.sp) }

                    Button(
                        onClick = onCancel,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = Color.White),
                        modifier = Modifier.weight(1f),
                    ) { Text("Cancel", fontSize = 18.sp) }
                }
            }

            item { Spacer(modifier = Modifier.height(45.dp)) }
        }
    }
}

// Simple value holder
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
            Image(painter = painterResource(icon), contentDescription = null, modifier = Modifier.size(26.dp))
            Spacer(Modifier.width(10.dp))
            Text(label, color = Color(0xFFD25917), fontSize = 20.sp, fontWeight = FontWeight.Bold)
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
    isDarkMode: Boolean
) {
    val underline = if (isDarkMode) Color.White else Color.Black
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(fontSize = 15.sp, color = if (isDarkMode) Color.White else Color.Black),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = if (isDarkMode) Color.White else Color.Black,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedBorderColor = underline,
            unfocusedBorderColor = underline,
            cursorColor = if (isDarkMode) Color.White else Color.Black
        )
    )
}

@Composable
private fun DropdownField(
    value: String,
    onValueChange: (String) -> Unit,
    isDarkMode: Boolean,
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
            color = if (isDarkMode) Color.White else Color.Black,
            fontSize = 15.sp,
            modifier = Modifier
                .weight(1f)
                .clickable { expanded = true }
        )
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = iconTint
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

// Preview Tests
/* They don't require a ViewModel or Nav. Later iterations prob should */

/** Light mode preview */
@Preview(
    name = "Events – Light",
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_6"
)
@Composable
private fun EventsCreateScreenPreview_Light() {
    CreateEventsScreen(
        isDarkMode = false,
        onSave = { /* blank for preview */ },
        onCancel = { /* blank for preview */ },
        initial = EventDraft(
            title = "Daniel Dovale",
            code = "ddovale2004@gmail.com",
            category = "Workshop",
            points = "10",
            expiresIn = "2 hours"
        )
    )
}

/** Dark mode preview */
@Preview(
    name = "Events – Dark",
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_6",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun EventsCreateScreenPreview_Dark() {
    CreateEventsScreen(
        isDarkMode = true,
        onSave = { /* blank for preview */ },
        onCancel = { /* blank for preview */ },
        initial = EventDraft(
            title = "Daniel Dovale",
            code = "ddovale2004@gmail.com",
            category = "General Body Meeting",
            points = "5",
            expiresIn = "1 hour"
        )
    )
}

/**
 * You can change fields live while the preview is running.
 * (Uses remember state so you can type in the preview.)
 */
@Preview(name = "Events – Editable Preview", showBackground = true, showSystemUi = true)
@Composable
private fun EventsCreateScreenPreview_Editable() {
    var draft by remember {
        mutableStateOf(
            EventDraft(
                title = "Daniel Dovale",
                code = "ddovale2004@gmail.com",
                category = "",
                points = "",
                expiresIn = ""
            )
        )
    }

    CreateEventsScreen(
        isDarkMode = false,
        onSave = { saved -> draft = saved }, // capture result in preview state (visible in debugger)
        onCancel = { /* blank */ },
        initial = draft
    )
}

