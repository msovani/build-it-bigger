package com.udacity.gradle.builditbigger;

/**
 * Created by msovani on 12/31/15.
 */
public interface DataDownloader {
    void dowloadSucceeded(String dataString);
    void dowloadFailed();
}
