package in.co.msedcl.msebapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class NotificationActivity extends AppCompatActivity {

    //Volley Request Queue
    private RequestQueue requestQueue;
    ProgressDialog progressDialog;
    int notificationId = 001;
    PendingIntent resultPendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        requestQueue = Volley.newRequestQueue(this);
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.alert)
                        .setContentTitle("Alert")
                        .setContentText("In danger")
                .setSound(soundUri);

        mBuilder.setContentIntent(resultPendingIntent);




        NotificationManager nm =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(notificationId, mBuilder.build());

requestQueue.add(getData());




    }

    public StringRequest getData()
    {

         progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest getRequest = new StringRequest(Request.Method.POST, Config.NOTIFICATION_URL,
                new Response.Listener<String>()
                {

                    @Override
                    public void onResponse(String response) {
                        // display response
                        Log.d("Response", response);
                        //progressDialog.hide();

                        try
                        {

                            JSONObject jsonObject=new JSONObject(response);
                            String res=jsonObject.getString("result");
                            Log.e("result", res);
                            Log.e("log_tag", jsonObject.toString());
                            if(res.equalsIgnoreCase("success"))
                            {

                                JSONArray jArray=jsonObject.getJSONArray("notification");

                                /*for(int i=0;i<jArray.length();i++)
                                {*/
                                    JSONObject json_data = jArray.getJSONObject(0);
                                    int id=json_data.getInt("id");
                                    String alert_type=json_data.getString("alert_type");
                                    String alert_area=json_data.getString("alert_area");
                                    String alert_value=json_data.getString("alert_value");
                                    Log.e("receive data",id+alert_area+alert_type+alert_value);


                               // }

                                progressDialog.dismiss();

                            }


                        } catch (JSONException e) {
                            Log.e("JSON Error : ",e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("Error.Response", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        return getRequest;

    }
}
