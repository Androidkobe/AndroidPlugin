package com.sudnu.plugin_base.plugin_apk

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleEventObserver

class HostActivity : AppCompatActivity() {

    val TAG = "sundu"

    //lifecycle observer 弃用
    private var lifecycleObserver: LifecycleEventObserver =
        LifecycleEventObserver { source, event ->
            Log.e(TAG, event.targetState.name)
        }

     var pluginActivity : IPluginProxyActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(lifecycleObserver)
        var className = intent.getStringExtra("pluginName")
        pluginActivity = PluginLoader.pluginClassLoader?.loadClass(className)?.newInstance() as IPluginProxyActivity
        pluginActivity?.onCreate(this,savedInstanceState)
        pluginActivity?.let { it ->
            setContentView(it.getRootView())
        }
    }

    override fun onStart() {
        super.onStart()
        pluginActivity?.onStart()
    }

    override fun onResume() {
        super.onResume()
        pluginActivity?.onResume()
    }

    override fun onRestart() {
        super.onRestart()
        pluginActivity?.onRestart()
    }

    override fun onPause() {
        super.onPause()
        pluginActivity?.onPause()
    }

    override fun onStop() {
        super.onStop()
        pluginActivity?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        pluginActivity?.onDestroy()
    }

}