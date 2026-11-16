package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.admin.events

import androidx.compose.foundation.clickable
import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.asImageBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.window.DialogProperties

// Separate from the Google Calendar Event entity used elsewhere in the app.
data class AttendanceUser(val id: String, val name: String)

@Composable
fun ManualInputDialog(
    users: List<AttendanceUser>,
    onDismiss: () -> Unit,
    onAddUser: (AttendanceUser) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedUser by remember { mutableStateOf<AttendanceUser?>(null) }
    val matchingUsers = users.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "MANUAL INPUT",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFFD25917)
                )
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        selectedUser = null
                    },
                    label = { Text("Search or select user") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                LazyColumn(modifier = Modifier.heightIn(max = 240.dp)) {
                    if (matchingUsers.isEmpty()) {
                        item { Text("No users found", modifier = Modifier.padding(12.dp)) }
                    }
                    items(matchingUsers, key = { it.id }) { user ->
                        Text(
                            text = user.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedUser = user
                                    searchQuery = user.name
                                }
                                .padding(12.dp),
                            color = if (selectedUser?.id == user.id)
                                MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(onClick = onDismiss) { Text("Cancel") }
                    Button(
                        onClick = { selectedUser?.let(onAddUser) },
                        enabled = selectedUser != null,
                        modifier = Modifier.padding(start = 8.dp)
                    ) {
                        Text("Add User")
                    }
                }
            }
        }
    }
}

// Temporary test UI. Later, your teammate's table can open ManualInputDialog.
@Preview(showBackground = true)
@Composable
private fun ManualInputDialogPreview() {
    val sampleUsers = listOf(
        AttendanceUser("1", "Carolina Abad"),
        AttendanceUser("2", "Katheryn Abad"),
        AttendanceUser("3", "Javier Abadia"),
        AttendanceUser("4", "Elena Abraham"),
        AttendanceUser("5", "Jackson Abrams"),
        AttendanceUser("6", "Ruben Abreu"),
        AttendanceUser("7", "Cassandra Acebal"),
        AttendanceUser("8", "Fabian Acevedo")
    )
    MaterialTheme {
        ManualInputDialog(
            users = sampleUsers,
            onDismiss = {},
            onAddUser = {}
        )
    }
}


// Data used by the admin attendance popup. The table can map GraphQL users into this type.
data class EventAttendee(
    val id: String,
    val name: String,
    val username: String,
    val email: String
)

@Composable
fun EventInfoDialog(
    eventName: String,
    eventCode: String,
    attendees: List<EventAttendee>,
    onDismiss: () -> Unit,
    onRemoveAttendee: (EventAttendee) -> Unit,
    onDownloadCsv: () -> Unit
) {
    var pendingRemoval by remember { mutableStateOf<EventAttendee?>(null) }

    Dialog(onDismissRequest = onDismiss, properties = DialogProperties (usePlatformDefaultWidth = false)
        ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .widthIn(max = 680.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier
                    .heightIn(max = 620.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "EVENT INFORMATION",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFD25917),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, contentDescription = "Close event information")
                    }
                }
                Text("Code: $eventCode")
                Text("QR Code", style = MaterialTheme.typography.titleSmall)
                EventCodeQr(code = eventCode)
                Text(
                    eventName.uppercase(),
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFFD25917)
                )
                Text("Attendance: ${attendees.size}")
                HorizontalDivider()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    // Fixed column widths prevent text from being squeezed.
                    Row(
                        modifier = Modifier.width(620.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("NAME", modifier = Modifier.width(160.dp),
                            style = MaterialTheme.typography.labelSmall)
                        Text("USERNAME", modifier = Modifier.width(140.dp),
                            style = MaterialTheme.typography.labelSmall)
                        Text("EMAIL", modifier = Modifier.width(250.dp),
                            style = MaterialTheme.typography.labelSmall)
                        Text("REMOVE",
                            style = MaterialTheme.typography.labelSmall)
                    }

                    if (attendees.isEmpty()) {
                        Text(
                            "No attendees yet",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    attendees.forEach { attendee ->
                        HorizontalDivider()
                        Row(
                            modifier = Modifier.width(620.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                attendee.name,
                                modifier = Modifier.width(160.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                attendee.username,
                                modifier = Modifier.width(140.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                attendee.email,
                                modifier = Modifier.width(250.dp),
                                style = MaterialTheme.typography.bodySmall
                            )
                            IconButton(
                                onClick = { pendingRemoval = attendee }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Remove ${attendee.name}",
                                    tint = Color(0xFFD32F2F)
                                )
                            }
                        }
                    }
                }

                Button(onClick = onDownloadCsv) {
                    Text("Download as CSV")
                }
            }
        }
    }

    pendingRemoval?.let { attendee ->
        AlertDialog(
            onDismissRequest = { pendingRemoval = null },
            title = { Text("Remove attendee?") },
            text = { Text("Remove ${attendee.name} from $eventName?") },
            confirmButton = {
                TextButton(onClick = {
                    onRemoveAttendee(attendee)
                    pendingRemoval = null
                }) { Text("Remove") }
            },
            dismissButton = {
                TextButton(onClick = { pendingRemoval = null }) { Text("Cancel") }
            }
        )
    }
}

@Composable
private fun EventCodeQr(code: String) {
    val qrBitmap = remember(code) {
        try {
            val matrix = QRCodeWriter().encode(code, BarcodeFormat.QR_CODE, 240, 240)
            Bitmap.createBitmap(240, 240, Bitmap.Config.ARGB_8888).apply {
                for (y in 0 until 240) {
                    for (x in 0 until 240) {
                        setPixel(x, y, if (matrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                    }
                }
            }.asImageBitmap()
        } catch (_: Exception) {
            null
        }
    }
    if (qrBitmap != null) {
        Image(
            bitmap = qrBitmap,
            contentDescription = "QR code for event $code",
            modifier = Modifier.size(150.dp)
        )
    } else {
        Text("QR code unavailable")
    }
}
