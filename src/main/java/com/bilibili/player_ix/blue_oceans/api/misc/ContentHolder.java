
package com.bilibili.player_ix.blue_oceans.api.misc;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;

import java.util.ArrayList;

public class ContentHolder extends ArrayList<Content> {
    public ContentHolder() {
        super();
    }

    public Content get() {
        return this.get(0);
    }

    public Content last() {
        return this.get(this.size() - 1);
    }

    public boolean fill(Content content) {
        return this.add(content);
    }
}
