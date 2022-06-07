package com.google.permissionloader

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import com.google.permission.activity.PermissionActivity
import com.example.permissionloader.R
import com.google.permission.IntentCompat
import com.google.permission.OnPermissionResult
import com.google.permission.PermissionCompat
import com.google.permission.PermissionStatus

class MainActivity : PermissionActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btn_float_window).setOnClickListener {
            val hasFloatWindowPermission = PermissionCompat.isOpenSystemWindowPermission(this)
            if (hasFloatWindowPermission) {
                Toast.makeText(
                    applicationContext,
                    "悬浮窗权限已获取",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                IntentCompat.openSystemWindowPermissionCenter(this)
            }
        }
        findViewById<View>(R.id.btn_notification).setOnClickListener {
            val hasPermission = PermissionCompat.isOpenNotificationPermission(this@MainActivity)
            if (hasPermission) {
                Toast.makeText(
                    applicationContext,
                    "通知栏权限已获取",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                IntentCompat.openNotificationCenter(this)
            }
        }
        val atvPermissionList: AppCompatTextView = findViewById(R.id.atv_permission_list)
        val permissionList = Contract.REQUEST_PERMISSION_LIST
        permissionList.forEach {
            val lastText = if (Contract.REQUEST_PERMISSION_LIST.lastIndex == permissionList.indexOf(
                    it
                )
            ) {
                ""
            } else {
                "\n"
            }
            atvPermissionList.append(
                "${it}${lastText}"
            )
        }

        findViewById<View>(R.id.btn_request_1).setOnClickListener {
            requestPermission(
                Contract.REQUEST_PERMISSION_LIST, object : OnPermissionResult {
                    override fun onBackResult(
                        status: Int,
                        resultPermissions: List<String>
                    ) {
                        when (status) {
                            PermissionStatus.PERMISSION_STATUS_FORBID -> {
                                Toast.makeText(
                                    applicationContext,
                                    "权限被禁止${resultPermissions}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            PermissionStatus.PERMISSION_STATUS_REJECT -> {
                                Toast.makeText(
                                    applicationContext,
                                    "权限被拒绝${resultPermissions}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            PermissionStatus.PERMISSION_STATUS_SUCCEED -> {
                                Toast.makeText(
                                    applicationContext,
                                    "权限申请成功${resultPermissions}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                })


        }
        findViewById<View>(R.id.btn_request_2).setOnClickListener {
            requestPermissionXs(Contract.REQUEST_PERMISSION_LIST, object : OnPermissionResult {
                override fun onBackResult(status: Int, resultPermissions: List<String>) {
                    when (status) {
                        PermissionStatus.PERMISSION_STATUS_FORBID -> {
                            Toast.makeText(
                                applicationContext,
                                "权限被禁止${resultPermissions}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        PermissionStatus.PERMISSION_STATUS_REJECT -> {
                            Toast.makeText(
                                applicationContext,
                                "权限被拒绝${resultPermissions}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        PermissionStatus.PERMISSION_STATUS_SUCCEED -> {
                            Toast.makeText(
                                applicationContext,
                                "权限申请成功${resultPermissions}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        }
    }

}