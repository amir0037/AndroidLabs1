package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    ImageView weatherImage;
    TextView currentTemp;
    TextView minTemp;
    TextView maxTemp;
    TextView UV;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        weatherImage = findViewById(R.id.weatherImage);
        currentTemp = findViewById(R.id.currentTemp);
        minTemp = findViewById(R.id.minTemp);
        maxTemp = findViewById(R.id.maxTemp);
        UV = findViewById(R.id.UV);

        ForecastQuery req = new ForecastQuery(); //creates a background thread
        req.execute(new String [] {"http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric",
                "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389"
                });  //Type 2

        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
    }


    class ForecastQuery extends AsyncTask<String, Integer, String> {

        String curT;
        String minT;
        String maxT;
        String UVvalue;
        Bitmap weather;
        String iconName;

        @Override
        protected String doInBackground(String... args) {
            try {
                //create a URL object of what server to contact:
                URL url0 = new URL(args[0]);
                URL url1 = new URL(args[1]);

                HttpURLConnection urlConnection0 = (HttpURLConnection) url0.openConnection();//wait for data:
                InputStream response0 = urlConnection0.getInputStream();
                HttpURLConnection urlConnection1 = (HttpURLConnection) url1.openConnection();//wait for data:
                InputStream response1 = urlConnection1.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response0 , "UTF-8");

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("temperature"))
                        {
                            //If you get here, then you are pointing to a <Temperature> start tag
                            curT = xpp.getAttributeValue(null, "value");
                            minT = xpp.getAttributeValue(null, "min");
                            maxT = xpp.getAttributeValue(null, "max");
                            publishProgress(25);
                            publishProgress(50);
                            publishProgress(75);//?
                        }

                        else if(xpp.getName().equals("weather"))
                        {
                            iconName = xpp.getAttributeValue(null, "icon"); // this will run for <AMessage message="parameter" >
                        }
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(response1, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string
                // convert string to JSON: Look at slide 27:
                JSONObject uvReport = new JSONObject(result);
                //get the double associated with "value"
                UVvalue = uvReport.getString("value");
                Log.i("MainActivity", "The uv is now: " + UVvalue) ;


                weather = null;
                URL url = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    weather = BitmapFactory.decodeStream(connection.getInputStream());
                }

                publishProgress(100);

                String filename = iconName + ".png";
                FileOutputStream outputStream = openFileOutput( filename, Context.MODE_PRIVATE);
                weather.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                outputStream.flush();
                outputStream.close();

                if(fileExistance(filename)) {

                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(filename);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    weather = BitmapFactory.decodeStream(fis);
                }
            }
            catch (Exception e) {

            }
            return "Done";
        }

        public boolean fileExistance (String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }



        public void onPostExecute(String fromDoInBackground) {
            currentTemp.setText("Temperature now is: " + curT + " C");
            minTemp.setText("Minimum temperature is: " + minT + " C");
            maxTemp.setText("Maximum temperature is: " + maxT + " C");
            UV.setText("UV index is: " + UVvalue);
            weatherImage.setImageBitmap(weather);
            progressBar.setVisibility(View.INVISIBLE);
        }


        public  void onProgressUpdate(Integer...args){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(args[0]);

        }

    }

}