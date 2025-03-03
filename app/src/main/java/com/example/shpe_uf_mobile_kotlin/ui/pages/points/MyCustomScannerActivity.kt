package com.example.shpe_uf_mobile_kotlin.ui.customscanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageFormat
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.util.Size
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.example.shpe_uf_mobile_kotlin.R
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.camera.core.ExperimentalGetImage

@OptIn(ExperimentalGetImage::class)
class MyCustomScannerActivity : AppCompatActivity() {

    private lateinit var cameraProviderFuture: ProcessCameraProvider
    private lateinit var previewView: PreviewView
    private var camera: Camera? = null
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_scanner_layout)

        // Setup Executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        previewView = findViewById(R.id.cameraPreview)

        val backButton: ImageButton = findViewById(R.id.backButton)
        backButton.setOnClickListener {
            showCustomToast("Scan Canceled")
            finish()
        }

        // Zoom SeekBar
        val zoomSeekBar: SeekBar = findViewById(R.id.zoomSeekBar)
        zoomSeekBar.progress = 0
        zoomSeekBar.max = 10
        zoomSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val zoomRatio = 1.0f + (progress * 0.4f)
                camera?.cameraControl?.setZoomRatio(zoomRatio)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Start the camera after we have permission
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    startCamera()
                } else {
                    showCustomToast("Camera permission denied")
                    finish()
                }
            }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            bindCameraUseCases(cameraProvider)
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindCameraUseCases(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .setTargetResolution(Size(1280, 720))
            .build()
            .also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

        // Set up ML Kit analysis
        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(cameraExecutor) { imageProxy ->
            processImageProxy(imageProxy)
        }

        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            cameraProvider.unbindAll()
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageAnalysis
            )
        } catch (exc: Exception) {
            Log.e("CameraX", "Use case binding failed", exc)
        }
    }

    private var scanningActive = true

    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy) {
        if (!scanningActive) {
            // Already handled a scan, just close and return
            imageProxy.close()
            return
        }

        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }
        val rotationDegrees = imageProxy.imageInfo.rotationDegrees
        val image = InputImage.fromMediaImage(mediaImage, rotationDegrees)

        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val scanner = BarcodeScanning.getClient(options)

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    // Make sure we only process the first recognized scan
                    scanningActive = false

                    val barcode = barcodes[0]
                    val scannedText = barcode.rawValue ?: ""
                    showCustomToast("Code: <b>$scannedText<b>, was succesfully scanned")

                    val data = Intent().apply {
                        putExtra("SCAN_RESULT", scannedText)
                    }
                    setResult(RESULT_OK, data)
                    finish()
                }
            }
            .addOnFailureListener {
                // Not recognized, still scanning, do NOT set scanningActive=false
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun showCustomToast(message: String) {
        val layoutInflater = layoutInflater
        val view = layoutInflater.inflate(R.layout.custom_toast, null)

        // Bolding the code portion in HTML
        val textView = view.findViewById<TextView>(R.id.toast_message)
        val boldedText = Html.fromHtml(message)
        textView.text = boldedText

        // Create & configure the Toast
        val toast = Toast(applicationContext)
        toast.duration = Toast.LENGTH_SHORT

        // Position it at the top of the screen
        toast.setGravity(android.view.Gravity.TOP or android.view.Gravity.CENTER_HORIZONTAL, 0, 100)

        toast.view = view
        toast.show()
    }

}
