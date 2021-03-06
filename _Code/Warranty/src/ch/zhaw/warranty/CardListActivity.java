package ch.zhaw.warranty;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import ch.zhaw.warranty.card.WarrantyCard;
import ch.zhaw.warranty.database.TBLWarrantyConnector;

public class CardListActivity extends ListActivity {
	private ArrayAdapter<WarrantyCard> arrayAdapter;
	private ListView list;
	private CheckBox cbExpired;
	private boolean showExpired;
	public static TBLWarrantyConnector tblwarranty;
	private String order;


	/* (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_card_list, menu);
        return true;
    }
    
	/* (non-Javadoc)
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
	    case R.id.cardlist_menu_exit:
	    	moveTaskToBack(true);
	    	break;
	    case R.id.cardlist_menu_DeleteAll:
	    	tblwarranty.deleteCard(0);
	    	updateView();
	    }
		return true;
	}
	
	/**
	 * Determines the clicked button and executes the correct method
	 * 
	 * @param view	current view	
	 */
	public void onClick(View view) {
	        switch (view.getId()) {
	        case R.id.cardlist_BTnewcard:
	        	startActivity(new Intent(CardListActivity.this, ch.zhaw.warranty.photo.PhotoActivity.class));
	        	break;
	        case R.id.BTsort:
	        	getOrder();
	        	break;
	        }
	      }
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle saveInstanceState) {
   		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_card_list);
		tblwarranty = new TBLWarrantyConnector(this);
		cbExpired = (CheckBox) findViewById(R.id.cardlist_CBexpired);
		order = "title";
		list = getListView();
		updateView();

		
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				  Intent intent = new Intent(CardListActivity.this, CardActivity.class);
				  WarrantyCard card = (WarrantyCard) list.getItemAtPosition(position);
				  intent.putExtra("id", card.get_id());
				  intent.putExtra("status", "edit");
				  startActivity(intent);
				  updateView();
 			  }
			});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				final WarrantyCard card = (WarrantyCard) list.getItemAtPosition(position);
		        AlertDialog.Builder adb=new AlertDialog.Builder(CardListActivity.this);
		        adb.setTitle(getString(R.string.delete) + "?");
		        adb.setMessage(getString(R.string.ask_for_deletion_pre) + card.getTitle() + getString(R.string.ask_for_deletion_post) + "?");
		        adb.setNegativeButton(getString(R.string.cancel), null);
		        adb.setPositiveButton(getString(R.string.okay), new AlertDialog.OnClickListener() {
		            public void onClick(DialogInterface dialog, int which) {
		            	tblwarranty.deleteCard(card.get_id());
		            	updateView();
		            	Toast.makeText(getApplicationContext(), card.getTitle() + getString(R.string.deleted),Toast.LENGTH_LONG).show();
		            }}); 
		        adb.show();
				return true;
				}
		});		
		cbExpired.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				showExpired = cbExpired.isChecked();
				updateView();
			}
		});
	}
	
	/**
	 * Opens an alert dialog containing possible sorting options
	 */
	private void getOrder() {
		final CharSequence[] items = {getString(R.string.title), getString(R.string.description), getString(R.string.created_at) };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select order");
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	switch (item) {
				case 0:
					order="title";
					break;
				case 1:
					order="description";
					break;
				case 2:
					order="created_at";
					break;
				default:
					order="title";
					break;
				}
		    	updateView();
		        dialog.dismiss();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	

	/**
	 * updates the list view each time it is called
	 */
	private void updateView(){
		ArrayList<WarrantyCard> cards = tblwarranty.getAllCardsOrdered(order, showExpired);
		arrayAdapter = new ArrayAdapter<WarrantyCard>(this, android.R.layout.simple_list_item_1,cards);		
		setListAdapter(arrayAdapter);
	}
}
