package com.google.permission

import android.content.ClipData.newIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity


object IntentCompat {
    @JvmStatic
    fun openSystemWindowPermissionCenter(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                context.startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + context.packageName)
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    @JvmStatic
    fun openNotificationCenter(context: Context) {
        try {
            val intent = Intent()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                intent.data = Uri.fromParts("package", context.packageName, null)
            } else {
                intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
                intent.putExtra("app_package", context.packageName)
                intent.putExtra("app_uid", context.applicationInfo.uid)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @JvmStatic
    fun openApplicationSetting(context: Context?) {
        if (context == null) {
            return
        }
        try {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", context.packageName, null)
            )
            ActivityCompat.startActivity(context, intent, null)
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_SETTINGS)
                ActivityCompat.startActivity(context, intent, null)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}