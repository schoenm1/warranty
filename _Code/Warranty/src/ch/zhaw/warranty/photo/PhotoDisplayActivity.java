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
		Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
    	fullscreendisplay.setImageBitmap(bitmap);
    	
    	fullscreendisplay.setOnClickListener(new OnClickListener() {
			/* (non-Javadoc)
			 * @see android.view.View.OnClickListener#onClick(android.view.View)
			 */
			public void onClick(View v) {
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
