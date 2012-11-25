package ch.zhaw.warranty;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ch.zhaw.warranty.card.WarrantyCard;
import ch.zhaw.warranty.database.TBLWarrantyConnector;

public class CardListActivity extends ListActivity {
	private ArrayAdapter<WarrantyCard> arrayAdapter;
	private ListView list;
//	private ArrayList<WarrantyCard> cards;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_card_list, menu);
        return true;
    }
    
	
	@Override
	public void onCreate(Bundle saveInstanceState) {
   		super.onCreate(saveInstanceState);
		setContentView(R.layout.activity_card_list);
		list = getListView();
		
		
		list.setOnItemClickListener(new OnItemClickListener() {
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				  Intent intent = new Intent(CardListActivity.this, CardActivity.class);
				  WarrantyCard card = (WarrantyCard) list.getItemAtPosition(position);
				  intent.putExtra("id", card.get_id());
				  startActivity(intent);
 			  }
			});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				WarrantyCard card = (WarrantyCard) list.getItemAtPosition(position);
				return false;
				}
		});
		
		
		ArrayList<WarrantyCard> cards = MainActivity.tblwarranty.getAllCards();
		arrayAdapter = new ArrayAdapter<WarrantyCard>(this, android.R.layout.simple_list_item_1,cards);		
		setListAdapter(arrayAdapter);
		
	}
}
