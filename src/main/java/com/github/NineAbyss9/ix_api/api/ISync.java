
package com.github.NineAbyss9.ix_api.api;

public interface ISync {
    Synchronizer getSynchronizer();

    void loadWithSynchronizer(Synchronizer synchronizer);

    void saveSynchronizer();
}
