package knowme.android.com.knowme.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harsh on 24-08-2016.
 */
public class Request extends JsonRequest<String> {

    private final Gson gson = new Gson();
    private final Map<String, String> headers;
    private final Response.Listener<String> listener;
    // Used for request which do not return anything from the server
    private boolean muteRequest = false;

    /**
     * Basically, this is the constructor which is called by the others.
     * It allows you to send an object of type A to the server and expect a JSON representing a object of type B.
     * The problem with the #JsonObjectRequest is that you expect a JSON at the end.
     * We can do better than that, we can directly receive our POJO.
     * That's what this class does.
     *
     * @param method:        HTTP Method
     * @param url:           url to be called
     * @param requestBody:   The body being sent
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param headers:       Added headers
     */
    private Request(int method, String url, String requestBody,
                           Response.Listener<String> listener, Response.ErrorListener errorListener, Map<String, String> headers) {
        super(method, url, requestBody, listener,
                errorListener);
        this.listener = listener;
        this.headers = headers;
    }

    /**
     * Method to be called if you want to send some objects to your server via body in JSON of the request (with headers and not muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param toBeSent:      Object which will be transformed in JSON via Gson and sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param headers:       Added headers
     */
    public Request(int method, String url,  Object toBeSent,
                          Response.Listener<String> listener, Response.ErrorListener errorListener, Map<String, String> headers) {
        this(method, url, new Gson().toJson(toBeSent), listener,
                errorListener, headers);
    }

    /**
     * Method to be called if you want to send some objects to your server via body in JSON of the request (without header and not muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param toBeSent:      Object which will be transformed in JSON via Gson and sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     */
    public Request(int method, String url,  Object toBeSent,
                          Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(method, url, new Gson().toJson(toBeSent), listener,
                errorListener, new HashMap<String, String>());
    }

    /**
     * Method to be called if you want to send something to the server but not with a JSON, just with a defined String (without header and not muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param requestBody:   String to be sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     */
    public Request(int method, String url,  String requestBody,
                          Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(method,  url, requestBody, listener,
                errorListener, new HashMap<String, String>());
    }

    /**
     * Method to be called if you want to GET something from the server and receive the POJO directly after the call (no JSON). (Without header)
     *
     * @param url:           URL to be called
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     */
    public Request(String url,  Response.Listener<String> listener, Response.ErrorListener errorListener) {
        this(Request.Method.GET, url,  "", listener, errorListener);
    }

    /**
     * Method to be called if you want to GET something from the server and receive the POJO directly after the call (no JSON). (With headers)
     *
     * @param url:           URL to be called
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param headers:       Added headers
     */
    public Request(String url,  Response.Listener<String> listener, Response.ErrorListener errorListener, Map<String, String> headers) {
        this(Request.Method.GET,  url, "", listener, errorListener, headers);
    }

    /**
     * Method to be called if you want to send some objects to your server via body in JSON of the request (with headers and muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param toBeSent:      Object which will be transformed in JSON via Gson and sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param headers:       Added headers
     * @param mute:          Muted (put it to true, to make sense)
     */
    public Request(int method, String url,  Object toBeSent,
                          Response.Listener<String> listener, Response.ErrorListener errorListener, Map<String, String> headers, boolean mute) {
        this(method,  url, new Gson().toJson(toBeSent), listener,
                errorListener, headers);
        this.muteRequest = mute;
    }

    /**
     * Method to be called if you want to send some objects to your server via body in JSON of the request (without header and muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param toBeSent:      Object which will be transformed in JSON via Gson and sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param mute:          Muted (put it to true, to make sense)
     */
    public Request(int method, String url,  Object toBeSent,
                          Response.Listener<String> listener, Response.ErrorListener errorListener, boolean mute) {
        this(method,  url, new Gson().toJson(toBeSent), listener,
                errorListener, new HashMap<String, String>());
        this.muteRequest = mute;

    }

    /**
     * Method to be called if you want to send something to the server but not with a JSON, just with a defined String (without header and not muted)
     *
     * @param method:        HTTP Method
     * @param url:           URL to be called
     * @param requestBody:   String to be sent to the server
     * @param listener:      Listener of the request
     * @param errorListener: Error handler of the request
     * @param mute:          Muted (put it to true, to make sense)
     */
    public Request(int method, String url,  String requestBody,
                          Response.Listener<String> listener, Response.ErrorListener errorListener, boolean mute) {
        this(method,  url, requestBody, listener,
                errorListener, new HashMap<String, String>());
        this.muteRequest = mute;

    }


 /*   @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }*/

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        // The magic of the mute request happens here
        if (muteRequest) {
            if (response.statusCode >= 200 && response.statusCode <= 299) {
                Log.i("Health Monk", "Http Status : " + response.statusCode);
                // If the status is correct, we return a success but with a null object, because the server didn't return anything
                return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
            }
        } else {
            try {
                // If it's not muted; we just need to create our POJO from the returned JSON and handle correctly the errors
                String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                Log.i("Health Monk", "Http Response : " + json);
                return Response.success(json, HttpHeaderParser.parseCacheHeaders(response));
            } catch (UnsupportedEncodingException e) {
                return Response.error(new ParseError(e));
            } catch (JsonSyntaxException e) {
                return Response.error(new ParseError(e));
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }
}