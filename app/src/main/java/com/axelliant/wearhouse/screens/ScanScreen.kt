package com.axelliant.wearhouse.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.axelliant.wearhouse.R
import com.axelliant.wearhouse.component.CustomButton
import com.axelliant.wearhouse.component.MediumTextView
import com.axelliant.wearhouse.component.SmallTextView
import com.axelliant.wearhouse.ui.theme.AppColor
import com.axelliant.wearhouse.ui.theme.WhiteColor
import com.axelliant.wearhouse.ui.theme.dimens
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@kotlin.OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanScreen() {
    val context = LocalContext.current
    var scanningActive by remember { mutableStateOf(true) }
    var scannedResult by remember { mutableStateOf("") } // Holds the scanned result
    var showDialog by remember { mutableStateOf(false) } // Controls dialog visibility

    if (showDialog) {
        // Display the dialog with scanned result
        ScannedResultDialog(
            scannedResult = scannedResult,
            onDismiss = {
                showDialog = false
                scanningActive = true // Restart scanning when dialog is dismissed
            },
            onAddToCard = {
                showDialog = false
                scanningActive = true // Restart scanning when dialog is dismissed
            }

        )
    }

    // Only show the camera preview if scanning is active
    if (scanningActive) {
        val cameraPermissionState = rememberPermissionState(
            Manifest.permission.CAMERA
        )
        if (cameraPermissionState.hasPermission) {
            CameraPreviewView(onBarcodeDetected = { result ->
                scannedResult = result
                scanningActive = false
                showDialog = true // Show the dialog when a barcode is detected
            })
        } else if (!cameraPermissionState.shouldShowRationale && !cameraPermissionState.hasPermission) {

            Box(
                modifier = Modifier
                    .fillMaxSize(), // Ensures the item takes the full height of the LazyColumn

                contentAlignment = Alignment.Center // Centers the content inside the Box
            ) {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    SmallTextView(
                        textString = stringResource(id = R.string.camera_permission_required))


                    Box(modifier = Modifier.width(MaterialTheme.dimens.d100)){
                        CustomButton(
                            modifier = Modifier.padding(top = MaterialTheme.dimens.d20),
                            onClick = {
                                val intent = Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.fromParts("package", context.packageName, null)
                                )
                                context.startActivity(intent)
                            },
                            text = stringResource(R.string.allow),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppColor,
                                contentColor = WhiteColor
                            ),
                            shape = RoundedCornerShape(MaterialTheme.dimens.d20) // Custom shape
                        )
                    }

                }

            }

        }

    }
}

@Composable
fun ScannedResultDialog(scannedResult: String, onDismiss: () -> Unit, onAddToCard: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Scanned Result") },
        text = { Text(text = scannedResult) },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "OUT bound")
            }
        },
        confirmButton = {
            Button(onClick = onAddToCard) {
                Text(text = "IN bound")
            }
        }
    )
}

@Composable
fun CameraPreviewView(onBarcodeDetected: (String) -> Unit) {
    AndroidView(
        factory = { context ->
            val previewView = PreviewView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }

            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                val imageAnalyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also { analysis ->
                        analysis.setAnalyzer(
                            ContextCompat.getMainExecutor(context)
                        ) { imageProxy ->
                            processImageProxy(imageProxy) { result ->
                                onBarcodeDetected(result) // Pass scanned result to the dialog
                            }
                        }
                    }

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        context as LifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalyzer
                    )
                } catch (exc: Exception) {
                    Log.e("CameraPreview", "Use case binding failed", exc)
                }
            }, ContextCompat.getMainExecutor(context))

            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalGetImage::class)
fun processImageProxy(
    imageProxy: ImageProxy,
    onBarcodeDetected: (String) -> Unit
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val barcodeScanner = BarcodeScanning.getClient()

        barcodeScanner.process(image)
            .addOnSuccessListener { barcodes ->
                if (barcodes.isNotEmpty()) {
                    for (barcode in barcodes) {
                        val rawValue = barcode.rawValue
                        Log.d("BarcodeScanner", "Scanned value: $rawValue")

                        // Pass the scanned result to the callback
                        onBarcodeDetected(rawValue ?: "")
                        break
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("BarcodeScanner", "Barcode scanning failed", e)
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } else {
        imageProxy.close()
    }
}

