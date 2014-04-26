package com.shaikhhamadali.blogspot.deviceinformation;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class DeviceInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_info);
		//Create instance of Listview and assign reference of control we declared in layout
		ListView lv=(ListView)findViewById(R.id.listView1);
		//create an instance of ArrayAdapter and pass context,layout,list of items
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
				this, 
				android.R.layout.simple_list_item_1,
				Utility.getlist() );
		//assign adapter to listview
		lv.setAdapter(arrayAdapter); 
	}

}
