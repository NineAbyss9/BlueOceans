
package org.NineAbyss9.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class Manager {
    private static final StackWalker WALKER;
    private final Class<?> owner;
    private final ReentrantReadWriteLock lock;
    private final List<Object> list;
    private final Set<Object> set;

    Manager(Class<?> clazz) {
        owner = clazz;
        this.lock = new ReentrantReadWriteLock();
        this.list = new ManagerList();
        this.set = new ManagerSet();
    }

    public void lock(boolean write) {
        if (this.checkCaller(WALKER.getCallerClass())) {
            if (write)
                this.lock.writeLock().lock();
            else
                this.lock.readLock().lock();
        }
    }

    public void unlock(boolean write) {
        if (checkCaller(WALKER.getCallerClass())) {
            if (write)
                this.lock.writeLock().unlock();
            else
                this.lock.readLock().unlock();
        }
    }

    public boolean isLocked() {
        return this.lock.isWriteLocked();
    }

    public boolean add(Object object) {
        return this.list.add(object);
    }

    public boolean addSet(Object object) {
        return this.set.add(object);
    }

    private boolean checkCaller(Class<?> clazz) {
        return clazz == this.getClass() || clazz == this.owner;
    }

    static {
        WALKER = StackWalker.getInstance();
    }

    private class ManagerList extends ArrayList<Object> {
        public boolean add(Object object) {
            if (Manager.this.isLocked())
                return false;
            return super.add(object);
        }

        public boolean remove(Object o) {
            if (Manager.this.isLocked())
                return false;
            return super.remove(o);
        }
    }

    private class ManagerSet extends HashSet<Object> {
        public boolean add(Object object) {
            if (Manager.this.isLocked())
                return false;
            return super.add(object);
        }

        public boolean remove(Object o) {
            if (Manager.this.isLocked())
                return false;
            return super.remove(o);
        }
    }
}
