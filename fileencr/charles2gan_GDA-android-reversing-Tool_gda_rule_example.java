//comments
gda_vrule{
	id=3;
	rule=apk.minsdk<21&&api.match("sendBroadcast",2)||api.match("sendBroadcastAsUser",3);
	type="broadcast";
	vname="Broadcast sent (or as specific user) with receiverPermission with minimum SDK under 21";
	vlevel="warning";
	description="A broadcast, sendBroadcast/sendBroadcastAsUser which specifies the receiverPermission, but may still be vulnerable to interception, due to the permission squatting vulnerability in API levels before 21. This means any application, installed prior to the expected receiver(s) on the device can potentially receive this broadcast. You should investigate this for potential data leakage.";
	suggestion="";
	returnType=callors;
}

gda_vrule{
	id=8;
	rule=apk.minsdk<21&&api.match("sendOrderedBroadcast","2-7")||api.match("sendOrderedBroadcastAsUser",7);
	type="broadcast";
	vname="Ordered broadcast sent (or as specific user) with receiverPermission with minimum SDK under 21";
	vlevel="warning";
	description="";
	suggestion="";
	returnType=callors;
}

gda_vrule{
	id=10;
	rule=apk.minsdk>=21&&api.match("sendStickyBroadcast|sendStickyBroadcastAsUser|sendStickyOrderedBroadcast|sendStickyOrderedBroadcastAsUser","android.support.v4.content.LocalBroadcastManager");
	type="broadcast";
	vname="Sticky broadcast sent";
	vlevel="VULNERABILITY";
	description="";
	suggestion="";
	returnType=callors;
}
//Determines if the `checkServerTrusted` method is overriden with either no function body (defaults to allow)or if the body just returns
gda_vrule{
	id=11;
	rule=method.match("checkServerTrusted")&&(method.body.len==0||(method.body.len==1&&method.body1^"return"));
	type="cert";
	vname="Empty certificate method";
	vlevel="warning";
	description="";
	suggestion="";
	returnType=callors;
}

gda_vrule{
	id=13;
	rule=api.match("AllowAllHostnameVerifier|NullHostNameVerifier");
	type="cert";
	vname="Allow all hostname verifier used";
	vlevel="warning";
	description="";
	suggestion="";
	returnType=callors;
}
gda_vrule{
	id=14;
	rule=api.match("setHostnameVerifier",1)&&api.callors.body1^".ALLOW_ALL_HOSTNAME_VERIFIER";
	type="cert";
	vname="setHostnameVerifier set to ALLOW_ALL";
	vlevel="warning";
	description="";
	suggestion="";
	returnType=callors;
}

gda_vrule{
	id=15;
	rule=api.match("getInstance","javax.crypto.Cipher")&&api.callors.refString^~".*/ECB/.*";
	type="crypto";
	vname="ECB Cipher Usage";
	vlevel="VULNERABILITY";
	description="ECB mode is an insecure encryption technique and prone to data leakage,getInstance should not be called with ECB as the cipher mode, as it is insecure.";
	suggestion="";
	returnType=callors;
}

gda_vrule{
	id=18;
	rule=apk.refString^~"PRIVATE\sKEY";
	type="crypto";
	vname="Encryption keys are packaged with the application";
	vlevel="VULNERABILITY";
	description="It appears there is a private key embedded in your application";
	suggestion="";
	returnType=string;
}
gda_vrule{
	id=20;
	rule=api.match("<init>","java.util.Random");
	type="crypto";
	vname="Random number generator is insecure";
	vlevel="WARNING";
	description="";
	suggestion="A quick fix could be to replace the use of java.util.Random with something stronger, such as java.security.SecureRandom.";
	returnType=callors;
}
gda_vrule{
	id=21;
	rule=api.match("v|d|i|w|e","android.util.Log");
	type="file";
	vname="Logging found";
	vlevel="WARNING";
	description="Logs are detected. This may allow potential leakage of information from Android applications. Logs should never be compiled into an application except during development.";
	suggestion="";
	returnType=callors;
}

gda_vrule{
	id=27;	
	rule=api.match("openOrCreateDatabase")&&api.callors.body1^"openOrCreateDatabase(\"privateNotSoSecure\"";
	type="INFO";
	vname="Clear text data Storage";
	vlevel="WARNING";
	description="";
	suggestion="";
	returnType=callors;
}

gda_vrule{
	id=34;
	rule=api.match("setJavaScriptEnabled")&&api.callorss.body1^"setJavaScriptEnabled(true)";
	type="WebView";
	vname="Javascript enabled in Webview";
	vlevel="WARNING";
	description="";
	suggestion="";
	returnType=callors;
}


gda_vrule{
	id=37;
	rule=api.match("setAllowContentAccess",1)&&!(api.callors.body1^".getSettings()"&&api.callors.body1^"setAllowContentAccess(true)");
	type="WebView";
	vname="Webview enables content access";
	vlevel="WARNING";
	description="";
	suggestion="";
	returnType=callors;
}

gda_vrule{
	id=41;
	rule=mainfest.application^"android:allowBackup";
	type="mainfest";
	vname="Backup is allowed in manifest";
	vlevel="WARNING";
	description="Backups enabled: Potential for data theft via local attacks via adb backup, if the device has USB debugging enabled (not common).";
	suggestion="";
}

gda_vrule{
	id=44;
	rule=apk.minsdk<21&&mainfest.permission^~"android:protectionLevel\x20{0,}=\x20{0,}(signature|signatureOrSystem)";
	type="mainfest";
	vname="Custom permissions are enabled in the manifest";
	vlevel="WARNING";
	description="";
	suggestion="";
}

gda_vrule{
	id=48;
	rule=apk.exposed("receive");
	type="mainfest";
	vname="Receive Component Exposed";
	vlevel="WARNING";
	description="";
	returnType=string;
}










