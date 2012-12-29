package ch.zhaw.warranty;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import ch.zhaw.warranty.card.WarrantyCard;
import ch.zhaw.warranty.database.TBLWarrantyConnector;

public class CardActivity extends FragmentActivity {
	private EditText tbtitle,tbdesc,tbprice,tbreseller;
	private Button btcreatedat,btvalidtil;
	private TBLWarrantyConnector tblwarranty;
	private int id;
	private String imgPath;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

		tblwarranty = new TBLWarrantyConnector(this);
        tbtitle = (EditText) findViewById(R.id.card_TBtitle);
        tbdesc = (EditText) findViewById(R.id.card_TBdesc);
        btcreatedat = (Button) findViewById(R.id.card_BTcreatedat);
        btvalidtil = (Button) findViewById(R.id.card_BTvaliduntil);
        tbprice = (EditText) findViewById(R.id.card_TBprice);
        tbreseller = (EditText) findViewById(R.id.card_TBreseller);
        
        Bundle extras = getIntent().getExtras();
        if (extras.getString("status").matches("new")) {
        	imgPath = extras.getString("path");
        	id = 0;
        } else {
        	id = extras.getInt("id");
        	WarrantyCard card = tblwarranty.getWarrantyCard(id);
        	tbtitle.setText(card.getTitle());
            tbdesc.setText(card.getDescription());
            btcreatedat.setText(card.getCreatedAt());
            btvalidtil.setText(card.getValidUntil());
            tbprice.setText(card.getPrice());
            tbreseller.setText(card.getReseller());
            imgPath = card.getImagePath();   
        }
        showImage(imgPath);
    }
    
    private void showImage(String imgPath) {
    	ImageView warrantyImg = (ImageView) findViewById(R.id.card_ImageView);
    	Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
    	warrantyImg.setImageBitmap(bitmap);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_card, menu);
        return true;
    }
    
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.card_BTaddcard:
        	createNewCard();
        	break;
        case R.id.card_BTClear:
        	clearAllFields();
        	break;
        case R.id.card_BTcreatedat:
        	showDatePicker(view);
        	break;
        case R.id.card_BTvaliduntil:
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
    			tbdesc.getText().toString(), imgPath, btcreatedat.getText().toString(), 
    			btvalidtil.getText().toString(), tbprice.getText().toString(), tbreseller.getText().toString());
    	tblwarranty.insertWarrantyCard(card);
    	startActivity(new Intent(this, CardListActivity.class));
    }
    
    /**
     * Clears all fields in the CardActivity.
     */
    private void clearAllFields() {
    	//TODO: there is probably a nicer way to clear all fields... 
    	tbtitle.setText("");
    	tbdesc.setText("");
    	btcreatedat.setText("");
    	btvalidtil.setText("");
    	tbprice.setText("");
    	tbreseller.setText("");
    }
        
    private void showDatePicker(View v) {
    	DialogFragment datePickerFragment = new DatePickerFragment();
    	datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }
    
    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    	@Override
    	public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
    	}

    	public void onDateSet(DatePicker view, int year, int month, int day) {
    		System.out.println("Date was set! year : " + year + " month: " + month + " day: "+ day);
    		Button button1 = (Button) findViewById(R.id.card_BTcreatedat);
    		button1.setText(day +"."+ month + "." + year);
    	}
    	
    }

    
}
