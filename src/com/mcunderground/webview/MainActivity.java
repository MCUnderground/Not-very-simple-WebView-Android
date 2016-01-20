
package com.mcunderground.webview;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import android.support.v4.widget.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import com.parse.*;

public class MainActivity extends Activity
{

	MediaPlayer bkgrdmsc;
	WebView myWebView;

	//enable javascript in webview
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		Toast.makeText(getApplicationContext(), "Welcome!",
					   Toast.LENGTH_SHORT).show();

			// Adds music to app. Add music in raw folder and name it 
			// bkgrdmsc to this code load it.	   
					   
		/* bkgrdmsc = MediaPlayer.create(MainActivity.this, R.raw.bkgrdmsc);
		bkgrdmsc.setLooping(true);
		bkgrdmsc.start(); */

        //adding webview
		
		myWebView = (WebView) findViewById(R.id.webview);
		myWebView.getSettings().setJavaScriptEnabled(true);
		WebSettings webSettings = myWebView.getSettings();
		myWebView.getSettings();
		myWebView.setBackgroundColor(0);
	    myWebView.setWebChromeClient(new MyChromeClient());
		
		myWebView.addJavascriptInterface(new JSAndyConnector(this), "Dev");
		// Loads assests in this case index.html. you can set url for example:
		// myWebView.loadUrl("http://github.com/mcunderground);
		myWebView.loadUrl("file:///android_asset/index.html");

		// swipe down to refresh page. Implementing
		
		final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
		
//colors can be 4
        swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
								   android.R.color.holo_purple,
								   android.R.color.holo_orange_light,
								   android.R.color.holo_red_light);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                @Override public void onRefresh()
				{
					
					myWebView.reload();
					
					new Thread() 
					{
						// bar shows for 2.1 seconds
						public void run(){
							SystemClock.sleep(2100);
							swipeLayout.setRefreshing(false);


						};
					}.start();
					
                    
	
                }
				});
				
				// add parse
				ParseAnalytics.trackAppOpenedInBackground(getIntent());
				
				PushService.setDefaultPushCallback(this, MainActivity.class);
		        ParseInstallation.getCurrentInstallation().saveInBackground();
		
 // if error occure
				myWebView.setWebViewClient(new WebViewClient() {
					public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
						// loads error.html
					  myWebView.loadUrl("file:///android_asset/error.html");
						
					}  
				});
				
}
	

// adding chromeclient. 
	public class MyChromeClient extends WebChromeClient
	{

		@Override
		public boolean onConsoleMessage(ConsoleMessage consoleMessage)
		{
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, consoleMessage.message(),
						   Toast.LENGTH_SHORT).show();
			return super.onConsoleMessage(consoleMessage);

		}

		@Override
		public boolean onJsAlert(WebView view, String url, String message,
								 JsResult result)
		{
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(
				MainActivity.this);
			builder.setTitle("From JS");
			builder.setMessage(message);
			builder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub

					}
				});

			builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub

					}
				});

			builder.setCancelable(true);
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
			return super.onJsAlert(view, url, message, result);
		}
	}

	public class JSAndyConnector
	{

		private Context context;

		public JSAndyConnector(Context context)
		{
			this.context = context;
		}

		@JavascriptInterface
		public void displayDialog(String msg)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setTitle("From JS");
			builder.setMessage(msg);
			builder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub

					}
				});

			builder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub

					}
				});

			builder.setCancelable(true);
			AlertDialog alertDialog = builder.create();
			alertDialog.show();

		}

	}
// back button doesnt quit app, but loads previous page.
	@Override
	public void onBackPressed()
	{
		if (myWebView.canGoBack())
		{
			myWebView.goBack();
		}
		else
		{
			super.onBackPressed();
		}
	}
// stops music when apps closes
//	@Override
	//protected void onPause(){
	//	super.onPause();
	//	bkgrdmsc.release();
	//	finish();
//	}


}
