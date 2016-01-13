
package com.mcunderground.webview;

import com.parse.Parse;
import com.parse.PushService;
import com.parse.ParseInstallation;
import android.app.*;
import com.parse.*;

public class ParseApplication extends Application
{

	@Override
	public void onCreate()
	{
		// go to parse.com make app. then copy keys here. From parse.com you will be able to
		// send notifications to phone if this is installed.
		super.onCreate();
		Parse.initialize(this, "key1", "key1");
		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
		
		defaultACL.setPublicReadAccess(true);
		
		ParseACL.setDefaultACL(defaultACL, true);
		}
	
	
	
}
