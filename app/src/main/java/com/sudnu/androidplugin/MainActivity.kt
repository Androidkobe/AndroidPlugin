package com.sudnu.androidplugin

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sudnu.layout_dsl.dsl.*
import com.sudnu.plugin_base.plugin_apk.HostActivity
import com.sudnu.plugin_base.plugin_apk.PluginLoader


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myRequetPermission()
        requestmanageexternalstorage_Permission()
        findViewById<Button>(R.id.load).setOnClickListener{
            PluginLoader.loadPluginApk(this)
        }
        findViewById<Button>(R.id.jumpPlugin).setOnClickListener {
            var intent =Intent(this, HostActivity::class.java)
            intent.putExtra("pluginName","com.sudnu.plugin.PluginActivity")
            startActivity(intent)
        }

        findViewById<Button>(R.id.loadViewPlugin).setOnClickListener {
            var intent =Intent(this,PluginViewMainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun myRequetPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.VIBRATE
                ),
                1
            )
        } else {
            Toast.makeText(this, "您已经申请了权限!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun requestmanageexternalstorage_Permission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 先判断有没有权限
            if (Environment.isExternalStorageManager()) {
                Toast.makeText(
                    this,
                    "Android VERSION  R OR ABOVE，HAVE MANAGE_EXTERNAL_STORAGE GRANTED!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Android VERSION  R OR ABOVE，NO MANAGE_EXTERNAL_STORAGE GRANTED!",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:" + this.packageName)
                startActivityForResult(intent, 100)
            }
        }
    }
}