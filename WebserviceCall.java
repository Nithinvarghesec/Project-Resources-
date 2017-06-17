package WebServices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import DB.DB;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


public class WebserviceCall {	
	String namespace = "http://tempuri.org/";
	public String url = "";
	public  Context context;
	String SOAP_ACTION;
	SoapObject request = null, objMessages = null;
	SoapSerializationEnvelope envelope;
	HttpTransportSE  androidHttpTransport;
	static Context ctx;



	 
	
	public WebserviceCall(Context ctx) {
		WebserviceCall.ctx=ctx;	
		

		try{
			DB obj_DB = new DB(ctx);
			obj_DB.open();
			String url_v=obj_DB.getUserData("SELECT * FROM " +DB.TBL_URL).get(0)[1];
			System.out.println("url is "+url_v);	
			url = url_v;
			obj_DB.close();
		}catch(Exception e){
			
		}
		
	}
	

	
	protected void SetEnvelope() {
		
		
		try {
			try {

				System.out.println("called called ");	
				System.out.println("called  url  "+url);
			} catch (Exception e) {
				System.out.println("called  url  Error ");

			}	
			envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.dotNet = true;			
			envelope.setOutputSoapObject(request);
			androidHttpTransport = new HttpTransportSE(url);
			androidHttpTransport.debug = true;

		} catch (Exception e) {
			System.out.println("Soap Exception---->>>" + e.toString());	
		}
	}
	
		// GetFeedback_Experience ()

	
		public String GetFeedback_Experience() 
		  {			
			try {
				SOAP_ACTION = namespace + "Execute";
         	    String dataString =
			    "{\"EstibyanDATA\": {\"Header\": {\"Action\": \"GetFeedback_Experience\",\"Lang\": \"0\"},\"Body\": {\"MasterTypeID\": \"3\"}}}";
				System.out.println("called  values sending input "+ dataString);	
				
		
		    	request = new SoapObject(namespace, "Execute");
		        request.addProperty("data",dataString);
				SetEnvelope();
				
				try {
				

					androidHttpTransport.call(SOAP_ACTION, envelope);				
					String result = envelope.getResponse().toString();
	            	System.out.println("called  imageURLS  "+result);
					System.out.println(" send Transaction ---->>>" + result.toString());		
					
		
					
					return result;
					
				} catch (Exception e) {
					// TODO: handle exception
					return e.toString();
				}
			} catch (Exception e) {
				// TODO: handle exception
				return e.toString();
			}

		}
		
		
		
		public String GetFeedback_Elements(String SubCategory_ID) 
		
		
		  {			
			System.out.println("PassSubCategory_ID =  " + SubCategory_ID);
			try {
				SOAP_ACTION = namespace + "Execute";
       	    String dataString =
			    "{\"EstibyanDATA\": {\"Header\": {\"Action\": \"GetFeedback_Elements\",\"Lang\": \"0\"},\"Body\": {\"SubCategory_ID\": \""+SubCategory_ID+"\"}}}";
				System.out.println("called  values sending input "+ dataString);	
				
		
		    	request = new SoapObject(namespace, "Execute");
		        request.addProperty("data",dataString);
				SetEnvelope();
				
				try {
				

					androidHttpTransport.call(SOAP_ACTION, envelope);				
					String result = envelope.getResponse().toString();
	            	System.out.println("called  imageURLS  "+result);
					System.out.println(" send Transaction ---->>>" + result.toString());		
					
		
					
					return result;
					
				} catch (Exception e) {
					// TODO: handle exception
					return e.toString();
				}
			} catch (Exception e) {
				// TODO: handle exception
				return e.toString();
			}

		}
		
		
		
		
		
	
}
