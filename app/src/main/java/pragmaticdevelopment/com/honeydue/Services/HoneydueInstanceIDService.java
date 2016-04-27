package pragmaticdevelopment.com.honeydue.Services;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

public class HoneydueInstanceIDService extends InstanceIDListenerService {
    public void onTokenRefresh() {
        Intent intent = new Intent(this, GcmRegistrationIntentService.class);
        startService(intent);
    }
}
