package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sovani.jokesandroidlibary.JokeDisplay;
import com.udacity.gradle.builditbigger.DataDownloader;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.JokesRetriever;



public class MainActivity extends AppCompatActivity {
    DataDownloader dataDownloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataDownloader = new DataDownloader() {
            @Override
            public void dowloadSucceeded(String dataString) {
                displayJoke(dataString);
            }

            @Override
            public void dowloadFailed() {
                noJoke();
            }
        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){

        if (isOnline()) {

                showJoke();

        }else {
            Toast.makeText(this, this.getText(R.string.no_network), Toast.LENGTH_SHORT).show();
        }
    }

    public void noJoke()
    {
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        Toast.makeText(this, this.getText(R.string.failed_to_get_joke), Toast.LENGTH_SHORT).show();
    }

    //    credits : http://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-timeouts
    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showJoke(){
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        JokesRetriever jokesRetriever = new JokesRetriever();
        jokesRetriever.setDownloader(dataDownloader);
        jokesRetriever.execute();
    }
    private void displayJoke(String joke){
        Intent in = new Intent(getApplicationContext(), JokeDisplay.class);
        in.putExtra("Joke", joke);
        this.startActivity(in);

    }

}
