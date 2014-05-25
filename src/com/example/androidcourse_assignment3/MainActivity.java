package com.example.androidcourse_assignment3;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	
	private String keywordSearch;
	private String locationSearch;
	public ProgressDialog dialog;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) this.findViewById(R.id.button1);
        button.setOnClickListener(this);
    }
    
    public void onClick(View v) {
        switch(v.getId())
        {
          case R.id.button1:
        	  TextView keywordTxt = (TextView)this.findViewById(R.id.editText1);
        	  keywordSearch = (String) keywordTxt.getText().toString();
        	  
        	  TextView locationTxt = (TextView)this.findViewById(R.id.editText2);
        	  locationSearch = (String) locationTxt.getText().toString();
              new PerformSearch().execute(keywordSearch, locationSearch);
          break;

        }
    }
    
	private class PerformSearch extends AsyncTask<String, Void, XMLNode> {

		@Override
		protected XMLNode doInBackground(String... params) {
			InputStream stream = search(params);
			XMLNode doc = new XMLNode(stream);
			return doc;
		}
		
		@Override
        protected void onPostExecute(XMLNode doc) {
	        List<XMLNode>nodes = doc.nodesForXpath("./ResponseJobSearch/Results");
	        if(nodes.size() > 0) {
	        	nodes = doc.nodesForXpath("./ResponseJobSearch/Results/JobSearchResult");
	        	Integer limit = Math.min(nodes.size(), 10);
	            Job[] jobs = new Job[limit];
	        	String title = "";
	        	String company = "";
	        	String location = "";
	    		for (int i = 0; i<limit; i++) {
	    			XMLNode node = nodes.get(i);
	    			XMLNode companyNode = node.nodeForXpath("./Company");
	    			if(companyNode != null) {
	    				company = companyNode.getElementStringValue();
	    			}
	
	    			XMLNode titleNode = node.nodeForXpath("./JobTitle");
	    			if(titleNode != null) {
	    				title = titleNode.getElementStringValue();
	    			}
	    			
	    			XMLNode locationNode = node.nodeForXpath("./Location");
	    			if (locationNode != null) {
	    				location = locationNode.getElementStringValue();
	    			}
	    			jobs[i] = (Job) new Job(company, title, location);
	    		}
	        	Intent intent = new Intent(MainActivity.this, JobsListActivity.class);
	    		intent.putExtra("JOBS_ARRAY", jobs);
	            startActivity(intent);
	        } else {
	        	// display no results message
	        }
	        dialog.hide();
        }

        @Override
        protected void onPreExecute() {
        	dialog = ProgressDialog.show(MainActivity.this, "", "Doing stuff. Please wait...", true);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
	}

	private InputStream search(CharSequence... params) {
		InputStream stream = null;
        String url = "http://api.careerbuilder.com/v2/jobsearch?DeveloperKey=WDT85BK6CPVTLH3RDHR6";
        
        String keywordString = params[0].toString();
        String locationString = params[1].toString();
        
        String uri = Uri.parse(url)
            .buildUpon()
            .appendQueryParameter("Keywords", keywordString)
            .appendQueryParameter("&Location", locationString)
            .build().toString();
        
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        HttpConnectionParams.setSoTimeout(httpParams, 10000);
        HttpClient httpClient = new DefaultHttpClient(httpParams);
        
        HttpContext context = new BasicHttpContext();
        HttpGet request = new HttpGet(uri);
        
        try {
        	HttpResponse response = httpClient.execute(request, context);
        	stream = response.getEntity().getContent();
        } catch (Exception e) {
            Log.e("BackgroundError", e.toString());
        }
        
        return stream;
	}
}
