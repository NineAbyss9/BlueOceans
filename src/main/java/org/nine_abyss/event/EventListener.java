
package org.nine_abyss.event;

public interface EventListener {
    <T extends Event> void handleEvent(T event);
}
