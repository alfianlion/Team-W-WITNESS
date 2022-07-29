package sg.edu.np.mad.WittnessFittness;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class weatherChecker extends AppCompatActivity implements LocationListener {
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    TextView Loading;
    TextView showLat;
    ImageView icon;

    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appId = "ac68037a29baa28a04755b33e25159d7";
    DecimalFormat df = new DecimalFormat("#.##");


//    @Override
//    public void setContentView(View view) {
//        super.setContentView(view);
//        if (ContextCompat.checkSelfPermission(weatherChecker.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            TextView txt = findViewById(R.id.loading);
//            txt.setText("Please enable your location for this feature");
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_checker);

        Loading = (TextView) findViewById(R.id.loading);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        String tempUrl = "";
        tempUrl = url + "?lat=" + Math.round(location.getLatitude()) + "&lon=" + Math.round(location.getLongitude())  + "&appid=" + appId;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                String output = "";
                Log.d("response", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String main = jsonObjectWeather.getString("main");
                    String description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    int humid = jsonObjectMain.getInt("humidity");
                    Log.d("weather", main);
                    Loading.setVisibility(View.GONE);
                    TextView WeatherStatus = findViewById(R.id.weatherStatus);
                    TextView weatherDesc = findViewById(R.id.weatherDescription);
                    TextView temperature = findViewById(R.id.temperature);
                    TextView humidity = findViewById(R.id.humidity);
                    ImageView icon = findViewById(R.id.weatherIcon);
                    WeatherStatus.setVisibility(View.VISIBLE);
                    weatherDesc.setVisibility(View.VISIBLE);
                    temperature.setVisibility(View.VISIBLE);
                    humidity.setVisibility(View.VISIBLE);
                    icon.setVisibility(View.VISIBLE);

                    WeatherStatus.setText(main + "");
                    weatherDesc.setText(description + "");
                    temperature.setText("Temperature: "+df.format(temp) + " Celcuis");
                    humidity.setText("Humidity: "+ humid + " %");

                    if (main == "Clear") {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.clearsky));
                    } else if (main == "Clouds") {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                    } else if (main == "Thunderstorm") {
                        icon.setImageDrawable(getResources().getDrawable(R.drawable.thunderstorm));
                    } else if (main == "") {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }
}