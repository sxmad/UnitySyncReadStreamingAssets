# UnitySyncReadStreamingAssets
StreamingAssets Path Reader For Unity With Sync Method

Assets:Unity Project Folder

PathLib:Android Studio Project Folder

```c#
public SyncPathSystem syncPath;

private void TestSyncReader(){
    string filePath = Path.Combine(Application.streamingAssetsPath, "config.json");
    syncPath.ReadBytesFromStreamingAssets(filePath);
}
```

