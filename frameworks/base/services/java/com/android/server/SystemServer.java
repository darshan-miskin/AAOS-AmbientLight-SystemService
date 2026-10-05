package com.android.server;

import com.android.server.VehicleAmbientLightService;

public final class SystemServer implements Dumpable {
  /*
    System Server other components, variables and functions
  */
  
  private void startOtherServices(@NonNull TimingsTraceAndSlog t) {
      /*
        other services
      */
      
      //========== Add the following code ================
      if(isAutomotive){
          t.traceBegin("StartVehicleAmbientLightService");
          try {
              Slog.i(TAG, "Starting VehicleAmbientLightService (Kotlin)");
              VehicleAmbientLightService ambientLightService = new VehicleAmbientLightService(context);
              ServiceManager.addService(Context.VEHICLE_AMBIENT_LIGHT_SERVICE, ambientLightService);
          } catch (Throwable e) {
              Slog.e(TAG, "Failure starting VehicleAmbientLightService", e);
          }
          t.traceEnd();
      }
      //========== Add the above code ================
      
       /*
        other services
      */
  }
  
  /*
    System Server other components, variables and functions
  */

}
