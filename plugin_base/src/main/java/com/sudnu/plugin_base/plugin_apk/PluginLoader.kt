package com.sudnu.plugin_base.plugin_apk

import android.content.Context
import android.content.res.AssetManager
import android.content.res.Resources
import android.os.Environment
import android.util.Log
import dalvik.system.DexClassLoader
import java.io.File
import java.lang.reflect.Method


object PluginLoader {

    var pluginResources: Resources? = null

    var pluginClassLoader: DexClassLoader? = null

    fun loadPluginApk(context: Context) {
        var plugin = Environment.getExternalStorageDirectory().absolutePath+ File.separator+"plugin/plugin-debug.apk"
        var dexPath = Environment.getExternalStorageDirectory().absolutePath+ File.separator+"plugin/dex"
        var dexFile = File(dexPath)
        if (!dexFile.exists()) {
            dexFile.mkdirs()
        }
        var pluginFile = File(plugin)
        pluginClassLoader = DexClassLoader(pluginFile.absolutePath, dexFile.absolutePath, null, this.javaClass.classLoader)
        Log.e("sundu","load plugin success  ${pluginFile.exists()}")
        pluginResources = loadPluginResource(context,plugin)

    }

    /**
     * 获取对应插件的Resource对象
     * @param context 宿主apk的上下文
     * @param pluginPath 插件apk的路径，带apk名
     * @return
     */
    fun loadPluginResource(context: Context, pluginPath: String?): Resources? {
        try {
            val assetManager = AssetManager::class.java.newInstance()
            // 反射调用方法addAssetPath(String path)
            val addAssetPath: Method =
                assetManager.javaClass.getMethod("addAssetPath", String::class.java)
            // 将插件Apk文件添加进AssetManager
            addAssetPath.invoke(assetManager, pluginPath)
            // 获取宿主apk的Resources对象
            val superRes: Resources = context.getResources()
            // 获取插件apk的Resources对象
            return Resources(
                assetManager,
                superRes.getDisplayMetrics(),
                superRes.getConfiguration()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}