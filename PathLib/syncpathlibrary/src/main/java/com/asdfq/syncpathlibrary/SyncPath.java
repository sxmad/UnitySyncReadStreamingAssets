package com.asdfq.syncpathlibrary;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * Created by xiaoxin on 2018/5/25.
 */

public class SyncPath {

    private Context pathContext;
    private Activity pathActivity;

    private String callbackObjectName;
    private String callbackFuncName;

    private AssetManager assetManager;

    private static volatile SyncPath instance = null;
    private SyncPath(){}
    public static SyncPath getInstance() {
        if (instance == null) {
            synchronized (SyncPath.class) {
                if (instance == null) {
                    instance = new SyncPath();
                }
            }
        }
        return instance;
    }

    public void initSyncPath(Activity pathActivity, Context pathContext, String callbackObjectName, String callbackFuncName){
        this.pathActivity = pathActivity;
        this.pathContext = pathContext;
        this.callbackObjectName = callbackObjectName;
        this.callbackFuncName = callbackFuncName;
        assetManager = pathActivity.getAssets();
    }

    public byte[] readBytesFromAssets(String path){
        InputStream inputStream = null;
        try{
            inputStream = assetManager.open(path);
        }catch (FileNotFoundException e){
            commonCallbackToUnity("FileNotFoundException from java, readBytes: "+e.getStackTrace());
        }catch (IOException e){
            commonCallbackToUnity("IOException from java, readBytes: "+e.getStackTrace());
        }
        return readTextBytes(inputStream);
    }

    private byte[] readTextBytes(InputStream inputStream){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len = 0;
        try{
            while ((len = inputStream.read(buf)) != -1){
                outputStream.write(buf, 0, len);
                outputStream.flush();
            }
            outputStream.close();
            inputStream.close();
        }catch (IOException e){
            commonCallbackToUnity("IOException from Java, readTextBytes: "+ e.getMessage());
        }
        return outputStream.toByteArray();
    }

    public void commonCallbackToUnity(String logStr){
        callbackToUnity(callbackObjectName, callbackFuncName, logStr);
    }

    public void callbackToUnity(String callbackObjectName, String callbackFuncName, String callbackParam){
        //UnityPlayer.UnitySendMessage(callbackObjectName, callbackFuncName, param);
        try{
            Class unityPlayer = Class.forName("com.unity3d.player.UnityPlayer");
            if (unityPlayer != null){
                try{
                    Method unitySendMessage = unityPlayer.getMethod("UnitySendMessage", String.class, String.class, String.class);
                    unitySendMessage.setAccessible(true);
                    unitySendMessage.invoke(null, callbackObjectName, callbackFuncName, callbackParam);
                    Log.d("callbackToUnity", "unitySendMessage.invoke:" + callbackParam);
                }catch (Exception ex){
                    Log.d("callbackToUnity", "UnityPlayer exception e:" + ex.toString());
                }
            }
        }catch (Exception ex){
            Log.d("callbackToUnity", "Unity callback exception e:" + ex.toString());
        }
    }

}
