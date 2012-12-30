package ch.zhaw.warranty;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
	
    private Calendar createdAt;
    private Calendar validUntil;
    
    private Button activeDateButton;
    private Calendar activeDate;

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        createdAt = Calendar.getInstance();
        validUntil = Calendar.getInstance();

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
        	resetButtonText();
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
    
    /**
     * Displays an image in the imageView
     * 
     * @param imgPath	path to the image that should be displayed
     */
    private void showImage(String imgPath) {
    	ImageView warrantyImg = (ImageView) findViewById(R.id.card_ImageView);
    	Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
    	warrantyImg.setImageBitmap(bitmap);
    }
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_card, menu);
        return true;
    }
    
    /**
     * Determines the clicked button and executes the correct method
     *  
     * @param view	current view
     */
    public void onClick(View view) {
        switch (view.getId()) {
        case R.id.card_BTaddcard:
        	createNewCard();
        	break;
        case R.id.card_BTClear:
        	clearAllFields();
        	break;
        case R.id.card_BTcreatedat:
        	showDateDialog(btcreatedat, createdAt);
        	break;
        case R.id.card_BTvaliduntil:
        	showDateDialog(btvalidtil, validUntil);
        	break;
        }
      }
    
    /**
     * gets text from input fields and creates a new card
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
    	//seems that there is no nicer way to clear all input fields...
    	tbtitle.setText("");
    	tbdesc.setText("");
    	tbprice.setText("");
    	tbreseller.setText("");
    	resetButtonText();
    }

    /**
     * Sets the date of both buttons to today
     */
    private void resetButtonText() {
    	Calendar date = Calendar.getInstance();
    	updateButtonText(btcreatedat,date);
    	updateButtonText(btvalidtil, date);
    }
    
    /**
     * Shows a date picker which is related to the clicked button
     * 
     * @param dateButton	Button that was clicked
     * @param date			Date of clicked button
     */
    private void showDateDialog(Button dateButton, Calendar date) {
        activeDateButton = dateButton;
        activeDate = date;
        showDialog(0);
    }
    
    /**
     * Listens for clicked buttons in date picker dialog and executeds onDateSet methods.
     */
    private OnDateSetListener dateSetListener = new OnDateSetListener() {
		/* (non-Javadoc)
		 * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
		 */
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			activeDate.set(year, monthOfYear, dayOfMonth);
			updateButtonText(activeDateButton, activeDate);
		}
	};
    
	/**
	 * Updates a button's text
	 * 
	 * @param activeDateButton	Button to update
	 * @param activeDate		Date that should be displayed on the button
	 */
	private void updateButtonText(Button activeDateButton, Calendar activeDate) {
		activeDateButton.setText(activeDate.get(Calendar.DAY_OF_MONTH) + "." + activeDate.get(Calendar.MONTH -1) + "." + activeDate.get(Calendar.YEAR));
	}
	
    /* (non-Javadoc)
     * @see android.app.Activity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int id) {
    	return new DatePickerDialog(this, dateSetListener, activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
    }

    /* (non-Javadoc)
     * @see android.app.Activity#onPrepareDialog(int, android.app.Dialog)
     */
    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        ((DatePickerDialog) dialog).updateDate(activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
    }
    
}
