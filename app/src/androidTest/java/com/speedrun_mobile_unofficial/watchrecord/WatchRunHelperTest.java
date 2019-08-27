package com.speedrun_mobile_unofficial.watchrecord;

import android.app.Instrumentation;
import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import okhttp3.mockwebserver.MockWebServer;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class WatchRunHelperTest {

    @Rule
    public MockWebServer mockWebServer = new MockWebServer();
    private Context context;

    @Before
    public void before() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void IfFetchRunDataSuccess() {

        String runId = "zp444jrz";
        Map<String, Object> map;
        RunModel model = new RunModel();

        final CountDownLatch signal = new CountDownLatch(1);
        final StringBuilder strBuilder = new StringBuilder();

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(() -> {
            WatchRunHelper.fetchRunData(context, runId, ((success, result) -> {
                model.setMap(result);
                signal.countDown();
            }));
        });

        try {
            signal.await(5, TimeUnit.SECONDS); // wait for callback
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertThat(model.getMap()).containsKeys("weblink");
        assertThat(signal.getCount()).isEqualTo(0);
    }

}