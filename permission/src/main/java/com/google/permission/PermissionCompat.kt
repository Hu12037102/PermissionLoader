package com.google.permission

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat

object PermissionCompat {
    @JvmStatic
    fun hasPermission(context: Context, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED
    }

    @JvmStatic
    fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        var count = 0
        for (permission in permissions) {
            count = if (hasPermission(context, permission)) count + 1 else count
        }
        return count == permissions.size && count > 0
    }

    @JvmStatic
    fun isOpenNotificationPermission(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    @JvmStatic
    fun isOpenSystemWindowPermission(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Settings.canDrawOverlays(context)
        } else {
            true
        }
    }

}