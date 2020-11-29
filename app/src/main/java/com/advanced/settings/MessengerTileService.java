package com.advanced.settings;

public class MessengerTileService extends PackageTileService {
    public static final String UPDATE_MESSENGER_ENABLED_STATUS_ACTION = "com.update.messenger";
    private final String MESSENGER_TILE_ENABLED_LABEL = "Messenger: Enabled";
    private final String MESSENGER_TILE_DISABLED_LABEL = "Messenger: Disabled";

    @Override
    public void onCreate() {
        super.onCreate();
        setUpdatePackageEnabledStatusAction(UPDATE_MESSENGER_ENABLED_STATUS_ACTION);
        setControlledPackageName(MyApplication.MESSENGER_PACKAGE_NAME);
        setTileEnabledLabel(MESSENGER_TILE_ENABLED_LABEL);
        setTileDisabledLabel(MESSENGER_TILE_DISABLED_LABEL);
    }
}