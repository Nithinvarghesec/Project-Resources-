package WebServices;

import java.net.URLConnection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;


import DB.DB;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;



public class Web_Req_Asyncfor_GetFeedback_Experience extends	AsyncTask<String[], Void, String> {
	String[] values;
	ProgressDialog pdlg;
	public String xml_result_set = "";
	public String passingresult = "";
	public  Context context;
	JSONObject jsonObj ;
	String Filenamepart="";
	DB Obj_Db = new DB(context) ;


	public Web_Req_Asyncfor_GetFeedback_Experience(Context ctx) {
		this.context=ctx;
	}

	@Override
	protected String doInBackground(String[]... params) {	


		if (checkNetwork()) {
			WebserviceCall com = new WebserviceCall(context); 
			passingresult=com.GetFeedback_Experience();	


		} else {
			passingresult = "networkError";
			return passingresult;
		}

		return passingresult;

	}

	@SuppressLint("ShowToast")
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);		
		Log.i("onPostExecute", "result : " + result);		
		//Toast.makeText(context, ": JSON" +result, 100).show();
		if(result.equalsIgnoreCase("networkError")){

		}else {
			
			
			
			new AlertDialog.Builder(context)
			.setTitle("DAtAAAAAAA")
			.setMessage(result)
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					// continue with delete
				}
			})
			.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) { 
					// do nothing
				}
			})
			.show();
			



			try {

				JSONObject reader = new JSONObject(result);			
				JSONObject jsonEstibyanDATA = reader.getJSONObject("EstibyanDATA");
				//JSONObject Head  = jsonEstibyanDATA.getJSONObject("Header");
				JSONObject Result  = jsonEstibyanDATA.getJSONObject("Result");		
				JSONArray Experience  = Result.optJSONArray("Experience");
				
				
				Obj_Db.open();
				Obj_Db.delete_table(DB.TBL_GetFeedback_Experience);
				Obj_Db.close();
				
				
				
				if (Experience instanceof JSONArray) {		
					System.out.println("jsonArray.length() "+Experience.length());
					//ExperienceArray = new String [Experience.length()];
					for(int i=0; i < Experience.length(); i++){
						JSONObject jsonObject = Experience.getJSONObject(i);
						String PK_Exp_ID = jsonObject.optString("PK_Exp_ID").toString();
						String Text_En = jsonObject.optString("Text_En").toString();
						String Text_Ar = jsonObject.optString("Text_Ar").toString();
						String Message_En = jsonObject.optString("Message_En").toString();
						String Message_Ar = jsonObject.optString("Message_Ar").toString();
						String TitleMessage_En = jsonObject.optString("TitleMessage_En").toString();
						String TitleMessage_Ar = jsonObject.optString("TitleMessage_Ar").toString();
						String FK_MasterTypeID = jsonObject.optString("FK_MasterTypeID").toString();
						String ImagePath = jsonObject.optString("ImagePath").toString();
						String Seq_Order = jsonObject.optString("Seq_Order").toString();
						String SubCatID = jsonObject.optString("SubCatID").toString();
						
						
						Obj_Db.open();
						Obj_Db.insertRow(new String[]{
								DB.PK_Exp_ID,
								DB.Text_En,
								DB.Text_Ar,
								DB.Message_En,
								DB.Message_Ar,
								DB.TitleMessage_En,
								DB.TitleMessage_Ar,
								DB.FK_MasterTypeID,
								DB.ImagePath,
								DB.Seq_Order,
								DB.SubCatID
								
						}
						, new String[]{
								
								PK_Exp_ID,
								Text_En,
								Text_Ar,
								Message_En,
								Message_Ar,
								TitleMessage_En,
								TitleMessage_Ar,
								FK_MasterTypeID,
								ImagePath,
								Seq_Order,
								SubCatID
								
						}, DB.TBL_GetFeedback_Experience);
						
						Obj_Db.close();
						
						if(i == (Experience.length()-1)){
							
							System.out.println("jsonArray.length() = i ; So next loop" );
							
							Obj_Db.open();
							String[] SubCategory_IDarray = Obj_Db.getUserData(DB.TBL_GetFeedback_Experience, DB.SubCatID);
							Obj_Db.close();
							
							
							System.out.println("SubCategory_ID.length() =  " +SubCategory_IDarray.length );

							Obj_Db.open();
							Obj_Db.delete_table(DB.TBL_GetFeedback_Elements);
							Obj_Db.close();
							
							
							Web_Req_Asyncfor_GetFeedback_Elements  OBjWeb_Req_Web_Req_Asyncfor_GetFeedback_Elements = new Web_Req_Asyncfor_GetFeedback_Elements(context);
							OBjWeb_Req_Web_Req_Asyncfor_GetFeedback_Elements.execute(SubCategory_IDarray);
						}


					}
				}else{
					
					//String PK_Survey_ID = jsonEstibyanDATA.getString("Response").toString();		
					//Toast.makeText(context, "AppN1: "+PK_Survey_ID, 100).show();
				}
				
				
			}catch (Exception e) {
				// TODO Auto-generated catch block
				Toast.makeText(context, "Error on downloading : ", 100).show(); 
				System.out.println("Response from server Number Exception");
				e.printStackTrace();
			}
		
				
			

		}


	}



	@SuppressLint("ShowToast")
	protected boolean checkNetwork() {
		Boolean connection_status_flag = false;

		try {

			java.net.URL myUrl = new java.net.URL(
					"http://83.111.125.205/somwebserviceTEST/");
			URLConnection connection = myUrl.openConnection();
			connection.setConnectTimeout(2000);
			connection.connect();
			connection_status_flag = true;
		} catch (Exception e) {
			Log.w("checkNetwork", e.toString());
			connection_status_flag = false;
		}
		return connection_status_flag;
	}



}
