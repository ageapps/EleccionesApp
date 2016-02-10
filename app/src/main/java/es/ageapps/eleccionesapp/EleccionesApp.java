package es.ageapps.eleccionesapp;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.PushService;

/**
 * Created by adricacho on 23/11/15.
 */
public class EleccionesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "a62OWoPm0nWef8SoAXHGQpRQb0xmteRmjzIRrxqL", "PkeFiNp6wgI2XG0imwEApwHcBsRd67QNWya5hEeO");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public static String myId() {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        return installation.getInstallationId();
    }
}
