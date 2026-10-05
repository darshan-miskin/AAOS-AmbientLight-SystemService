package android.os;

/** @hide */
oneway interface IVehicleAmbientLightCallback {
    void onLuxChanged(int lux);
}