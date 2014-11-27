package com.example.demo_httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
    TextView tv;
    Button bt;
    HttpClient httpclient;
    HttpResponse httpresponse;
    Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv=(TextView) findViewById(R.id.textView1);
		bt=(Button) findViewById(R.id.button1);
		handler =new Handler(){
			public void handleMessage(android.os.Message msg) {
				tv.setText((CharSequence) msg.obj);
			};
		};
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				new Thread(){
					@Override
					public void run() {
						HttpGet httpget=new HttpGet("http://wwww.baidu.com");
						httpclient=new DefaultHttpClient();
						InputStream inputstream=null;
						try {
							httpresponse=httpclient.execute(httpget);
							if(httpresponse.getStatusLine().getStatusCode()==200){
								HttpEntity httpentity = httpresponse.getEntity();
								inputstream=httpentity.getContent();
								BufferedReader reader=new BufferedReader
										(new InputStreamReader(inputstream));
								StringBuilder builder=new StringBuilder();
								String text="";
								while((text=reader.readLine())!=null){
									builder.append(text);
								}
								Message msg =Message.obtain();
								msg.obj=builder.toString();
								handler.sendMessage(msg);
								reader.close();
							}
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					}
				.start();
				
			}});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
