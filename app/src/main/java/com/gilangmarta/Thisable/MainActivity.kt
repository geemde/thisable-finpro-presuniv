package com.gilangmarta.Thisable

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gilangmarta.Thisable.databinding.ActivityMainBinding
import com.gilangmarta.Thisable.utils.ConstVal
import com.gilangmarta.Thisable.utils.showToast

class MainActivity : AppCompatActivity() {

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_activityMainBinding?.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                PendeteksiTeksFragment.REQUIRED_PERMISSIONS,
                ConstVal.REQUEST_CODE_PERMISSIONS
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!allPermissionsGranted()) {
            showToast(getString(R.string.message_not_permitted))
        }
    }

    private fun allPermissionsGranted() = PendeteksiTeksFragment.REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
    }

}