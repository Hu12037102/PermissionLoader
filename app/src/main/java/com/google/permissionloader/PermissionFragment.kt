package com.google.permissionloader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.permission.fragment.PermissionFragment
import com.example.permissionloader.R
import com.google.permission.OnPermissionResult
import com.google.permission.PermissionStatus

class PermissionFragment: PermissionFragment() {
    private var mRootView:View?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView= LayoutInflater.from(context).inflate(R.layout.fragment_permission,container,false)
        mRootView?.findViewById<View>(R.id.btn_request_1)?.setOnClickListener {
            requestPermission(Contract.REQUEST_PERMISSION_LIST, object : OnPermissionResult {
                    override fun onBackResult(
                        status: Int,
                        resultPermissions: List<String>
                    ) {
                        when (status) {
                            PermissionStatus.PERMISSION_STATUS_FORBID -> {
                                Toast.makeText(
                                    context,
                                    "权限被禁止${resultPermissions}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            PermissionStatus.PERMISSION_STATUS_REJECT -> {
                                Toast.makeText(
                                    context,
                                    "权限被拒绝${resultPermissions}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            PermissionStatus.PERMISSION_STATUS_SUCCEED -> {
                                Toast.makeText(
                                    context,
                                    "权限申请成功${resultPermissions}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                })
        }

        mRootView?.findViewById<View>(R.id.btn_request_2)?.setOnClickListener {
            requestPermissionXs(Contract.REQUEST_PERMISSION_LIST, object : OnPermissionResult {
                override fun onBackResult(status: Int, resultPermissions: List<String>) {
                    when (status) {
                        PermissionStatus.PERMISSION_STATUS_FORBID -> {
                            Toast.makeText(
                                context,
                                "权限被禁止${resultPermissions}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        PermissionStatus.PERMISSION_STATUS_REJECT -> {
                            Toast.makeText(
                                context,
                                "权限被拒绝${resultPermissions}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        PermissionStatus.PERMISSION_STATUS_SUCCEED -> {
                            Toast.makeText(
                                context,
                                "权限申请成功${resultPermissions}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            })
        }

        return mRootView
    }
}