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
import android.widget.Toast;
import ch.zhaw.warranty.card.WarrantyCard;

public class CardListActivity extends ListActivity {
	private ArrayAdapter<WarrantyCard> arrayAdapter;
	private ListView list;
	private ArrayList<WarrantyCard> cards;
	
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
//				Intent myIntent = new Intent(CardListActivity.this, EditCard.class);
				  Intent intent = new Intent(CardListActivity.this, CardActivity.class);
//				  intent.putExtra("WarrantyCard", list.getItemAtPosition(position));
				  System.out.println("****" + id );
				  intent.putExtra("id", id );
				  startActivity(intent);
				  WarrantyCard asdf = (WarrantyCard) list.getItemAtPosition(position);
				  System.out.println(asdf.getTitle() + " WITH ID "+ asdf.get_id());


			  }
			});
		
		list.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(getApplicationContext(),
					      "Opening Number " + position, Toast.LENGTH_LONG).show();
				return false;
				}
		});
		
		ArrayList<WarrantyCard> cards = MainActivity.tblwarranty.getAllCards();
		arrayAdapter = new ArrayAdapter<WarrantyCard>(this, android.R.layout.simple_list_item_1,cards);		
		setListAdapter(arrayAdapter);
		
	}
}
