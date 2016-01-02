package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.UiThreadTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    CountDownLatch signal;
    JokesRetriever jokesRetriever;
    String joke;
    public ApplicationTest() {
        super(Application.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        signal = new CountDownLatch(1);
        jokesRetriever = new JokesRetriever();

        jokesRetriever.setDownloader(new DataDownloader() {
            @Override
            public void dowloadSucceeded(String dataString) {
                joke = dataString;
            }

            @Override
            public void dowloadFailed() {
                joke = null;
            }
        });

        jokesRetriever.execute();

    }

    @UiThreadTest
    public void testDownload() throws InterruptedException
    {
        signal.await(30, TimeUnit.SECONDS);

        assertTrue("Valid joke is returned", joke != null);
    }

}