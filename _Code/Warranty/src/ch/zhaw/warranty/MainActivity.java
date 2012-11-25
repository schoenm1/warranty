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
			startActivity(new Intent(MainActivity.this, ch.zhaw.warranty.CardActivity.class));
			break;
		case R.id.BTListWarrantyCards:
			startActivity(new Intent(MainActivity.this,	ch.zhaw.warranty.CardListActivity.class));
			break;
		case R.id.BTTakePicture:
			startActivity(new Intent(MainActivity.this, ch.zhaw.warranty.photo.PhotoIntentActivity.class));
			break;
		case R.id.BTDeleteAll:
			deleteAllCards();
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
