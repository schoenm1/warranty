package ch.zhaw.warranty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import ch.zhaw.tools.DatePickerFragment;
import ch.zhaw.warranty.card.WarrantyCard;
import ch.zhaw.warranty.database.TBLWarrantyConnector;

public class CardActivity extends FragmentActivity {
	private EditText tbtitle,tbdesc,tbcreatedat,tbvalidtil,tbprice,tbreseller;
	private TBLWarrantyConnector tblwarranty;
	private int id;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
		tblwarranty = new TBLWarrantyConnector(this);
        tbtitle = (EditText) findViewById(R.id.card_TBtitle);
        tbdesc = (EditText) findViewById(R.id.card_TBdesc);
        tbcreatedat = (EditText) findViewById(R.id.card_TBcreatedAt);
        tbvalidtil = (EditText) findViewById(R.id.card_TBvalidTil);
        tbprice = (EditText) findViewById(R.id.card_TBprice);
        tbreseller = (EditText) findViewById(R.id.card_TBreseller);
        
      	Bundle extras = getIntent().getExtras();
      	id = (extras != null) ? extras.getInt("id") : 0;
        if (id != 0 ) {
        	WarrantyCard card = tblwarranty.getWarrantyCard(id);
        	tbtitle.setText(card.getTitle());
            tbdesc.setText(card.getDescription());
            tbcreatedat.setText(card.getCreatedAt());
            tbvalidtil.setText(card.getValidUntil());
            tbprice.setText(card.getPrice());
            tbreseller.setText(card.getReseller());
        }
       
//        tbcreatedat.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//			
//			public void onFocusChange(View v, boolean state) {
//				// TODO Auto-generated method stub
//			       DialogFragment newFragment = new DatePickerFragment();
//			        newFragment.show(getSupportFragmentManager(), "datePicker");
//			}
//		});
    }
    
     @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_card, menu);
        return true;
    }
    
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.card_BTadd:
        	createNewCard();
        	break;
        case R.id.card_BTClear:
        	clearAllFields();
        	break;
        case R.id.button1:
        	showDatePicker(view);
        	break;
        }
      }
    
    /**
     * grabs text from input fields and creates a new card
     */
    private void createNewCard() {
    	//Note: 0 is a dummy _id. This will be overwritten by auto increment of sqlite
    	WarrantyCard card = new WarrantyCard(id,tbtitle.getText().toString(), 
    			tbdesc.getText().toString(), "/foobar/", tbcreatedat.getText().toString(), 
    			tbvalidtil.getText().toString(), tbprice.getText().toString(), tbreseller.getText().toString());
    	MainActivity.tblwarranty.insertWarrantyCard(card);
    	startActivity(new Intent(this, MainActivity.class));
//    	clearAllFields();
//    	//TODO: listAllCards() - testing only.
//    	listAllCards();
    }
    
    /**
     * Clears all fields in the CardActivity.
     */
    private void clearAllFields() {
    	//TODO: there is probably a nicer way to clear all fields... 
    	tbtitle.setText("");
    	tbdesc.setText("");
    	tbcreatedat.setText("");
    	tbvalidtil.setText("");
    	tbprice.setText("");
    	tbreseller.setText("");
    	System.out.println("ahoi");
    }
    
    /**
     * currently only syso's all cards
     */
//    private void listAllCards() { 
//    	ArrayList<WarrantyCard> cards = MainActivity.tblwarranty.getAllCards();
//    	
//    	for (WarrantyCard card : cards) {
//			System.out.println("Card title is: " + card.getTitle());
//		}
//    }
    
    private void showDatePicker(View v) {
    	DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
