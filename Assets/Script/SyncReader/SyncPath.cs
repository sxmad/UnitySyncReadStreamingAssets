using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SyncPath : MonoBehaviour {
    
    private AndroidJavaObject jInstance;
    private AndroidJavaObject jActivity;

    void Start() {
#if UNITY_ANDROID
        AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
        jActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");

        AndroidJavaClass midasJC = new AndroidJavaClass("com.asdfq.syncpathlibrary.SyncPath");
        jInstance = midasJC.CallStatic<AndroidJavaObject>("getInstance");
        InitSyncPath();
#endif
    }

    private void InitSyncPath() {
        jInstance.Call("initSyncPath", jActivity, jActivity, SyncPathConst.SYNC_PATH_NAME, "CallbackHandler");
    }

    public void CallbackHandler(string _callbackStr) {
        Debug.Log("----- SyncPath CALLBACK:" + _callbackStr);
        //_callbackStr.showAsToast();
    }

    public byte[] ReadBytesFromStreamingAssets(string _path){
        return jInstance.Call<byte[]>("readBytesFromAssets", _path);
    }

}
