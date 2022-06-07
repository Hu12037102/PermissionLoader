package com.google.permission

import androidx.annotation.IntDef

@IntDef(
    PermissionStatus.PERMISSION_STATUS_SUCCEED,
    PermissionStatus.PERMISSION_STATUS_REJECT,
    PermissionStatus.PERMISSION_STATUS_FORBID
)
@Retention(AnnotationRetention.SOURCE)
annotation class PermissionStatus {
    companion object {
        const val PERMISSION_STATUS_SUCCEED = 0
        const val PERMISSION_STATUS_REJECT = 1
        const val PERMISSION_STATUS_FORBID = 2
    }
}

interface OnPermissionResult {
    fun onBackResult(
        @PermissionStatus status: Int,
        resultPermissions: List<String>
    )
}