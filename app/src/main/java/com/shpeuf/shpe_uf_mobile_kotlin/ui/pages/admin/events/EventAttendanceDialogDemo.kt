package com.shpeuf.shpe_uf_mobile_kotlin.ui.pages.admin.events

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Mock data only. No server requests or attendance mutations happen here.
private val demoUsers = listOf(
    AttendanceUser("1", "Carolina Abad"),
    AttendanceUser("2", "Katheryn Abad"),
    AttendanceUser("3", "Javier Abadia"),
    AttendanceUser("4", "Elena Abraham"),
    AttendanceUser("5", "Jackson Abrams"),
    AttendanceUser("6", "Ruben Abreu"),
    AttendanceUser("7", "Cassandra Acebal"),
    AttendanceUser("8", "Fabian Acevedo")
)

@Composable
fun EventAttendanceDialogDemo() {
    val context = LocalContext.current
    var showManualInput by remember { mutableStateOf(false) }
    var showEventInfo by remember { mutableStateOf(false) }
    var attendees by remember { mutableStateOf<List<EventAttendee>>(emptyList()) }
    val eventName = "GBM 8 Volunteers"
    val eventCode = "gbm8volunteers"
    var message by remember { mutableStateOf("No one added yet") }
    val saveCsv = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("text/csv")
    ) { uri ->
        if (uri != null) {
            try {
                val header = "Name,Username,Email"
                fun csvCell(value: String) = "\"" + value.replace("\"", "\"\"") + "\""
                val csvText = buildString {
                    appendLine(header)
                    attendees.forEach { attendee ->
                        appendLine(listOf(attendee.name, attendee.username, attendee.email)
                            .joinToString(",") { csvCell(it) })
                    }
                }
                context.contentResolver.openOutputStream(uri)?.use {
                    it.write(csvText.toByteArray(Charsets.UTF_8))
                } ?: error("Could not open selected file")
                message = "CSV saved"
            } catch (e: Exception) {
                message = "Could not save CSV: ${e.message}"
            }
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Event Attendance — Demo", style = MaterialTheme.typography.headlineSmall)
            Text("Temporary UI test. Does not update real attendance.")
            Button(onClick = { showManualInput = true }) {
                Text("Manual Input")
            }
            Button(onClick = { showEventInfo = true }) {
                Text("Event Information")
            }
            Text(message)
            Text("Mock attendance (${attendees.size}):", style = MaterialTheme.typography.titleMedium)
            if (attendees.isEmpty()) {
                Text("No attendees")
            } else {
                attendees.forEach { attendee ->
                    Text("• ${attendee.name}")
                }
            }
            Spacer(Modifier.height(8.dp))
            OutlinedButton(onClick = {
                attendees = emptyList()
                message = "Demo attendance cleared"
            }) {
                Text("Reset demo")
            }
        }

        if (showEventInfo) {
            EventInfoDialog(
                eventName = eventName,
                eventCode = eventCode,
                attendees = attendees,
                onDismiss = { showEventInfo = false },
                onRemoveAttendee = { attendee ->
                    attendees = attendees.filterNot { it.id == attendee.id }
                    message = "Removed ${attendee.name} from demo attendance"
                },
                onDownloadCsv = { saveCsv.launch("${eventCode}_attendance.csv") }
            )
        }

        if (showManualInput) {
            ManualInputDialog(
                users = demoUsers.filterNot { candidate ->
                    attendees.any { it.id == candidate.id }
                },
                onDismiss = {
                    showManualInput = false
                    message = "Cancelled — nobody added"
                },
                onAddUser = { selected ->
                    if (attendees.none { it.id == selected.id }) {
                        attendees = attendees + EventAttendee(
                            id = selected.id,
                            name = selected.name,
                            username = selected.name.lowercase().replace(" ", "."),
                            email = "user${selected.id}@example.org"
                        )
                        message = "Added ${selected.name} to demo attendance"
                    }
                    showManualInput = false
                }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 393, heightDp = 700)
@Composable
private fun EventAttendanceDialogDemoPreview() {
    MaterialTheme {
        EventAttendanceDialogDemo()
    }
}
