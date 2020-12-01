package com.example.consumirwsvolley;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue request;
    private StringRequest stringr;
    TextView txtlanguages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void btnenviar(View view)
    {
        txtlanguages= (TextView)findViewById(R.id.lbllistalanguages);

        txtlanguages.setText("Solicitando Lenguajes de programación...");
        request = Volley.newRequestQueue(MainActivity.this);
        String URL = "https://judge0.p.rapidapi.com/languages";

        stringr = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                String lstlanguages = new String ();
                JSONArray JSONlista = null;
                try {
                    JSONlista = new JSONArray(response);
                    for(int i=0; i< JSONlista.length();i++){
                        JSONObject language=
                                JSONlista.getJSONObject(i);
                        lstlanguages= lstlanguages +"\n"+ language.getString("name").toString();
                    }
                    Intent intent = new Intent(MainActivity.this, recibir.class);
                    Bundle b = new Bundle();
                    b.putString("languages", lstlanguages);
                    intent.putExtras(b);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtlanguages.setText(error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("x-rapidapi-key", "c1ff95d264msh5b7960c362d7596p1212f5jsn1f346837947e");
                params.put("x-rapidapi-host", "judge0.p.rapidapi.com");
                return params;
            } };
        request.add(stringr);
    }
}