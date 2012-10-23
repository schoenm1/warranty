package ch.zhaw.warranty;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import ch.zhaw.warranty.database.TBLWarrantyConnector;
import ch.zhaw.warranty.R;

public class MainActivity extends Activity {

	public static TBLWarrantyConnector tblwarranty;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tblwarranty = new TBLWarrantyConnector(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.BTcreateNewWarrantyCard:
			Intent myIntent = new Intent(MainActivity.this, CardActivity.class);
			startActivity(myIntent);
			break;
		case R.id.BTListWarrantyCards:
			Intent myIntent2 = new Intent(MainActivity.this,
					CardListActivity.class);
			startActivity(myIntent2);
			break;
		case R.id.BTTakePicture:
			Intent myIntent3 = new Intent(MainActivity.this,
					ch.zhaw.warranty.photo.PhotoIntentActivity.class);
			startActivity(myIntent3);
			break;
		case R.id.BTDeleteAll:
			 Intent myIntent_test = new Intent(MainActivity.this,ch.zhaw.warranty.photo.PhotoIntentActivity.class);
			 startActivity(myIntent_test);
//			deleteAllCards();
			break;

		case R.id.BTQuit:
			this.finish();
			break;

		}

	}

	/**
	 * deletes all saved cards
	 */
	private void deleteAllCards() {
		tblwarranty.deleteAllCards();
		// listAllCards();
	}
}
