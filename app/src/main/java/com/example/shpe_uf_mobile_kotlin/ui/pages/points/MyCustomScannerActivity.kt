package com.example.shpe_uf_mobile_kotlin.ui.customscanner

import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.shpe_uf_mobile_kotlin.R
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.DecoratedBarcodeView

/**
 * A custom Activity that extends JourneyApps' CaptureActivity,
 * which allows us to override its layout and add UI elements.
 */
class MyCustomScannerActivity : CaptureActivity() {
    private lateinit var barcodeView: DecoratedBarcodeView

    private fun showCustomToast(message: String) {
        val layoutInflater = layoutInflater
        val view = layoutInflater.inflate(R.layout.custom_toast, null)

        // Update the text
        val textView = view.findViewById<TextView>(R.id.toast_message)
        textView.text = message

        // Change the icon dynamically (Optional)
        val imageView = view.findViewById<ImageView>(R.id.toast_icon)
        imageView.setImageResource(R.drawable.shpe_logo_full_color) // Replace with your logo

        // Create and show the toast
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = view
        toast.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_scanner_layout)

        barcodeView = findViewById(R.id.zxing_barcode_scanner)
        barcodeView.initializeFromIntent(intent)

        barcodeView.setStatusText("")

        barcodeView.decodeContinuous { result ->
            // Show success toast
            showCustomToast("QR Code scanned: ${result.text}")

            // Return scanned text to calling Activity
            val data = Intent().apply {
                putExtra("SCAN_RESULT", result.text)
            }
            setResult(RESULT_OK, data)
            finish()
        }


        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            showCustomToast("Scan canceled")
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        barcodeView.resume()
    }

    override fun onPause() {
        barcodeView.pause()
        super.onPause()
    }
}
