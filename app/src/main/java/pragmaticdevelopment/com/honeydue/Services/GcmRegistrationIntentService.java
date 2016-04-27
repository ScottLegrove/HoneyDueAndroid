package pragmaticdevelopment.com.honeydue.Services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import pragmaticdevelopment.com.honeydue.DBSource.APIConsumer;
import pragmaticdevelopment.com.honeydue.R;

public class GcmRegistrationIntentService extends IntentService {
    private final String PROJECT_NUMBER = "515341255994";

    public GcmRegistrationIntentService()
    {
        super("GcmRegistrationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferences sp = getSharedPreferences(getString(R.string.shared_pref_id), Context.MODE_PRIVATE);

        if(!sp.contains("token"))
        {
            return;
        }

        String token = sp.getString("token", "");

        InstanceID instanceID = InstanceID.getInstance(this);
        String gcmToken;

        try{
            gcmToken = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE);

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("gcmToken", token);
            editor.commit();

            sendTokenToServer(gcmToken, token);

            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(gcmToken, "/topics/global", null);

        }catch(Exception ex)
        {
        }
    }

    private void sendTokenToServer(String gcmToken, String userToken)
    {
        APIConsumer.updateGcmToken(gcmToken, userToken);
    }
}