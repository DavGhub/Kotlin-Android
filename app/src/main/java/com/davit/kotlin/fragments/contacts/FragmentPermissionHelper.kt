package com.davit.kotlin.fragments.contacts

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

class FragmentPermissionHelper {

    lateinit var requestPermissionLauncher:ActivityResultLauncher<String>


    fun startPermissionRequest(fragment: Fragment, permissionInterface: FragmentPermissionInterface){
        requestPermissionLauncher =
            fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                permissionInterface.onGranted(isGranted)
            }
    }

    fun lunchPermission(permission:String){
        requestPermissionLauncher.launch(permission)
    }
}