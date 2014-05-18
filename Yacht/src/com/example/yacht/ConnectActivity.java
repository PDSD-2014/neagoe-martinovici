package com.example.yacht;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConnectActivity extends Activity {

	EditText hisIP;
	Button genNr;
	TextView mynr, hisnr, result;
	String hisNumber;
	String hisIp;
	Integer myNumber;
	Button go;
	Context c;
	String first = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connect);
		hisIP = (EditText) findViewById(R.id.edtID);
		genNr = (Button) findViewById(R.id.btnRandom);
		mynr = (TextView) findViewById(R.id.txtMyNr);
		hisnr = (TextView) findViewById(R.id.txtHisNr);
		result = (TextView) findViewById(R.id.txtResult);
		go = (Button) findViewById(R.id.btnGo);
		c = this;
		Random nr = new Random();
		myNumber = nr.nextInt(10000000);
		go.setEnabled(false);
		go.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(c, Game.class);
				String[] data = new String[2];
				data[0] = hisIp;
				data[1] = first.toString();
				i.putExtra("data", data);

			}
		});

		class SingleThreadedServer implements Runnable {

			private final static String TAG = "ServerThread";

			// For a TCP connection (i.e. a server) we need a ServerSocket
			private ServerSocket in;

			// In the constructor we try creating the server socket, on port
			// 9000.
			public SingleThreadedServer() {
				try {
					// Beware: Only privileged users can use ports below 1023.
					in = new ServerSocket(9000);
				}
				catch (IOException e) {
					Log.e(TAG,
							"Cannot create socket. Due to: " + e.getMessage());
				}
			}

			@Override
			public void run() {

				// Always try serving incoming requests.
				while (true) {
					// For every request we are allocated a new socket.
					Socket incomingRequest = null;

					try {
						// Wait in blocked state for a request.
						incomingRequest = in.accept();
					}
					catch (IOException e) {
						Log.e(TAG, "Error when accepting connection.");
					}

					// When accept() returns a new request was received.
					// We use the incomingRequest socket for I/O
					Log.d(TAG,
							"New request from: "
									+ incomingRequest.getInetAddress());

					// Get its associated OutputStream for writing.
					InputStream receiveStream = null;
					try {
						receiveStream = incomingRequest.getInputStream();
						BufferedReader b = new BufferedReader(
								new InputStreamReader(receiveStream));
						hisNumber = b.readLine();
						Log.e(TAG, " nr lui " + hisNumber);
					}
					catch (IOException e) {
						Log.e(TAG, "Cannot get inputstream");
					}

					// Do the UI update.
					hisnr.post(new Runnable() {

						@Override
						public void run() {
							if (hisNumber != null) {
								// Set the contents of the text field to
								// the
								// received response.
								hisnr.setText("His number is: " + hisNumber);
								final Integer other = Integer
										.valueOf(hisNumber);
								result.post(new Runnable() {

									@Override
									public void run() {
										// TODO Auto-generated method stub
										if (other < myNumber) {
											first = "1";
										}
										result.setText(other > myNumber ? "You go second"
												: "You go first");
										go.setEnabled(true);
									}

								});
							}
							else {
								Toast.makeText(ConnectActivity.this,
										"Your friend said nothing.",
										Toast.LENGTH_SHORT).show();
							}
						}
					});

					// Make sure data is sent and allocated resources are
					// cleared.
					try {
						incomingRequest.close();
					}
					catch (IOException e) {
						Log.e(TAG, "Error finishing request.");
					}

					Log.d(TAG, "Sent greeting.");
					// Continue the looping.
				}

			}
		}

		Thread greetingServer = new Thread(new SingleThreadedServer());
		greetingServer.start();

		genNr.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				hisIp = hisIP.getText().toString();
				if (hisIp.length() != 0) {
					// Create then start the "worker" thread.
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								// Open the socket.
								Socket clientSocket = new Socket(hisIp, 9000);

								OutputStream responseStream = null;
								try {
									responseStream = clientSocket
											.getOutputStream();
								}
								catch (IOException e) {
									Log.e("CLIENT", "Cannot get outputstream.");
								}

								// Wrap it with a PrinStream for convenience.
								PrintStream writer = new PrintStream(
										responseStream);

								writer.print(myNumber.toString());
								// Release the resource.
								clientSocket.close();
							}
							catch (UnknownHostException e) {
								Log.e("CLIENT", "Unknown host");
								Toast.makeText(ConnectActivity.this,
										"Unknown host.", Toast.LENGTH_SHORT)
										.show();
							}
							catch (IOException e) {
								Log.e("CLIENT", "Error opening client socket.");
								Toast.makeText(ConnectActivity.this,
										"Cannot open socket..",
										Toast.LENGTH_SHORT).show();
							}

						}
					}).start();

					mynr.setText("My number is: " + myNumber.toString());
				}
			}
		});
	}
}
