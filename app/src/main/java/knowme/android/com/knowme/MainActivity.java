package knowme.android.com.knowme;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import knowme.android.com.knowme.network.Request;
import knowme.android.com.knowme.network.UrlHelper;
import knowme.android.com.knowme.network.VolleySingleton;

/**
 * Created by Harsh on 24-08-2016.
 */
public class MainActivity extends AppCompatActivity{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    EditText mFullName, mEmail;
    Spinner mGender;
    Button mSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_content);

        mEmail = (EditText) findViewById(R.id.email);
        mFullName = (EditText) findViewById(R.id.name);
        mGender = (Spinner) findViewById(R.id.gender);
        mSignUp = (Button) findViewById(R.id.signup);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signMeUp();
            }
        });
    }

    private void signMeUp(){
        if (TextUtils.isEmpty(mEmail.getText())){
            Toast.makeText(this, getString(R.string.enter_email_error), Toast.LENGTH_LONG).show();
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail.getText()).matches()){
            Toast.makeText(this, getString(R.string.enter_valid_email_error), Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(mFullName.getText())){
            Toast.makeText(this, getString(R.string.enter_name_error), Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(mGender.getSelectedItem().toString())){
            Toast.makeText(this, getString(R.string.select_gender_error), Toast.LENGTH_LONG).show();
        } else {
            Request saveUserRequest = new Request(getSaveUserUrl(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(LOG_TAG, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            VolleySingleton.getInstance().addToRequestQueue(saveUserRequest, UrlHelper.saveUser);
        }
    }

    private String getSaveUserUrl(){
        Uri.Builder userUriBuilder = Uri.parse(UrlHelper.saveUser).buildUpon();
        userUriBuilder.appendQueryParameter("user_email", mEmail.getText().toString())
                .appendQueryParameter("user_name", mFullName.getText().toString())
                .appendQueryParameter("user_gender", mGender.getSelectedItem().toString())
                .build();

        return userUriBuilder.toString();
    }
}
