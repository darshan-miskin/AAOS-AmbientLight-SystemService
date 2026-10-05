package com.android.server

import android.content.Context
import android.os.IVehicleAmbientLightCallback
import android.os.IVehicleAmbientLightService
import android.os.RemoteCallbackList
import android.os.RemoteException
import android.util.Slog
import java.io.FileDescriptor
import java.io.FileOutputStream
import java.io.PrintWriter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VehicleAmbientLightService(private val context: Context) : IVehicleAmbientLightService.Stub() {

    companion object {
        private const val TAG = "VehicleAmbientLightService"
    }

    private val callbackList = RemoteCallbackList<IVehicleAmbientLightCallback>()

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val _luxState = MutableStateFlow(0)
    val luxState = _luxState.asStateFlow()

    override fun getLux(): Int = _luxState.value

    override fun setLux(lux: Int) {
        Slog.d(TAG, "Updating ambient light intensity to: $lux lux")
        _luxState.value = lux

        serviceScope.launch {
            notifyCallbacks(lux)
        }
    }

    override fun registerCallback(callback: IVehicleAmbientLightCallback?) {
        if (callback != null) {
            callbackList.register(callback)
        }
    }

    override fun unregisterCallback(callback: IVehicleAmbientLightCallback?) {
        if (callback != null) {
            callbackList.unregister(callback)
        }
    }

    private fun notifyCallbacks(lux: Int) {
        val count = callbackList.beginBroadcast()
        for (i in 0 until count) {
            try {
                callbackList.getBroadcastItem(i).onLuxChanged(lux)
            } catch (e: RemoteException) {
                Slog.e(TAG, "Failed to dispatch callback to process index $i", e)
            }
        }
        callbackList.finishBroadcast()
    }

    override fun dump(fd: FileDescriptor, args: Array<out String>?) {
        val pw = PrintWriter(FileOutputStream(fd))
        try {
            pw.println("--- VehicleAmbientLightService Status ---")
            pw.println("Service Operational: true")
            pw.println("----------------------------------------")
        } finally {
            pw.flush()
        }
    }

}
