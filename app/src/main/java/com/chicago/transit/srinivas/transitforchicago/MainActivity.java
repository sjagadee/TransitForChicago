package com.chicago.transit.srinivas.transitforchicago;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.chicago.transit.srinivas.transitforchicago.models.RoutesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lvRoutes = (ListView)findViewById(R.id.lvRoutes);

        new GetDataForTheList().execute("http://www.ctabustracker.com/bustime/api/v2/getroutes?key=6Qvj3UgzA28GcWJPPaJf8bRAV&format=json");
    }

    private class GetDataForTheList extends AsyncTask<String, Void, List<RoutesModel>> {

        @Override
        protected List<RoutesModel> doInBackground(String... urls) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.connect();

                InputStream stream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String retData = buffer.toString();
                JSONObject object = new JSONObject(retData);

                JSONObject bustimeResponse = object.getJSONObject("bustime-response");

                JSONArray routes = bustimeResponse.getJSONArray("routes");

                List<RoutesModel> routesList = new ArrayList<>();

                for(int i = 0; i < routes.length(); i++) {
                    JSONObject eachRouteObject = routes.getJSONObject(i);

                    RoutesModel routesModel = new RoutesModel();
                    routesModel.setRt(eachRouteObject.getString("rt"));
                    routesModel.setRtnm(eachRouteObject.getString("rtnm"));

                    routesList.add(routesModel);
                }

                return routesList;

            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(urlConnection != null) {
                    urlConnection.disconnect();
                }
                if(reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<RoutesModel> result) {
            super.onPostExecute(result);

            // TODO: use list and set up list view
        }
    }

}

