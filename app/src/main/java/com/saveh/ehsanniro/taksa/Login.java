package com.saveh.ehsanniro.taksa;

import java.io.IOException;
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends ListActivity {

    String Password;
    String UserName;
    String Role;
    Intent i;
    String Message;
    String ManagerFirstName;
    String ManagerLastName;
    String BusinessUnitName;
    String Token;
    int Status = 200;

    Typeface tf;
    private ProgressDialog pDialog;

  //  JSONParser jParser = new JSONParser();

  //  GetNewToken Tk = new GetNewToken();

    String Url = "login";
    private String url_Login_check = "login";

    private static final String TAG_UserName = "UserName";
    private static final String TAG_Password = "Password";
    private static final String TAG_ManagerFirstName = "ManagerFirstName";
    private static final String TAG_ManagerLastName = "ManagerLastName";
    private static final String TAG_BusinessUnitName = "BusinessUnitName";
    private static final String TAG_Token = "Token";
     DbHelper mDbHelper;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {

    //    mDbHelper = new DbHelper(this);
        InitalizeFont();

    //    url_Login_check = getString(R.string.HostURL)
    //            + getString(R.string.BaseURL) + Url;
        Log.d("login_URL", url_Login_check);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final EditText User = (EditText) findViewById(R.id.inputUserName);
        final EditText pass = (EditText) findViewById(R.id.inputPassword);
        TextView UserNametxt = (TextView) findViewById(R.id.UserName);
        UserNametxt.setTypeface(tf);
        UserNametxt.setTextColor(Color.rgb(205, 147, 88));
        TextView Pass = (TextView) findViewById(R.id.Password);
        Pass.setTypeface(tf);
        Pass.setTextColor(Color.rgb(205, 147, 88));

        Button LoginBtn = (Button) findViewById(R.id.btnLogin);
        LoginBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);


                SQLiteDatabase mDb = null;
                try {
                    mDbHelper.createDataBase();
                    mDbHelper.openDataBase();
                    mDbHelper.close();
                    mDb = mDbHelper.getWritableDatabase();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {

                    String sql = "Delete FROM UserInfo ";

                    Cursor c = mDb.rawQuery(sql, null);
                    if (c != null) {
                        mDb.execSQL(sql);
                        mDb.close();

                    }

                } catch (SQLException mSQLException) {
                    throw mSQLException;
                }
                if (User.getText().toString().trim().equalsIgnoreCase(""))
                    User.setError("لطفا این قسمت را پر کنید");
                else if (pass.getText().toString().trim().equalsIgnoreCase(""))
                    pass.setError("لطفا این قسمت را پر کنید");
                else {

                    UserName = User.getText().toString();
                    Password = pass.getText().toString();
                    Log.d("USer", UserName);

                    new LoginCheck().execute();
                }
            }
        });

    }

    /**
     * Background Async Task to Load all product by making HTTP Request
     * */
    class LoginCheck extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("در حال اتصال به سرور و بررسی اطلاعات ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * getting All products from url
         * */
        protected String doInBackground(String... args) {
            // Building Parameters
        /*    List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(TAG_UserName, UserName));
            params.add(new BasicNameValuePair(TAG_Password, Password));
            Log.d("Role", params.toString());*/

          /*  JSONObject json = jParser.makeHttpRequest(url_Login_check, "POST",
                    params);*/

            // Checking for SUCCESS TAG
          /*      Status = jParser.httpResponse.getStatusLine().getStatusCode();*/
            Log.d("Status", Status + " ");

            if (Status == 200) {
                Log.d("asdasd", "success");

      /*          ManagerFirstName = json.getString(TAG_ManagerFirstName);
                ManagerLastName = json.getString(TAG_ManagerLastName);
                BusinessUnitName = json.getString(TAG_BusinessUnitName);
                Token = json.getString(TAG_Token);*/
                Log.d("asdasfwfa", ManagerFirstName);
                Log.d("asdasfwfa", ManagerLastName);

            }


            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after getting all products
            pDialog.dismiss();
            Log.d("All Products: ", Status + "1");

            if (Status == 200) {

                AlertDialog alertDialog = new AlertDialog.Builder(
                        Login.this).create();
                alertDialog.setTitle("خوش آمدید");
                alertDialog.setMessage("کاربر گرامی " + ManagerFirstName + " "
                        + ManagerLastName + " خوش آمدید ");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "وارد شو !",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                Intent i = new Intent(getApplicationContext(),
                                        MainActivity.class);
                                i.putExtra(TAG_ManagerFirstName,
                                        ManagerFirstName);
                                i.putExtra(TAG_ManagerLastName, ManagerLastName);
                                i.putExtra(TAG_BusinessUnitName,
                                        BusinessUnitName);
                                i.putExtra(TAG_UserName, UserName);
                                i.putExtra(TAG_Password, Password);
                                i.putExtra(TAG_Token, Token);

                                SQLiteDatabase mDb = null;
                                try {

                                    mDbHelper.createDataBase();
                                    mDbHelper.openDataBase();

                                    mDb = mDbHelper.getWritableDatabase();
                                    String sql = "Delete FROM UserInfo ";

                                    Cursor c = mDb.rawQuery(sql, null);

                                    if (c != null) {
                                        mDb.execSQL(sql);
                                    }
                                    ContentValues insertValues = new ContentValues();

                                    insertValues.put("UserName", UserName);
                                    insertValues.put("Password", Password);

                                    insertValues.put("BusinessUnitName",
                                            BusinessUnitName);
                                    insertValues.put("ManagerLastName",
                                            ManagerLastName);
                                    insertValues.put("ManagerFirstName",
                                            ManagerFirstName);
                                    insertValues.put("Token", Token);
                                    mDb.insert("UserInfo", null, insertValues);
                                    mDb.close();

                                    mDbHelper.close();

                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                startActivity(i);

                                Login.this.finish();

                            }
                        });

                alertDialog.show();

            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(
                        Login.this).create();
                alertDialog.setTitle("خطا");
                alertDialog.setMessage("اطلاعات وارد شده صحیح نمیباشد");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,
                        "یک بار دیگر تلاش میکنم !",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();

                            }
                        });
                alertDialog.show();

            }

        }

    }

    void InitalizeFont() {
        tf = Typeface.createFromAsset(getAssets(), "fonts/BNazanin.ttf");

    }
}