package DB;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DB {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Estibyan_ADAC_WR.db";


	public static final String Img_path_folder = Environment.getExternalStorageDirectory().toString()+"/ADAC-FOLDER/";
	
	// Tables name
	public static final String TBL_URL = "TBL_URL";
	//public static final String TBL_GetFeedback_MainCategory = "TBL_GetFeedback_MainCategory"; 
	public static final String TBL_GetFeedback_Experience = "TBL_GetFeedback_Experience"; 
	public static final String TBL_GetFeedback_Elements= "TBL_GetFeedback_Elements";
	
	
	
	
	
	// Table Columns TBL_URL 
	
	public static final String Currentdate = "currentdate";
	public static final String URL = "URL";

	
	

// Table Columns TBL_GetFeedback_MainCategory
	
//	public static final String PK_MainCat_ID = "PK_MainCat_ID";
//	public static final String Category_En = "Category_En";
//	public static final String Category_Ar = "Category_Ar";
	public static final String ImagePath = "ImagePath";
	
	
	
	
	// Table Columns TBL_GetFeedback_Experience
	
	public static final String PK_Exp_ID = "PK_Exp_ID";
	public static final String Text_En = "Text_En";
	public static final String Text_Ar = "Text_Ar";
	public static final String Message_En = "Message_En";
	public static final String Message_Ar = "Message_Ar";
	public static final String TitleMessage_En = "TitleMessage_En";
	public static final String TitleMessage_Ar = "TitleMessage_Ar";
	public static final String FK_MasterTypeID = "FK_MasterTypeID";
	//public static final String ImagePath = "ImagePath";
	public static final String Seq_Order = "Seq_Order";  
	public static final String SubCatID = "SubCatID"; 
	
	
	
	
	// Table Columns TBL_GetFeedback_Elements
	
	public static final String PK_Element_ID = "PK_Element_ID";
	public static final String FK_SubCategory_ID = "FK_SubCategory_ID";
	public static final String Element_En = "Element_En";
	public static final String Element_Ar = "Element_Ar";
	//public static final String ImagePath = "ImagePath";

	
	
	

	
	private static final String CREATE_TBL_URL= "CREATE TABLE "
			+ TBL_URL + "(" 
			+ Currentdate+ " VARCHAR(100), "  
			+ URL+ " VARCHAR(100))";
	
	
	

			/*private static final String CREATE_TBL_GetFeedback_MainCategory= "CREATE TABLE "
					+TBL_GetFeedback_MainCategory + "(" 
					+PK_MainCat_ID+ " VARCHAR(100), "  
					+Category_En+ " VARCHAR(100), "
					+Category_Ar+ " VARCHAR(100), "
					+ImagePath+ " VARCHAR(100))";*/
			
			
	
			
			
			private static final String CREATE_TBL_GetFeedback_Experience= "CREATE TABLE "
					+TBL_GetFeedback_Experience + "(" 
					+PK_Exp_ID+ " VARCHAR(100), "  
					+Text_En+ " VARCHAR(100), "
					+Text_Ar+ " VARCHAR(100), "
					+Message_En+ " VARCHAR(100), "
					+Message_Ar+ " VARCHAR(100), "
					+TitleMessage_En+ " VARCHAR(100), "
					+TitleMessage_Ar+ " VARCHAR(100), "
					+FK_MasterTypeID+ " VARCHAR(100), "		
					+ImagePath+ " VARCHAR(100), "	
					+Seq_Order+ " VARCHAR(100),"
					+SubCatID+ " VARCHAR(100))";	
			
			
			
			
			
			private static final String CREATE_TBL_GetFeedback_Elements= "CREATE TABLE "
					+TBL_GetFeedback_Elements + "(" 
					+PK_Element_ID+ " VARCHAR(100), "  
					+FK_SubCategory_ID+ " VARCHAR(100), "
					+Element_En+ " VARCHAR(100), "
					+Element_Ar+ " VARCHAR(100), "
					+ImagePath+ " VARCHAR(100))";
			
			
			



	private DbHelper dbHelper;
	private SQLiteDatabase mdb;
	private static Context ctx;

	public DB(Context ctx) {
		DB.ctx = ctx;
	}

	public static class DbHelper extends SQLiteOpenHelper {

		public DbHelper(Context ctx) {
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			
			db.execSQL(CREATE_TBL_URL);
			//db.execSQL(CREATE_TBL_GetFeedback_MainCategory);	
			db.execSQL(CREATE_TBL_GetFeedback_Experience);
			db.execSQL(CREATE_TBL_GetFeedback_Elements);
			
			
			
			Log.e(CREATE_TBL_URL, "CREATE_TBL_URL table created Successfully");
			Log.e(CREATE_TBL_GetFeedback_Experience, " CREATE_TBL_GetFeedback_Experience table created Successfully");
			//Log.e(CREATE_TBL_GetFeedback_MainCategory, " CREATE_TBL_GetFeedback_MainCategory table created Successfully");
			Log.e(CREATE_TBL_GetFeedback_Elements, " CREATE_TBL_GetFeedback_Elements table created Successfully");
			
		

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}

	public DB open() throws SQLException {
		dbHelper = new DbHelper(ctx);
		mdb = dbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		dbHelper.close();
	}

	public void delete_table(String table) {
		mdb.delete(table, null, null);

	}

	/*** Inserts row in table, returns 1 if inserted, else -1 ***/

	public long insertRow(String[] columns, String[] data, String table) {

		ContentValues values = new ContentValues();

		int counter = columns.length;
		if (counter > data.length) {
			Log.i("insertRow", "FAILED to insert (missmatch data pblm) in  "
					+ table);
			return -1;
		}
		for (int i = 0; i < counter; i++) {
			values.put(columns[i], data[i]);
		}

		if (mdb.insert(table, null, values) < 0) {
			Log.e("insertRow", "FAILED to insert (Inserting time pblm) in "
					+ table);
			return -1;
		} else {
			Log.i("insertRow", "row inserted in " + table);
			return 1;
		}

	}





	/********************************* GetUserData in any way **************************************/
	public List<String[]> getData(String table, String[] columns,
			String whereClause, String orderBy) throws SQLException {

		Cursor c = mdb.query(table, columns, whereClause, null, null, // TODO
				null, orderBy);

		c.moveToFirst();
		List<String[]> data = new ArrayList<String[]>();

		if (c != null) {
			for (int i = 0; i < c.getCount(); i++) { // iterate rows

				String[] row = new String[c.getColumnCount()];
				for (int j = 0; j < c.getColumnCount(); j++) {// iterate columns

					row[j] = c.getString(c.getColumnIndexOrThrow(columns[j]));
				}
				data.add(row);
				c.moveToNext();
			}
		}
		c.close();
		return data;
	}

	/********************************* GetUserData WITH raWQUERY **************************************/


	/*** Delete rows ***/
	public void deleteRows(String table, String whereClause) {


		Log.i("mDb deleteRows from table : " + table +"          "+whereClause, mdb.delete(table, whereClause, null) + " rows deleted ");

	}


	/********************************* GetUserData WITH raWQUERY **************************************/
	public List<String[]> getUserData(String query) throws SQLException {

		Log.v("GetUserData", "qry : " + query);

		Cursor c = mdb.rawQuery(query, null);

		Log.v("GetUserData", "2 : ");
		c.moveToFirst();

		List<String[]> data = new ArrayList<String[]>();

		if (c != null) {
			for (int i = 0; i < c.getCount(); i++) { // iterate rows

				String[] row = new String[c.getColumnCount()];
				for (int j = 0; j < c.getColumnCount(); j++) {// iterate columns

					row[j] = c.getString(j);
					// Log.i("DB value", row[j]);
				}
				data.add(row);
				c.moveToNext();
			}
		}
		c.close();

		Log.i("getUserData", data.size() + " rows returned");
		return data;
	}



	///////////////////////////////

	public String[] getUserData(String table, String column)
			throws SQLException {

		Cursor c = mdb.query(table, new String[] { column }, null, null, null,
				null, null);

		c.moveToFirst();
		String[] x = new String[c.getCount()];
		if (c != null) {
			for (int i = 0; i < c.getCount(); i++) {
				x[i] =  c.getString(c.getColumnIndexOrThrow(column));
				if (c.moveToNext()) {

				}
			}
		}
		c.close();
		return x;
	}
	
	
	
	
	public String[] getUserData_Exprerince(String table, String column)
			throws SQLException {

		Cursor c = mdb.query(table, new String[] { column }, null, null, null,
				null, null);

		c.moveToFirst();
		String[] x = new String[c.getCount()];
		if (c != null) {
			for (int i = 0; i < c.getCount(); i++) {
				x[i] =  Img_path_folder+c.getString(c.getColumnIndexOrThrow(column))+".png";
				if (c.moveToNext()) {

				}
			}
		}
		c.close();
		return x;
	}


}
