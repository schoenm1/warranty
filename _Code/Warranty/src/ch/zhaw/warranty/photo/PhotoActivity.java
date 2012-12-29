package ch.zhaw.warranty.photo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import ch.zhaw.warranty.R;

public class PhotoActivity extends Activity {

	private static final int ACTION_TAKE_PHOTO_B = 1;

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";
	private ImageView mImageView;
	private Bitmap mImageBitmap;
	private String mCurrentPhotoPath;

	private static final String JPEG_FILE_PREFIX = "IMG_";
	private static final String JPEG_FILE_SUFFIX = ".jpg";

	//TODO: This method *must* check whether the pic's directory in the app's data folder exists.
	// If this isn't the case, the method *must* create the pic's directory.
	private File getPhotoDir() {
		File storageDir = new File(Environment.getExternalStorageDirectory() + "/Warranty");
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			if (storageDir != null) {
				if (!storageDir.mkdirs()) {
					if (!storageDir.exists()) {
						Log.d("getPhotoDir()", "failed to create directory");
						return null;
					}
				}
			}
		} else {
			Log.v(getString(R.string.app_name),"External storage is not mounted READ/WRITE.");
		}
		return storageDir;
	}

	private File createImageFile() throws IOException {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
		File albumF = getPhotoDir();
		File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
		return imageF;
	}

	private void galleryAddPic() {
		Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
		File f = new File(mCurrentPhotoPath);
		Uri contentUri = Uri.fromFile(f);
		mediaScanIntent.setData(contentUri);
		this.sendBroadcast(mediaScanIntent);
	}

	private void dispatchTakePictureIntent(int actionCode) {

		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		switch (actionCode) {
		case ACTION_TAKE_PHOTO_B:
			File f;

			try {
				f = createImageFile();
				mCurrentPhotoPath = f.getAbsolutePath();
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,	Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		startActivityForResult(takePictureIntent, actionCode);
	}


	Button.OnClickListener mTakePicOnClickListener = new Button.OnClickListener() {
//		@Override
		public void onClick(View v) {
			dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
		}
	};


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card);

		mImageView = (ImageView) findViewById(R.id.imageView1);
		mImageBitmap = null;
		dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case ACTION_TAKE_PHOTO_B: {
				if (resultCode == RESULT_OK) {
					Bundle imagePath = new Bundle();
					imagePath.putString("path", mCurrentPhotoPath);
					Intent intent = new Intent(PhotoActivity.this, ch.zhaw.warranty.CardActivity.class);
					intent.putExtra("path",mCurrentPhotoPath);
					intent.putExtra("status","new");
					startActivity(intent);
				}
				break;
			} 
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, mImageBitmap);
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY, (mImageBitmap != null));
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mImageBitmap = savedInstanceState.getParcelable(BITMAP_STORAGE_KEY);
		mImageView.setImageBitmap(mImageBitmap);
		mImageView.setVisibility(savedInstanceState
						.getBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY) ? ImageView.VISIBLE
						: ImageView.INVISIBLE);
	}
}