/*

 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package seniordesign.mobileappandroid;

import android.R.id;
import android.R.layout;
import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.math.*;
import java.util.Arrays;


import seniordesign.mobileappandroid.BluetoothChat;

/**
 * This is the main Activity that displays the current chat session.
 */
public class BluetoothChat extends Activity {
	// Debugging
	private static final String TAG = "BluetoothChat";
	private static final boolean D = true;

	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;

	// Key names received from the BluetoothChatService Handler
	public static final String DEVICE_NAME = "device_name";
	public static final String TOAST = "toast";

	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE_SECURE = 1;
	private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
	private static final int REQUEST_ENABLE_BT = 3;

	// Layout Views
	private ListView mConversationView;
	

	// Name of the connected device
	private String mConnectedDeviceName = null;
	// Array adapter for the conversation thread
	private ArrayAdapter<String> mConversationArrayAdapter;
	// String buffer for outgoing messages
	private StringBuffer mOutStringBuffer;
	// Local Bluetooth adapter
	private BluetoothAdapter mBluetoothAdapter = null;
	// Member object for the chat services
	private BluetoothChatService mChatService = null;

	public Regression r;
	public Regression2 r2;
	public Regression3 r3;
	
	
	TextView leftLung;
	TextView rightLung;
	TextView heart;
	TextView liver;
	TextView largeIntestine;
	TextView smallIntestine;
	TextView stomach;
	TextView spleen;
	TextView gallbladder;
	TextView lKidney;
	TextView rKidney;
	TextView pancreas;
	TextView venaCava;
	TextView dAorta;
	TextView aAorta;

//	private DataThread mThread;
//	
//	public final class DataThread extends AsyncTask<Void, Void, Void>{
//
//		@Override
//		protected Void doInBackground(Void... params) {
//			// TODO Auto-generated method stub
//			while(!isCancelled() && !isFinishing()) {
//				Message msg = new Message();
//				msg.obj = new byte[] { 4, 15, 23, 29, 0, 0, 0, 0, 0, 0, 0, 0,
//						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//						0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
//				msg.arg1 = 4;
//				msg.arg2 = -1;
//				msg.what = MESSAGE_READ;
//				mHandler.sendMessage(msg);
//				
//				try{
//					Thread.sleep(10000);
//				}catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//			
//			return null;
//		}
//		
//	}
	


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		if(D) Log.e(TAG, "+++ ON CREATE +++");

		// Set up the window layout
		setContentView(R.layout.main);

		// Get local Bluetooth adapter
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		

		// If the adapter is null, then Bluetooth is not supported
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		

		leftLung = (TextView) findViewById(R.id.lLungDamage);
		rightLung = (TextView) findViewById(R.id.rLungDamage);
		heart = (TextView) findViewById(R.id.heartDamage);
		liver = (TextView) findViewById(R.id.liverDamage);
		largeIntestine = (TextView) findViewById(R.id.lIntestineDamage);
		smallIntestine = (TextView) findViewById(R.id.sIntestineDamage);
		stomach = (TextView) findViewById(R.id.stomachDamage);
		spleen = (TextView) findViewById(R.id.spleenDamage);
		gallbladder = (TextView) findViewById(R.id.gallbladderDamage);
		lKidney = (TextView) findViewById(R.id.lKidneyDamage);
		rKidney = (TextView) findViewById(R.id.rKidneyDamage);
		pancreas = (TextView) findViewById(R.id.pancreasDamage);
		venaCava = (TextView) findViewById(R.id.venaCavaDamage);
		dAorta = (TextView) findViewById(R.id.dAortaDamage);
		aAorta = (TextView) findViewById(R.id.aAortaDamage);
	}

	@Override
	public void onStart() {
		super.onStart();
		if(D) Log.e(TAG, "++ ON START ++");

		// If BT is not on, request that it be enabled.
		// setupChat() will then be called during onActivityResult
		if (!mBluetoothAdapter.isEnabled()) {
			Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
			// Otherwise, setup the chat session
		} else {
			if (mChatService == null) setupChat();
		}
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		if(D) Log.e(TAG, "+ ON RESUME +");

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity returns.
		if (mChatService != null) {
			// Only if the state is STATE_NONE, do we know that we haven't started already
			if (mChatService.getState() == BluetoothChatService.STATE_NONE) {
				// Start the Bluetooth chat services
				mChatService.start();
			}
		}
	}

	private void setupChat() {
		Log.d(TAG, "setupChat()");

		// Initialize the array adapter for the conversation thread
		mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message);
		mConversationView = (ListView) findViewById(R.id.in);
		mConversationView.setAdapter(mConversationArrayAdapter);

		// Initialize the BluetoothChatService to perform bluetooth connections
		mChatService = new BluetoothChatService(this, mHandler);

		// Initialize the buffer for outgoing messages
		mOutStringBuffer = new StringBuffer("");
	}

	@Override
	public synchronized void onPause() {
		super.onPause();
		if(D) Log.e(TAG, "- ON PAUSE -");
	}

	@Override
	public void onStop() {
		super.onStop();
		if(D) Log.e(TAG, "-- ON STOP --");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth chat services
		if (mChatService != null) mChatService.stop();
		if(D) Log.e(TAG, "--- ON DESTROY ---");
	}

	private void ensureDiscoverable() {
		if(D) Log.d(TAG, "ensure discoverable");
		if (mBluetoothAdapter.getScanMode() !=
				BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
			Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
			discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
			startActivity(discoverableIntent);
		}
	}

	/**
	 * Sends a message.
	 * @param message  A string of text to send.
	 */
	private void sendMessage(String message) {
		// Check that we're actually connected before trying anything
		if (mChatService.getState() != BluetoothChatService.STATE_CONNECTED) {
			Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
			return;
		}

		// Check that there's actually something to send
		if (message.length() > 0) {
			// Get the message bytes and tell the BluetoothChatService to write
			byte[] send = message.getBytes();
			mChatService.write(send);

			// Reset out string buffer to zero and clear the edit text field
			mOutStringBuffer.setLength(0);
			//mOutEditText.setText(mOutStringBuffer);
		}
	}

	// The action listener for the EditText widget, to listen for the return key
	private TextView.OnEditorActionListener mWriteListener =
			new TextView.OnEditorActionListener() {
		public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
			// If the action is a key-up event on the return key, send the message
			if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
				String message = view.getText().toString();
				sendMessage(message);
			}
			if(D) Log.i(TAG, "END onEditorAction");
			return true;
		}
	};

	/** private final void setStatus(int resId) {
        final ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(resId);
    }

    private final void setStatus(CharSequence subTitle) {
        final ActionBar actionBar = getActionBar();
        actionBar.setSubtitle(subTitle);
    } **/

	// The Handler that gets information back from the BluetoothChatService
	private final Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_STATE_CHANGE:
				if(D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
				switch (msg.arg1) {
				case BluetoothChatService.STATE_CONNECTED:
					//setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
					mConversationArrayAdapter.clear();
					break;
				case BluetoothChatService.STATE_CONNECTING:
					// setStatus(R.string.title_connecting);
					break;
				case BluetoothChatService.STATE_LISTEN:
				case BluetoothChatService.STATE_NONE:
					//setStatus(R.string.title_not_connected);
					break;
				}
				break;
			case MESSAGE_WRITE:
				byte[] writeBuf = (byte[]) msg.obj;
				// construct a string from the buffer
				String writeMessage = new String(writeBuf);
				mConversationArrayAdapter.add("Me:  " + writeMessage);
				break;
			/*
			The following code reads serial data sent from bluetooth device, in the case of this project,
			the device is an Arduino.  Multiple coordinates are going to be received from the Arduino without
			any identifying markers.  To combat this, it is necessary to change the code in the Arduino to 
			offset the value being sent.  This will provide the identifier.  Below we convert the coordinates
			back to the original value (0-7) and store the value in the appropriate variable to be used by the 
			regression programs.
			*/
				
			case MESSAGE_READ:

				byte[] readBuf = (byte[]) msg.obj;
				String readMessage = new String(Arrays.toString(readBuf));
				Log.d(TAG, "Message Received: " + Arrays.toString(readBuf));
				mConversationArrayAdapter.add(readMessage);
//				int a = readBuf.length;
				
//				byte[] mThread = (byte[]) msg.obj;
//				String readMessage = new String(Arrays.toString(mThread));
//				Log.d(TAG, "Message Received: " + Arrays.toString(mThread));
//				mConversationArrayAdapter.add(readMessage);
				
				int a = msg.arg1;

				//coordinates[] is the exact length necessary as given by double[a], and contains no more and
				//no fewer positions than required.
				
				double coordinates[] = new double[a];

				//Check the values of the data in readBuf[] and make the necessary conversions to store in the
				//variables.
				for(int i=0; i<a; i++){
					
					int val=readBuf[i];
//					int val=mThread[i];
					
					/*
					*(int) val/8 will divide val and round down to the nearest integer.
					*val%8 divides by 8 but takes the remainder which is the value of
					*all variables above x1, though the math works with x1.
					*/
					
					coordinates[(int) val/8] = val%8;

				}

				//The code below is using the length of readBuf[] to determine which regression program to use.
				
				if(msg.arg1==4){
					double x1 = coordinates[0];
					double x2 = coordinates[1];
					double y1 = coordinates[2];
					double y2 = coordinates[3];

					r = new Regression(x1, x2, y1, y2);
					r.computation();
					
					leftLung.setText("Left Lung "+ r.leftLungDamage() +"% hit");
					rightLung.setText("Right Lung "+ r.rightLungDamage() +"% hit");
					heart.setText("Heart "+ r.heartDamage() +"% hit");
					liver.setText("Liver "+ r.liverDamage() +"% hit");
					smallIntestine.setText("Small Intestine "+ r.smIntestineDamage() +"% hit");
					largeIntestine.setText("Large Intestine "+ r.lgIntestineDamage() +"% hit");
					stomach.setText("Stomach "+ r.stomachDamage() +"% hit");
					spleen.setText("Spleen "+ r.spleenDamage() +"% hit");
					gallbladder.setText("Gallbladder "+ r.gallbladderDamage() +"% hit");
					lKidney.setText("Left Kidney "+ r.lKidneyDamage() +"% hit");
					rKidney.setText("Right Kidney "+ r.rKidneyDamage() +"% hit");
					pancreas.setText("Pancreas "+ r.pancreasDamage() +"% hit");
					venaCava.setText("Vena Cava "+ r.venaCavaDamage() +"% hit");
					dAorta.setText("Descending Aorta "+ r.dAortaDamage() +"% hit");
					aAorta.setText("Ascending Aorta "+ r.aAortaDamage() +"% hit");
				}

				if(msg.arg1 == 6){
					double x1 = coordinates[0];
					double x2 = coordinates[1];
					double y1 = coordinates[2];
					double y2 = coordinates[3];
					double x3 = coordinates[4];
					double y3 = coordinates[5];

					r2 = new Regression2(x1, y1, x2, y2, x3, y3);
					r2.computation();
					
					leftLung.setText("Left Lung "+ r2.leftLungDamage() +"% hit");
					rightLung.setText("Right Lung "+ r2.rightLungDamage() +"% hit");
					heart.setText("Heart "+ r2.heartDamage() +"% hit");
					liver.setText("Liver "+ r2.liverDamage() +"% hit");
					smallIntestine.setText("Small Intestine "+ r2.smIntestineDamage() +"% hit");
					largeIntestine.setText("Large Intestine "+ r2.lgIntestineDamage() +"% hit");
					stomach.setText("Stomach "+ r2.stomachDamage() +"% hit");
					spleen.setText("Spleen "+ r2.spleenDamage() +"% hit");
					gallbladder.setText("Gallbladder "+ r2.gallbladderDamage() +"% hit");
					lKidney.setText("Left Kidney "+ r2.lKidneyDamage() +"% hit");
					rKidney.setText("Right Kidney "+ r2.rKidneyDamage() +"% hit");
					pancreas.setText("Pancreas "+ r2.pancreasDamage() +"% hit");
					venaCava.setText("Vena Cava "+ r2.venaCavaDamage() +"% hit");
					dAorta.setText("Descending Aorta "+ r2.dAortaDamage() +"% hit");
					aAorta.setText("Ascending Aorta "+ r2.aAortaDamage() +"% hit");
				}

				if(msg.arg1 == 8){
					double x1 = coordinates[0];
					double x2 = coordinates[1];
					double y1 = coordinates[2];
					double y2 = coordinates[3];
					double x3 = coordinates[4];
					double y3 = coordinates[5];
					double x4 = coordinates[6];
					double y4 = coordinates[7];

					r3 = new Regression3(x1, y1, x2, y2, x3, y3, x4, y4);
					r3.computation();
					
					leftLung.setText("Left Lung "+ r3.leftLungDamage() +"% hit");
					rightLung.setText("Right Lung "+ r3.rightLungDamage() +"% hit");
					heart.setText("Heart "+ r3.heartDamage() +"% hit");
					liver.setText("Liver "+ r3.liverDamage() +"% hit");
					smallIntestine.setText("Small Intestine "+ r3.smIntestineDamage() +"% hit");
					largeIntestine.setText("Large Intestine "+ r3.lgIntestineDamage() +"% hit");
					stomach.setText("Stomach "+ r3.stomachDamage() +"% hit");
					spleen.setText("Spleen "+ r3.spleenDamage() +"% hit");
					gallbladder.setText("Gallbladder "+ r3.gallbladderDamage() +"% hit");
					lKidney.setText("Left Kidney "+ r3.lKidneyDamage() +"% hit");
					rKidney.setText("Right Kidney "+ r3.rKidneyDamage() +"% hit");
					pancreas.setText("Pancreas "+ r3.pancreasDamage() +"% hit");
					venaCava.setText("Vena Cava "+ r3.venaCavaDamage() +"% hit");
					dAorta.setText("Descending Aorta "+ r3.dAortaDamage() +"% hit");
					aAorta.setText("Ascending Aorta "+ r3.aAortaDamage() +"% hit");
				}

				
				break;
				
			case MESSAGE_DEVICE_NAME:
				// save the connected device's name
				mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
				Toast.makeText(getApplicationContext(), "Connected to "
						+ mConnectedDeviceName, Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

	/*
	 * I have not added or modified the below in any way other than deleting unnecessary blocks of code.  If a code
	 * block has been left below, then  it is either valuable, or I don't know what it does and I am not going to 
	 * mess with it
	 */
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(D) Log.d(TAG, "onActivityResult " + resultCode);
		switch (requestCode) {
		case REQUEST_CONNECT_DEVICE_SECURE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, true);
			}
			break;
		case REQUEST_CONNECT_DEVICE_INSECURE:
			// When DeviceListActivity returns with a device to connect
			if (resultCode == Activity.RESULT_OK) {
				connectDevice(data, false);
			}
			break;
		case REQUEST_ENABLE_BT:
			// When the request to enable Bluetooth returns
			if (resultCode == Activity.RESULT_OK) {
				// Bluetooth is now enabled, so set up a chat session
				setupChat();
			} else {
				// User did not enable Bluetooth or an error occurred
				Log.d(TAG, "BT not enabled");
				Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}

	private void connectDevice(Intent data, boolean secure) {
		// Get the device MAC address
		String address = data.getExtras()
				.getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
		// Get the BluetoothDevice object
		BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
		// Attempt to connect to the device
		mChatService.connect(device, secure);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent serverIntent = null;
		switch (item.getItemId()) {
		case R.id.secure_connect_scan:
			// Launch the DeviceListActivity to see devices and do scan
			serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_SECURE);
			return true;
		case R.id.insecure_connect_scan:
			// Launch the DeviceListActivity to see devices and do scan
			serverIntent = new Intent(this, DeviceListActivity.class);
			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
			return true;
		case R.id.discoverable:
			// Ensure this device is discoverable by others
			ensureDiscoverable();
			return true;
		}
		return false;
	}

}
