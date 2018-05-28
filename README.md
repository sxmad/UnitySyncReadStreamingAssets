# UnitySyncReadStreamingAssets
StreamingAssets Path Reader For Unity With Sync Method



```c#
public SyncPathSystem syncPath;

private void TestSyncReader(){
    string filePath = Path.Combine(Application.streamingAssetsPath, "config.json");
    syncPath.ReadBytesFromStreamingAssets(filePath);
}
```

