
package com.bilibili.player_ix.blue_oceans.book;

import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

import java.util.ArrayList;
import java.util.List;

public class BlueBook {
    private final List<Page> pages;
    public BlueBook() {
        pages = new ArrayList<>();
        Page page1 = new Page();
        page1.addContent(FormattedCharSequence.forward("Welcome to BlueOceans",
                Style.EMPTY));
        addPage(page1);
    }

    public List<Page> getPages() {
        return pages;
    }

    public void addPage(Page page) {
        pages.add(page);
    }
}
