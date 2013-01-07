package ch.zhaw.warranty.photo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import ch.zhaw.warranty.R;

public class PhotoDisplayActivity extends Activity {
	private Bitmap bitmap;


	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_display);
		
		Bundle extras = getIntent().getExtras();
		String imgPath = extras.getString("imgpath");        	
		
		ImageView fullscreendisplay = (ImageView) findViewById(R.id.fullscreendisplay);
//		bitmap = BitmapFactory.decodeFile(imgPath);
//    	fullscreendisplay.setImageBitmap(bitmap);
		fullscreendisplay.setImageBitmap(BitmapLoader.loadBitmap(imgPath, 250, 250));
    	
    	fullscreendisplay.setOnClickListener(new OnClickListener() {
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			public void onClick(View v) {
				bitmap = null;
				finish();
			}
		});
   	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//no menu in this activity
		return false;
	}
}

	class BitmapLoader {
		public static int getScale(int originalWidth,int originalHeight,final int requiredWidth,final int requiredHeight) {

			int scale=1;
  			if((originalWidth>requiredWidth) || (originalHeight>requiredHeight)) {
				if(originalWidth<originalHeight) {
					scale=Math.round((float)originalWidth/requiredWidth);
				} else {
					scale=Math.round((float)originalHeight/requiredHeight);
				}
			}
			return scale;
		}
	  
		public static BitmapFactory.Options getOptions(String filePath, int requiredWidth,int requiredHeight) {   
			BitmapFactory.Options options=new BitmapFactory.Options();
			options.inJustDecodeBounds=true;
			BitmapFactory.decodeFile(filePath,options);
			options.inSampleSize=getScale(options.outWidth,options.outHeight, requiredWidth, requiredHeight);
			options.inJustDecodeBounds=false;
			return options;
		}

		public static Bitmap loadBitmap(String filePath, int requiredWidth,int requiredHeight) {
			BitmapFactory.Options options= getOptions(filePath,requiredWidth, requiredHeight);
			return BitmapFactory.decodeFile(filePath,options);
		}
	}

