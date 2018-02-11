package com.mbds.barcode_battle.utils;

import android.app.Activity;

import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by Yasmine on 06/02/2018.
 */

public class ScanHandler {

    public static void scanCodeBarre(Activity activity){
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }
}
