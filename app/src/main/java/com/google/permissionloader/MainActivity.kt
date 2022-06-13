package com.google.permissionloader

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.FileProvider
import com.example.permissionloader.R
import com.google.permission.IntentCompat
import com.google.permission.OnPermissionResult
import com.google.permission.PermissionCompat
import com.google.permission.PermissionStatus
import com.google.permission.activity.PermissionActivity
import java.io.File

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
        findViewById<View>(R.id.btn_test).setOnClickListener {
            if(!PermissionCompat.hasPermission(this@MainActivity,Manifest.permission.CAMERA)){
                Toast.makeText(this.applicationContext, "请打开相机权限", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var file = this.getExternalFilesDir("Camera")
            file = File(file, "${System.currentTimeMillis()}${(Math.random() * 1000).toInt()}.jpg")
            val uri: Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val authority = "com.google.permissionloader.provider"
                uri = FileProvider.getUriForFile(this, authority, file)
                /*val resolveInfoData: List<ResolveInfo> = this.packageManager
                    .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
                if (resolveInfoData.isNotEmpty()) for (resolveInfo in resolveInfoData) {
                    val packageName = resolveInfo.activityInfo.packageName
                   grantUriPermission(
                        packageName,
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }*/
            } else {
                uri = Uri.fromFile(file)
            }
            testRegister.launch(uri)
          //  testRegister.launch(null)
        }
    }

    private val testRegister = registerForActivityResult(ActivityResultContracts.TakePicture()) {

        Log.w("TakePicture--", "拍照成功：$it")
    }
}