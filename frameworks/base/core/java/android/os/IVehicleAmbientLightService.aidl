package android.os;

import android.os.IVehicleAmbientLightCallback;

/** @hide */
interface IVehicleAmbientLightService{
    int getLux();
    void setLux(int lux);

    void registerCallback(IVehicleAmbientLightCallback callback);
    void unregisterCallback(IVehicleAmbientLightCallback callback);
}