package com.example.cameraHDR;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.example.cameraexample.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraActivity extends Activity implements OnClickListener, PictureCallback {

	private CameraSurfaceView cameraSurfaceView;
	private Button shutterButton;
	
	private Bitmap imgOne; 
	private int state = 0;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		// set up our preview surface
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		cameraSurfaceView = new CameraSurfaceView(this);
		preview.addView(cameraSurfaceView);

		// grab out shutter button so we can reference it later
		shutterButton = (Button) findViewById(R.id.shutter_button);
		shutterButton.setOnClickListener(this);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_camera, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
					
		takePicture();
		
	}

	private void takePicture() {
		shutterButton.setEnabled(false);
		cameraSurfaceView.takePicture(this);
	}

	 protected File getPhotoPath() {
		 
		    File dir=getPhotoDirectory();
		    dir.mkdirs();

		    return(new File(dir, getPhotoFilename()));
	 }
	 
	  protected File getPhotoDirectory() {
	    return(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM));
	  }

	  protected String getPhotoFilename() {
	    String ts=
	        new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
	
	    return("Photo_" + ts + ".jpg");
	  }
	
	//(CompCam): Function to manage the images taken
	public void onPictureTaken(byte[] data, Camera camera) {

		Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
	
		//(CompCam): state machine to handle the images
		if(state == 0){
		
			Log.d("debugHDR", "changing parameters to Hi exposure");
			
			
			//(CompCam): INSERT_CODE_HERE: 
			// access the camera via cameraSurcafeView.getCamera(), and access parameters from cam to change the exposure compensation to the max
			// see http://developer.android.com/reference/android/hardware/Camera.Parameters.html for more details to change the params. 
			// don't forget to set the parameter object changes at the camera again using setParameters function. 
			
			
			
			state = 1;
			
		}		
		else if(state == 1){
			
			Log.d("debugHDR", "storing image and changing parameters to Low exposure");
			
			//(CompCam): INSERT_CODE_HERE: 
			// Copy the actual frame (bmp object) to another bitmap (like imgOne variable of this class) (do not forget to set the copied bitmap as morphable) 
			// then access the camera via cameraSurcafeView.getCamera(), and access parameters from cam to change the exposure compensation to the min
			// see http://developer.android.com/reference/android/hardware/Camera.Parameters.html for more details to change the params. 
			// don't forget to set the parameter object changes at the camera again using setParameters function. 
			
			
			
			state = 2;
				
		}
		else if(state == 2){
			
			Log.d("debugHDR", "processing images");
			
			//(CompCam): process the Imgs to get a new image in HDR
			hdrProcessing(bmp);

			//(CompCam): save the new bitmap as a image file
			File photo=getPhotoPath();
			
		    if (photo.exists()) {
		      photo.delete();
		    }
			
			try {
		       FileOutputStream out = new FileOutputStream(photo.getPath());
		       Log.d("debugHDR", "img one saving");
		       imgOne.compress(Bitmap.CompressFormat.PNG, 90, out); //if you find that the image is poor in quality, adjust the "90" here to a different compression amount.
		       Log.d("debugHDR", "img one saved");
		       out.close();
			} 
			catch (Exception e) {
		       e.printStackTrace();
			}
			
			Log.d("debugHDR", "changing parameters to normal exposure");
			
			
			//(CompCam):INSERT_CODE_HERE: optionally, you can set the exposure to default value here
			
			
			state = 0;
		
			
		}
		
		
		// Restart the preview and re-enable the shutter button so that we can take another picture
		camera.startPreview();
		shutterButton.setEnabled(true);
	}
	
	
	public void hdrProcessing(Bitmap bitmap)
	{
		//(CompCam):INSERT_CODE_HERE: 
		// process the images (bitmap and imgOne for example) to get a HDR img
				
	}
	
	
}
