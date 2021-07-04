package com.dadashow.serviceexampleapp;


import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class DownloadService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AsyncTaskCom asyncTaskCom = new AsyncTaskCom();
        try {
            asyncTaskCom.execute("https://www.atilsamancioglu.com/").get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    class AsyncTaskCom extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;
            try {
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader streamReader = new InputStreamReader(inputStream);
                int data = streamReader.read();
                while (data != -1) {
                    char current = (char) data;
                    result += current;
                    data = streamReader.read();
                }
                return result;
            } catch (Exception e) {

                e.printStackTrace();
                return "Failed";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("Result: " + s);
            super.onPostExecute(s);
        }

    }
}
