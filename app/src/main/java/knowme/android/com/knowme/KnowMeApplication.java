package knowme.android.com.knowme;

import android.app.Application;

import knowme.android.com.knowme.network.VolleySingleton;

/**
 * Created by Harsh on 24-08-2016.
 */
public class KnowMeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        VolleySingleton.getInstance().init(this);
    }
}
