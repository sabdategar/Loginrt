package com.example.acer.loginrt;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private EditText Nama;
    private EditText Password;

    String nilaiNama;
    String nilaiPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        Nama = (EditText)findViewById(R.id.nama);
        Password = (EditText)findViewById(R.id.password);

    }

    public void invokeLogin(View view){
        nilaiNama = Nama.getText().toString();
        nilaiPassword = Password.getText().toString();

        login(nilaiNama,nilaiPassword);
    }

    private void login(final String nilaiNama, String nilaiPassword){

        class loginAsync  extends AsyncTask<String, Void, String>{

                private Dialog pDialog;

            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                pDialog = ProgressDialog.show(LoginActivity.this, "tunggu", "Bentar");
            }

            @Override
            protected String doInBackground(String... params) {
                String uname = params[0];
                String pass = params[0];

                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("username", uname));
                nameValuePairs.add(new BasicNameValuePair("password", pass));

                String result = null;

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://sabda.esy.es/Login.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null){
                        sb.append(line + "\n");
                    }
                        result = sb.toString();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return result;
            }

            @Override
            protected void onPostExecute(String result){
                String s = result.trim();
                pDialog.dismiss();
                if(s.equalsIgnoreCase("success")){
                    Intent i = new Intent(LoginActivity.this, TambahData.class);
                    i.putExtra("username", nilaiNama);
                    finish();
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Salah Nama atau Password", Toast.LENGTH_LONG).show();
                }
            }
        }

                loginAsync la = new loginAsync();
                la.execute(nilaiNama, nilaiPassword);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exit) {

            System.exit(0);

        }
        return super.onOptionsItemSelected(item);
    }

}

