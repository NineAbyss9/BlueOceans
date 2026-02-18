
package com.bilibili.player_ix.blue_oceans.book;

import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import org.NineAbyss9.util.lister.Lister;
import org.NineAbyss9.util.lister.SubLister;

public class BlueBook {
    private final Lister<Page> pages;
    public BlueBook() {
        pages = new SubLister<>();
        Page page1 = new Page();
        page1.addContent(FormattedCharSequence.forward("Welcome to BlueOceans",
                Style.EMPTY));
        addPage(page1);
    }

    public Lister<Page> getPages() {
        return pages;
    }

    public void addPage(Page page) {
        pages.add(page);
    }
}
