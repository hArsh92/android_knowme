package knowme.android.com.knowme.network;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

import knowme.android.com.knowme.KnowMeApplication;

/**
 * Created by Harsh on 24-08-2016.
 */
public class VolleySingleton {

    private static final String LOG_TAG = VolleySingleton.class.getSimpleName();
    private static KnowMeApplication APPLICATION;
    private RequestQueue mRequestQueue;
    private static VolleySingleton mVolley;
    private static final int REQUEST_TIME_OUT = 60000; //1MIN
    public static final int MAX_RETRIES = 3;


    public VolleySingleton() {
    }

    public void init(KnowMeApplication application){
        APPLICATION = application;

        mRequestQueue = Volley.newRequestQueue(application, new HurlStack());
    }

    public static synchronized VolleySingleton getInstance(){
        if (mVolley == null){
            mVolley = new VolleySingleton();
        }
        return mVolley;
    }

    private RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(APPLICATION, new HurlStack());
        }
        return mRequestQueue;
    }

    public void addToRequestQueue(final Request request, final String requestTag){
        Log.d(LOG_TAG, request.getUrl());

        request.setTag(requestTag);

        RetryPolicy retryPolicy = new DefaultRetryPolicy(REQUEST_TIME_OUT, MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(retryPolicy);

        getRequestQueue().add(request);
    }
}
