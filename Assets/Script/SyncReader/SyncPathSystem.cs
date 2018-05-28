using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class SyncPathSystem {

    private SyncPath syncPath;

    public SyncPathSystem() {
#if !UNITY_EDITOR
        Init();
#endif
    }

    private void Init() {
        OnInit();
    }

    private void OnInit() {
        GameObject syncPathObj = new GameObject(SyncPathConst.SYNC_PATH_NAME);
        UnityEngine.Object.DontDestroyOnLoad(syncPathObj);
        syncPath = syncPathObj.AddComponent<SyncPath>();
    }

    public byte[] ReadBytesFromStreamingAssets(string _path) {
        return syncPath.ReadBytesFromStreamingAssets(_path);
    }
}
