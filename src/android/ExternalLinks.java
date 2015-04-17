package com.telnext;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;
import org.apache.cordova.PluginResult.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

/**
 * This class echoes a string called from JavaScript.
 */
//Changed from CDNsms to sms
public class ExternalLinks extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("openPhone")) {
            String phoneNumber = args.getString(0);
            this.openPhone(phoneNumber, callbackContext);
            return true;
        }

        if (action.equals("openMap")) {
            JSONObject params = args.getJSONObject(0);
            this.openMap(params, callbackContext);
            return true;
        }
        
        return false;
    }

    private void openPhone(String phoneNumber, final CallbackContext callbackContext) throws JSONException {
        try {
            Uri callUri = Uri.parse("tel:" + phoneNumber);
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, callUri);
            getActivity().startActivity(dialIntent);
            callbackContext.sendPluginResult(new PluginResult(Status.OK, "Intent OK"));
        } catch (Exception e){
            callbackContext.sendPluginResult(new PluginResult(Status.ERROR, e.getMessage()));
        }
    }

    private void openMap(JSONObject params, final CallbackContext callbackContext) throws JSONException {
        try {
            String address = null;
            String latitude = null;
            String longitude = null;
            try {address = params.getString("address");} catch (JSONException ex){};
            try {
                latitude = Double.toString(params.getDouble("latitude"));
                longitude = Double.toString(params.getDouble("longitude"));
            } catch (JSONException ex){};
            if (address != null || (latitude != null && longitude != null)){
                String mapUri = "http://maps.google.com/maps?";
                if (address != null) {
                    mapUri += ("q=" + address);
                } else {
                    mapUri += ("ll=" + latitude + ',' + longitude);
                }
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUri));
                getActivity().startActivity(mapIntent);
            }
            callbackContext.sendPluginResult(new PluginResult(Status.OK, "Intent OK"));
        } catch (Exception e){
            callbackContext.sendPluginResult(new PluginResult(Status.ERROR, e.getMessage()));
        }
    }
    
    private Activity getActivity() {
        return this.cordova.getActivity();
    }
}
