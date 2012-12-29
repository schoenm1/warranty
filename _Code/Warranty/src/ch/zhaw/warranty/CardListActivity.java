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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import ch.zhaw.warranty.card.WarrantyCard;
import ch.zhaw.warranty.database.TBLWarrantyConnector;

public class CardListActivity extends ListActivity {
	private ArrayAdapter<WarrantyCard> arrayAdapter;
	private ListView list;
	public static TBLWarrantyConnector tblwarranty;


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_card_list, menu);
        return true;
    }
    
	public boolean onOptionsItemSelected(MenuItem item) {
	    boolean handled = false;
		switch (item.getItemId()){
	    case R.id.cardlist_menu_exit:
	    	moveTaskToBack(true);
	    	break;
	    case R.id.cardlist_menu_DeleteAll:
	    	tblwarranty.deleteAllCards();
	    }
	    return handled;
	}
	
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
	
	@Override
	public void onCreate(Bundle saveInstanceState) {
   		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_card_list);
		tblwarranty = new TBLWarrantyConnector(this);
		list = getListView();
		
		list.setOnItemClickListener(new OnItemClickListener() {
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				  Intent intent = new Intent(CardListActivity.this, CardActivity.class);
				  WarrantyCard card = (WarrantyCard) list.getItemAtPosition(position);
				  intent.putExtra("id", card.get_id());
				  intent.putExtra("status", "edit");
				  startActivity(intent);
 			  }
			});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				WarrantyCard card = (WarrantyCard) list.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(), card.getTitle() + " deleted",Toast.LENGTH_LONG).show();
				tblwarranty.deleteCard(card.get_id());
				finish();
				startActivity(getIntent());
				return true;
				}
		});		
		setOrder("title");		
	}
	
	private void getOrder() {
		final CharSequence[] items = {getString(R.string.title), getString(R.string.description), getString(R.string.created_at) };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select order");
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	switch (item) {
				case 0:
					setOrder("title");
					break;
				case 1:
					setOrder("description");
					break;
				case 2:
					setOrder("created_at");
					break;
				default:
					setOrder("title");
					break;
				}
		        dialog.dismiss();
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();

	}
	
	private void setOrder(String order){
		ArrayList<WarrantyCard> cards = tblwarranty.getAllCardsOrdered(order);
		arrayAdapter = new ArrayAdapter<WarrantyCard>(this, android.R.layout.simple_list_item_1,cards);		
		setListAdapter(arrayAdapter);
		
		
	}
}
