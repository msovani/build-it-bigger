package com.udacity.gradle.builditbigger;

/**
 * Created by msovani on 12/31/15.
 */
public interface DataDownloader {
    public void dowloadSucceeded(String dataString);
    public void dowloadFailed();
}
