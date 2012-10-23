package ch.zhaw.warranty.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TBLWarrantyHelper extends SQLiteOpenHelper {
	public static final String TBL_NAME="warranty";
	public static final String CLMN_ID = "_id";
	public static final String CLMN_TITLE = "title";
	public static final String CLMN_DESC = "description";
	public static final String CLMN_IMGPATH = "img_path";
	public static final String CLMN_CREATEDAT = "created_at";
	public static final String CLMN_VLDTIL = "valid_until";
	public static final String CLMN_PRICE = "price";
	public static final String CLMN_RESSELLER = "reseller";
	
	/**
	 * Creates the table 'warranty' 
	 * 
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public TBLWarrantyHelper(Context context, String name, CursorFactory factory, int version) {
		super(context,name,factory,version);
	}
	
	// DB creation statement
	private static final String DB_CREATE="create table " + TBL_NAME + " (" +
			CLMN_ID + " integer primary key autoincrement," + 
			CLMN_TITLE + " text," + 
			CLMN_DESC + " text," + 
			CLMN_IMGPATH + " text," + 
			CLMN_CREATEDAT + " text," +
			CLMN_VLDTIL + " text," +
			CLMN_PRICE + " real," +
			CLMN_RESSELLER + " text);";
	
	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DB_CREATE);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
		onCreate(db);
	}

}
