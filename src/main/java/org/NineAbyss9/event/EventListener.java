
package org.NineAbyss9.event;

public interface EventListener {
    <T extends Event> void handleEvent(T event);
}
