package com.sudnu.plugin_base.plugin_apkimport android.app.Activityimport android.os.Bundleimport android.view.Viewinterface IPluginProxyActivity {    fun onCreate(activity:Activity,savedInstanceState: Bundle?)    fun onStart()    fun onRestart()    fun onResume()    fun onPause()    fun onStop()    fun onDestroy()    fun getRootView(): View}