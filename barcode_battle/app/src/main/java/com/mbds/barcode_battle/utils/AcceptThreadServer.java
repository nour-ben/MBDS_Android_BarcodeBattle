package com.mbds.barcode_battle.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * Created by Yasmine on 11/02/2018.
 */

public class AcceptThreadServer extends Thread {

    private final BluetoothServerSocket mmServerSocket;
    private String NAME;
    private UUID MY_UUID;
    private BluetoothAdapter mBluetoothAdapter;

    public AcceptThreadServer (String name, UUID uuid, BluetoothAdapter mBluetoothAdapter) {
        // Use a temporary object that is later assigned to mmServerSocket
        // because mmServerSocket is final.
        NAME = name;
        MY_UUID = uuid;
        this.mBluetoothAdapter = mBluetoothAdapter;
        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code.
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's listen() method failed", e);
        }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned.
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                Log.e(TAG, "Socket's accept() method failed", e);
                break;
            }

            if (socket != null) {
                // A connection was accepted. Perform work associated with
                // the connection in a separate thread.
                //manageMyConnectedSocket(socket);
                try {
                    mmServerSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    // Closes the connect socket and causes the thread to finish.
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the connect socket", e);
        }
    }
}
