
package com.github.player_ix.ix_api.api;

public interface ISync {
    Synchronizer getSynchronizer();

    void loadWithSynchronizer(Synchronizer synchronizer);

    void saveSynchronizer();
}
