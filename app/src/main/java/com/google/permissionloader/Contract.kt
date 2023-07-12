package com.google.permissionloader

import android.Manifest

object Contract {
    val REQUEST_PERMISSION_LIST = arrayOf(
       /* Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE*/
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
}