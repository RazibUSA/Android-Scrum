package com.gnirt69.mscrum.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.ButterKnife;
import butterknife.Bind;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gnirt69.mscrum.R;
import com.gnirt69.mscrum.constant.MSConstants;
import com.gnirt69.mscrum.model.DataHolder;
import com.gnirt69.mscrum.utils.JSONObjectManager;

import com.gnirt69.mscrum.utils.Utils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends ActionBarActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @Bind(R.id.input_email) EditText _emailText;
    @Bind(R.id.input_password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    @Bind(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _emailText.setText("po1@gmail.com");
        _passwordText.setText("123456");

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
//                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
//                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        //razib need to open later

        if (!validate()) {
            onLoginFailed();
            return;
        }

//        _loginButton.setEnabled(false);

//        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
//                R.style.AppTheme_Dark_Dialog);
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        //test data


        // TODO: Implement your own authentication logic here.

//        new android.os.Handler().postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onLoginSuccess or onLoginFailed
//                        onLoginSuccess();
//                        // onLoginFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);

        JSONObject userObj = new JSONObject();

        try {
            userObj.put("email", email);
            userObj.put("password", password);

            Log.d("userObj:", userObj.toString());


        } catch (JSONException e) {
            Log.d("JSONException", "JSONException"+e.toString());
            e.printStackTrace();
        }

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, MSConstants.USER_LOGIN, userObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", "Success");
                        Log.d("response", "Success"+response.toString());

                        try {
                            int status = response.getInt(MSConstants.KEY_STATUSCODE);
                            if(status ==1) {
                                JSONObject userData  = response.getJSONObject("data");
                                DataHolder.getInstance().setLogger(JSONObjectManager.parseUser(userData.getJSONObject("individual")));
                                int userType = JSONObjectManager.parseUserType(userData.getJSONObject("individual"));

                                if(userType > 0) {

                                    switch(userType){

                                        case 1:  //System admin
                                            if(userData.has("userList")) {
                                                JSONArray userList = (JSONArray) userData.get("userList");
                                                if (userList != null && userList.length() > 0) {
                                                    DataHolder.getInstance().setUserList(JSONObjectManager.parseUserListData(userList));
                                                }

                                            }
                                            break;
                                        case 2:  //po
                                            if(userData.has("projectList")) {
                                                JSONArray projectList = (JSONArray) userData.get("projectList");
                                                if (projectList != null) {
//                                                    DataHolder.getInstance().setUserList(JSONObjectManager.parseUserListData(userList));
                                                    DataHolder.getInstance().setProjectList(JSONObjectManager.parseProjectData(projectList));
                                                }
                                            }
                                        case 3:  //Sm
                                        case 4:  //dev
                                        default:
                                            Log.d("User Type:", "Not a valid User");

                                    }





                                //permission list
                                //save token n user type

                                String token = userData.getString("token");
//                                JSONObject role = userData.



                                   SharedPreferences sharedpreferences = getSharedPreferences(MSConstants.MSPREFERENCES, Context.MODE_PRIVATE);
                                   Utils.Save(sharedpreferences, token, userType);
                                   onLoginSuccess(userType);
                                   progressDialog.dismiss();
                               } else {
                                   progressDialog.dismiss();
                                   Toast.makeText(getBaseContext(), "Could't find Role", Toast.LENGTH_LONG).show();
                               }


                            }else {
                                Toast.makeText(getBaseContext(), "Email or Password is Incorrect", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.d("JSONException", ""+e.toString());
//                            e.printStackTrace();
                        }

                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Error", "Nope"+error.toString());
                        progressDialog.dismiss();
                    }
                }){
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String, String>();
//                params.put("firstName", "gggg");
////                params.put(MSConstants.KEY_LASTNAME, lName);
////                params.put(MSConstants.KEY_EMAIL, emailAddress);
////                params.put(MSConstants.KEY_PASSWORD,passwordText);
////                Log.d("params", ":" +params.get());
//
//                return params;
//            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
////                headers.put("Accept", "application/json");
//                Log.d("headers", ":" +headers.get("Content-Type"));
//                return headers;
//            }

        };

        Log.d("stringRequest", ":" +stringRequest);
//        stringRequest.setRetryPolicy(new RetryPolicy() {
//            @Override
//            public int getCurrentTimeout() {
//                return 50000;
//            }
//
//            @Override
//            public int getCurrentRetryCount() {
//                return 50000;
//            }
//
//            @Override
//            public void retry(VolleyError error) throws VolleyError {
//
//            }
//        });


        requestQueue.add(stringRequest);




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(int userType) {
        _loginButton.setEnabled(true);
        Intent intent=new Intent();
        intent.putExtra("USERTYPE",userType);
        setResult(200,intent);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
