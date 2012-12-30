package ch.zhaw.warranty.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import ch.zhaw.warranty.card.WarrantyCard;

/**
 * This class manages all calls to the database table 'warranty'
 */
public class TBLWarrantyConnector {
	private SQLiteDatabase db;
	private SQLiteOpenHelper dbHelper;
	
	public TBLWarrantyConnector(Context context) {
		dbHelper = new TBLWarrantyHelper(context,TBLWarrantyHelper.TBL_NAME,null,1);
	}
	
	/**
	 * Opens an r/w connection to the sqlite db
	 * @throws SQLException
	 */
	private void openDB() throws SQLException {
		db = dbHelper.getWritableDatabase();
	}
	
	/**
	 * closes the connection to the sqlite db
	 */
	private void closeDB() {
		if (db != null) {
			db.close();
		}
	}
	
	/**
	 * Creates a new warranty card or updates an existing one.
	 * If the cardID is 0, a new card will be created. Otherwise the card with the given ID will be updated.
	 * 
	 * @param card	card to create / to update
	 */
	public void insertWarrantyCard(WarrantyCard card){
		ContentValues values = new ContentValues();
		values.put(TBLWarrantyHelper.CLMN_TITLE, card.getTitle());
		values.put(TBLWarrantyHelper.CLMN_DESC, card.getDescription());
		values.put(TBLWarrantyHelper.CLMN_IMGPATH, card.getImagePath());
		values.put(TBLWarrantyHelper.CLMN_CREATEDAT, card.getCreatedAt());
		values.put(TBLWarrantyHelper.CLMN_VLDTIL, card.getValidUntil());
		values.put(TBLWarrantyHelper.CLMN_PRICE, card.getPrice());
		values.put(TBLWarrantyHelper.CLMN_RESSELLER, card.getReseller());
		System.out.println(card.getImagePath());
		Log.v("foo", card.getImagePath());

		openDB();
			if (card.get_id() == 0) {
				db.insert(TBLWarrantyHelper.TBL_NAME, null, values);
			} else {
				db.update(TBLWarrantyHelper.TBL_NAME, values, TBLWarrantyHelper.CLMN_ID + "=" + card.get_id(), null);
			}
		closeDB();
	}

//	/**
//	 * Updates a particular warranty card
//	 *
//	 * @param card	updated warranty card
//	 */
//	public void updateWarrantyCard(WarrantyCard card) {
//		ContentValues values = new ContentValues();
//		values.put(TBLWarrantyHelper.CLMN_TITLE, card.getTitle());
//		values.put(TBLWarrantyHelper.CLMN_DESC, card.getDescription());
//		values.put(TBLWarrantyHelper.CLMN_CREATEDAT, card.getCreatedAt());
//		values.put(TBLWarrantyHelper.CLMN_VLDTIL, card.getValidUntil());
////		values.put(TBLWarrantyHelper.CLMN_PRICE, card.getPrice());
//		values.put(TBLWarrantyHelper.CLMN_RESSELLER, card.getReseller());
//		
//		openDB();
//			db.update(TBLWarrantyHelper.TBL_NAME, values, "_id="+card.get_id(), null);
//		closeDB();
//	}
	
	/**
	 * Returns all warranty cards in an unordered ArrayList
	 * 
	 * @return	all saved warranty cards
	 */
	public ArrayList<WarrantyCard> getAllCards() {
		System.out.println("list all cards");
		openDB();

		ArrayList<WarrantyCard> cards = new ArrayList<WarrantyCard>();
		Cursor cursor = db.query(TBLWarrantyHelper.TBL_NAME, null , null, null, null, null,TBLWarrantyHelper.CLMN_TITLE);
		cursor.moveToFirst();

		while(!cursor.isAfterLast()) {
			cards.add(db2Card(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		closeDB();
		return cards;
	}
	
	/**
	 * Returns all warranty cards as a ordered ArrayList
	 * 
	 * @param order		sort criteria
	 * @return			ordered ArrayList
	 */
	public ArrayList<WarrantyCard> getAllCardsOrdered(String order) {
		if (order.matches("^title$")) {
			order = TBLWarrantyHelper.CLMN_TITLE;
		} else if (order.matches("^description$")) {
			order = TBLWarrantyHelper.CLMN_DESC;
		} else if (order.matches("^price$")) {
			order = TBLWarrantyHelper.CLMN_PRICE;
		} else if (order.matches("^reseller$")) {
			order = TBLWarrantyHelper.CLMN_RESSELLER;
		} else if (order.matches("^created_at$")) {
			order = TBLWarrantyHelper.CLMN_CREATEDAT;
		} else if (order.matches("^valid_until$")) {
			order = TBLWarrantyHelper.CLMN_VLDTIL;
		} else {
			order = TBLWarrantyHelper.CLMN_TITLE;			
		}
		
		System.out.println("list all cards ordered by" + order);
		openDB();

		ArrayList<WarrantyCard> cards = new ArrayList<WarrantyCard>();
		Cursor cursor = db.query(TBLWarrantyHelper.TBL_NAME, null , null, null, null, null,order);
		cursor.moveToFirst();

		while(!cursor.isAfterLast()) {
			cards.add(db2Card(cursor));
			cursor.moveToNext();
		}
		cursor.close();
		closeDB();
		return cards;		
	}
	
	/**
	 * Reads the current entry from the database and creates a warranty card from it
	 * 
	 * @param cursor	current position in the database
	 * @return			Warranty card of the current database position
	 */
	private WarrantyCard db2Card(Cursor cursor) {
		return new WarrantyCard(cursor.getInt(cursor.getColumnIndex(TBLWarrantyHelper.CLMN_ID)),
				cursor.getString(cursor.getColumnIndex(TBLWarrantyHelper.CLMN_TITLE)), 
				cursor.getString(cursor.getColumnIndex(TBLWarrantyHelper.CLMN_DESC)),
				cursor.getString(cursor.getColumnIndex(TBLWarrantyHelper.CLMN_IMGPATH)),
				cursor.getString(cursor.getColumnIndex(TBLWarrantyHelper.CLMN_CREATEDAT)),
				cursor.getString(cursor.getColumnIndex(TBLWarrantyHelper.CLMN_VLDTIL)),
				cursor.getString(cursor.getColumnIndex(TBLWarrantyHelper.CLMN_PRICE)),
				cursor.getString(cursor.getColumnIndex(TBLWarrantyHelper.CLMN_RESSELLER))
				);
	}
	
	/**
	 * Returns a warranty card with a particular ID
	 * 
	 * @param cardID	ID of the warranty card that should be returned
	 * @return			Warranty card with given ID
	 */
	public WarrantyCard getWarrantyCard(int cardID) {
		System.out.println("getting card with id "+ cardID + " for you");
		WarrantyCard card; 
		card = new WarrantyCard(0,"dummy", "dummy", "dummy", "dummy", "dummy", "dummy", "dummy");
		
		openDB();
		Cursor cursor = db.query(TBLWarrantyHelper.TBL_NAME, null , TBLWarrantyHelper.CLMN_ID + "=" + cardID , null, null, null, null);
		cursor.moveToFirst();
		
		if (! cursor.isAfterLast()) {
			card = db2Card(cursor);
		}
		cursor.close();
		closeDB();
		return card;
	}
	/**
	 * Deletes all saved warranty cards from the database
	 */
	public void deleteAllCards() {
		openDB();
		db.delete(TBLWarrantyHelper.TBL_NAME, null, null);
		closeDB();
	}
	
	/**
	 * Deletes a warranty card from the database
	 * 
	 * @param cardID	id of the card which should be deleted.
	 */
	public void deleteCard(int cardID) {
		openDB();
		db.delete(TBLWarrantyHelper.TBL_NAME, "_id = " + cardID, null);
		closeDB();
	}
}
