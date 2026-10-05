package android.app;

import android.os.IVehicleAmbientLightService;
import android.os.VehicleAmbientLightManager;

public final class SystemServiceRegistry {

    /*
      Other SystemServiceRegistry variables, components and functions
    */

    @RavenwoodRedirect
    private static void registerServices() {
        
        //============ Add the following code ==================
        registerService(
            Context.VEHICLE_AMBIENT_LIGHT_SERVICE,
            VehicleAmbientLightManager.class,
            new CachedServiceFetcher<VehicleAmbientLightManager>() {
                @Override
                public VehicleAmbientLightManager createService(ContextImpl ctx) {
                    IBinder b = ServiceManager.getService(Context.VEHICLE_AMBIENT_LIGHT_SERVICE);
                    IVehicleAmbientLightService service = IVehicleAmbientLightService.Stub.asInterface(b);
                    return new VehicleAmbientLightManager(service);
                }
            }
        );
        
        //============ Add the above code ==================
    }
    
    
    /*
      Other SystemServiceRegistry variables, components and functions
    */

}
