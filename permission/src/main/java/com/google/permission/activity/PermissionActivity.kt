package com.google.permission.activity

import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.permission.OnPermissionResult
import com.google.permission.PermissionCompat
import com.google.permission.PermissionStatus

open class PermissionActivity : AppCompatActivity() {
    private var mPermission: String = ""
    private var mPermissionArray: Array<out String>? = null
    private var mOnPermissionResult: OnPermissionResult? = null
    private val mLauncherPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            val list = arrayListOf(mPermission)
            if (it) {
                mOnPermissionResult?.onBackResult(PermissionStatus.PERMISSION_STATUS_SUCCEED, list)
            } else {
                val hasCanRequest = ActivityCompat.shouldShowRequestPermissionRationale(
                    this@PermissionActivity,
                    mPermission
                )
                if (hasCanRequest) {
                    mOnPermissionResult?.onBackResult(
                        PermissionStatus.PERMISSION_STATUS_REJECT,
                        list
                    )
                } else {
                    mOnPermissionResult?.onBackResult(
                        PermissionStatus.PERMISSION_STATUS_FORBID,
                        list
                    )
                }
            }

        }

    private val mLauncherPermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val succeedList = arrayListOf<String>()
            val allRejectList = arrayListOf<String>()
            for ((key, value) in it) {
                if (value) {
                    succeedList.add(key)
                } else {
                    allRejectList.add(key)
                }
            }
            if (allRejectList.size > 0) {
                val rejectList = arrayListOf<String>()
                val forbidList = arrayListOf<String>()
                for (permission in allRejectList) {
                    val hasCanRequest = ActivityCompat.shouldShowRequestPermissionRationale(
                        this@PermissionActivity,
                        permission
                    )
                    if (hasCanRequest) {
                        rejectList.add(permission)
                    } else {
                        forbidList.add(permission)
                    }
                }
                if (forbidList.size > 0) {
                    mOnPermissionResult?.onBackResult(
                        PermissionStatus.PERMISSION_STATUS_FORBID,
                        forbidList
                    )
                } else {
                    mOnPermissionResult?.onBackResult(
                        PermissionStatus.PERMISSION_STATUS_REJECT,
                        rejectList
                    )
                }
            } else {
                mOnPermissionResult?.onBackResult(
                    PermissionStatus.PERMISSION_STATUS_SUCCEED,
                    succeedList
                )
            }

        }

    companion object {
        const val REQUEST_PERMISSION_CODE = 100

    }


    fun requestPermission(
        permissions: Array<out String>,
        onPermissionResult: OnPermissionResult
    ) {
        val succeedPermissionList = ArrayList<String>()
        val rejectPermissionList = ArrayList<String>()
        this.mOnPermissionResult = onPermissionResult
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                val hasPermission = PermissionCompat.hasPermission(this, permission)
                if (hasPermission) {
                    succeedPermissionList.add(permission)
                } else {
                    rejectPermissionList.add(permission)
                }
            }
            if (rejectPermissionList.size > 0) {
                ActivityCompat.requestPermissions(
                    this,
                    rejectPermissionList.toTypedArray(),
                    REQUEST_PERMISSION_CODE
                )
            } else {
                mOnPermissionResult?.onBackResult(
                    PermissionStatus.PERMISSION_STATUS_SUCCEED,
                    succeedPermissionList
                )
            }

        } else {
            succeedPermissionList.addAll(permissions.toList())
            mOnPermissionResult?.onBackResult(
                PermissionStatus.PERMISSION_STATUS_SUCCEED,
                succeedPermissionList
            )
        }
    }


    fun requestPermissionX(permission: String, onPermissionResult: OnPermissionResult) {
        try {
            this.mPermission = permission
            this.mOnPermissionResult = onPermissionResult
            mLauncherPermission.launch(permission)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun requestPermissionXs(
        permissions: Array<out String>,
        onPermissionResult: OnPermissionResult
    ) {
        try {
            mPermissionArray = permissions
            this.mOnPermissionResult = onPermissionResult
            mLauncherPermissions.launch(mPermissionArray)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_CODE) {
            val succeedPermissionList = ArrayList<String>()
            val rejectPermissionList = ArrayList<String>()
            val forbidPermissionList = ArrayList<String>()
            for (permission in permissions) {
                val hasPermission = PermissionCompat.hasPermission(this, permission)
                if (hasPermission) {
                    succeedPermissionList.add(permission)
                } else {
                    val hasCanRequestPermission =
                        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                    if (hasCanRequestPermission) {
                        rejectPermissionList.add(permission)
                    } else {
                        forbidPermissionList.add(permission)
                    }
                }
            }
            if (forbidPermissionList.size > 0) {
                mOnPermissionResult?.onBackResult(
                    PermissionStatus.PERMISSION_STATUS_FORBID,
                    forbidPermissionList
                )
            } else if (rejectPermissionList.size > 0) {
                mOnPermissionResult?.onBackResult(
                    PermissionStatus.PERMISSION_STATUS_REJECT,
                    rejectPermissionList
                )
            } else {
                mOnPermissionResult?.onBackResult(
                    PermissionStatus.PERMISSION_STATUS_SUCCEED,
                    succeedPermissionList
                )
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        try {
            mLauncherPermission.unregister()
            mLauncherPermissions.unregister()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}