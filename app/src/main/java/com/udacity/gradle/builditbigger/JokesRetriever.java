package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.sovani.gce.jokeserver.myApi.MyApi;

import java.io.IOException;

/**
 * Created by msovani on 12/31/15.
 */
public class JokesRetriever extends AsyncTask<Void, Void, Void> {
    String joke;
    private MyApi jokeAPI = null;
    DataDownloader downloader;
    public void setDownloader (DataDownloader dataDownloader)
    {
        downloader = dataDownloader;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (jokeAPI == null)
        {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - 10.0.3.2 is localhost's IP address in Genymotion emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.3.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            jokeAPI = builder.build();
        }
        try {
            joke =  jokeAPI.getJoke().execute().getData();

        }catch (java.io.IOException ioe)
        {
            Log.d("Main", ioe.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //Call the appropriate interface method to tell caller if we have
        // managed to retrieve a joke from server or not
        super.onPostExecute(aVoid);
        if (joke != null) {
            if (this.downloader != null) {
                this.downloader.dowloadSucceeded(joke);
            }
        }else {
            if (this.downloader != null) {
                this.downloader.dowloadFailed();
            }
        }
    }
}