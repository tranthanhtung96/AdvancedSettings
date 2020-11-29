package com.advanced.settings;

public class FacebookTileService extends PackageTileService {
    public static final String UPDATE_FACEBOOK_ENABLED_STATUS_ACTION = "com.update.facebook";
    private final String FACEBOOK_TILE_ENABLED_LABEL = "Facebook: Enabled";
    private final String FACEBOOK_TILE_DISABLED_LABEL = "Facebook: Disabled";

    @Override
    public void onCreate() {
        super.onCreate();
        setUpdatePackageEnabledStatusAction(UPDATE_FACEBOOK_ENABLED_STATUS_ACTION);
        setControlledPackageName(MyApplication.FACEBOOK_PACKAGE_NAME);
        setTileEnabledLabel(FACEBOOK_TILE_ENABLED_LABEL);
        setTileDisabledLabel(FACEBOOK_TILE_DISABLED_LABEL);
    }
}