package com.sudnu.androidplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.sudnu.plugin_base.plugin_view.IPluginView
import com.sudnu.plugin_base.plugin_view.PluginLoadAar

class PluginViewMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plugin_view_activity_main)
        PluginLoadAar.loadPluginApk(this)
        findViewById<Button>(R.id.load).setOnClickListener {
            PluginLoadAar.pluginClassLoader?.loadClass("com.sudnu.pluginveiw.R$"+"mipmap")
            var pluginView = PluginLoadAar.pluginClassLoader?.loadClass("com.sudnu.pluginveiw.MyPluginView")?.newInstance() as IPluginView
            findViewById<LinearLayout>(R.id.view).addView(pluginView.loadView(this))
        }
    }
}