package nl.porsius.enhancedreality;

import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;


public class CameraActivity extends ActionBarActivity {

    private Camera mCamera;
    private CameraPreview mPreview;
    private SensorManager sensorManager;
    private Sensor sensor;
    //private ImageView imageView;
    private FrameLayout container;
    private LayoutInflater inflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        // Create an instance of Camera
        mCamera = getCameraInstance();

        // Create our Preview view and set it as the content of our activity.
        mPreview = new CameraPreview(this, mCamera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);

        container = (FrameLayout) findViewById(R.id.container);
       // imageView = (ImageView) findViewById(R.id.imageView);
        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);

        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        if (sensor != null) {
            sensorManager.registerListener(mySensorEventListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);
            Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");
        } else {
            Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
            Toast.makeText(this, "ORIENTATION Sensor not found",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }


    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    private SensorEventListener mySensorEventListener = new SensorEventListener() {

        float azimuth;

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            // angle between the magnetic north direction
            // 0=North, 90=East, 180=South, 270=West
            azimuth = event.values[0];
            float pitch_angle = event.values[1];
            float roll_angle = event.values[2];

            System.out.println(" ccc azimuth "+azimuth);
            System.out.println(" ccc pitch "+pitch_angle);
            System.out.println(" ccc roll "+roll_angle);
            container.removeAllViews();
            if( (azimuth >130 && azimuth <160) && (roll_angle > 75 && roll_angle < 90) ) {
                // imageView.setImageResource(R.drawable.ic_launcher);

                CustomImageView customLayout = new CustomImageView(CameraActivity.this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int value = (int)(160-azimuth);
                customLayout.setInitPos(value);
                container.addView(customLayout,params);
            }
            else {
                container.removeAllViews();
            }
        }

        public float getAzimuth()
        {
            return azimuth;
        }
    };
   }
