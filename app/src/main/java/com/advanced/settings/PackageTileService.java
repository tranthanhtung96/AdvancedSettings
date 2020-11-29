package com.advanced.settings;

import android.content.Intent;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class PackageTileService extends TileService {
    private String updatePackageEnabledStatusAction;
    private String controlledPackageName;
    private String tileEnabledLabel;
    private String tileDisabledLabel;

    private boolean packageEnabledStatus;
    private Tile packageTile;
    private MyApplication myApp;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    public void onStartListening() {
        super.onStartListening();
        packageTile = getQsTile();
        myApp = (MyApplication) getApplication();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        updateTile();
        updateCorrespondingSwitch();
    }

    @Override
    public void onClick() {
        super.onClick();
        if (packageEnabledStatus) {
            myApp.disablePackage(controlledPackageName);
        } else {
            myApp.enablePackage(controlledPackageName);
        }
        updateTile();
        updateCorrespondingSwitch();
    }

    private void updateTile() {
        packageEnabledStatus = myApp.getPackageEnabledStatus(controlledPackageName);
        if (packageEnabledStatus) {
            packageTile.setState(Tile.STATE_ACTIVE);
            packageTile.setLabel(tileEnabledLabel);
        } else {
            packageTile.setState(Tile.STATE_INACTIVE);
            packageTile.setLabel(tileDisabledLabel);
        }

        packageTile.updateTile();
    }

    private void updateCorrespondingSwitch() {
        if (FacebookFragment.isRunning()) {
            Intent intent = new Intent(updatePackageEnabledStatusAction);
            localBroadcastManager.sendBroadcast(intent);
        }
    }

    public void setUpdatePackageEnabledStatusAction(String updatePackageEnabledStatusAction) {
        this.updatePackageEnabledStatusAction = updatePackageEnabledStatusAction;
    }

    public void setControlledPackageName(String controlledPackageName) {
        this.controlledPackageName = controlledPackageName;
    }

    public void setTileEnabledLabel(String tileEnabledLabel) {
        this.tileEnabledLabel = tileEnabledLabel;
    }

    public void setTileDisabledLabel(String tileDisabledLabel) {
        this.tileDisabledLabel = tileDisabledLabel;
    }
}
