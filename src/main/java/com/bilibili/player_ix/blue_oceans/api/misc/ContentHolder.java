
package com.bilibili.player_ix.blue_oceans.api.misc;

import com.bilibili.player_ix.blue_oceans.common.chemistry.Content;
import org.NineAbyss9.value_holder.BooleanValueHolder;

import java.util.ArrayList;

public class ContentHolder extends ArrayList<Content> {
    @java.io.Serial
    private static final long serialVersionUID = 1215056449800105334L;

    public ContentHolder() {
        super();
    }

    public Content first() {
        return this.get(0);
    }

    public Content last() {
        return this.get(this.size() - 1);
    }

    public BooleanValueHolder<Content> fill(Content content) {
        return new BooleanValueHolder<>(this.add(content), content);
    }
}
