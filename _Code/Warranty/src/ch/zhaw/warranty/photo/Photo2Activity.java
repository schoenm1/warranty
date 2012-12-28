package ch.zhaw.warranty.photo;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import ch.zhaw.warranty.R;

public class Photo2Activity extends Activity {

	private static final int TAKE_PHOTO_CODE=1;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
//	    setContentView(R.layout.capture);

	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri());
	startActivityForResult(intent, TAKE_PHOTO_CODE);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);

	    if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
	        Uri imagePath = getImageUri();

	        System.out.println("imagePath");
	    }
	}

	/**
	 * Get the uri of the captured file
	 * @return A Uri which path is the path of an image file, stored on the dcim folder
	 */
	private Uri getImageUri() {
	    File file = new File(Environment.getExternalStorageDirectory() + "/Warranty");
	    Uri imgUri = Uri.fromFile(file);
	    return imgUri;
	}
	
	
}