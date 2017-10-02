package util.remoter.test;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.test.rule.ActivityTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import util.remoter.client.TestActivity;
import util.remoter.common.CustomData;
import util.remoter.common.IEchoService;
import util.remoter.common.ISampleService;
import util.remoter.common.ISampleService_Proxy;

import static util.remoter.common.ServiceIntents.INTENT_REMOTER_SERVICE;
import static util.remoter.common.ServiceIntents.INTENT_REMOTER_TEST_ACTIVITY;


/**
 * Tests Remoter-Parceler interop
 */
public class RemoterParcelerTest {

    private static final String TAG = RemoterParcelerTest.class.getSimpleName();
    private Object objectLock = new Object();
    private ISampleService sampleService;


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            sampleService = new ISampleService_Proxy(iBinder);
            synchronized (objectLock) {
                objectLock.notify();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    @Rule
    public ActivityTestRule<TestActivity> mActivityRule = new ActivityTestRule<TestActivity>(TestActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Intent intent = new Intent(INTENT_REMOTER_TEST_ACTIVITY);
            return intent;
        }
    };

    @Before
    public void setup() throws InterruptedException {
        synchronized (objectLock) {
            Intent remoterServiceIntent = new Intent(INTENT_REMOTER_SERVICE);
            remoterServiceIntent.setClassName("util.remoter.remoterservice", INTENT_REMOTER_SERVICE);

            mActivityRule.getActivity().startService(remoterServiceIntent);
            mActivityRule.getActivity().bindService(remoterServiceIntent, serviceConnection, 0);

            objectLock.wait();
        }
    }

    public void teardown() {
        mActivityRule.getActivity().unbindService(serviceConnection);
    }


    @Test
    public void testRemoterParcelerData() throws RemoteException {

        Assert.assertNotNull(sampleService);

        //get @Parcel data
        CustomData customData = sampleService.getParcelerData();

        Assert.assertNotNull(customData);

        //get the @Remoter interface from the @Parcel data
        IEchoService echoService = customData.getEchoService();

        //Check valid non null
        Assert.assertNotNull(echoService);

        //Check we can call remote method on it
        String data = "Hello";
        Assert.assertEquals(data, echoService.echo(data));
    }


}

