
package com.bilibili.player_ix.blue_oceans.book;

import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import org.NineAbyss9.util.lister.Lister;
import org.NineAbyss9.util.lister.SubLister;

public class BlueBook {
    private final Lister<Page> pages;
    public BlueBook() {
        pages = new SubLister<Page>();
        Page page1 = new Page();
        addContent(page1, "Welcome to BlueOceans", Style.EMPTY.withBold(true));
        addPage(page1);
    }

    public static void addContent(Page page, String text, Style style) {
        page.addContent(FormattedCharSequence.forward(text, style));
    }

    public Lister<Page> getPages() {
        return pages;
    }

    public void addPage(Page page) {
        pages.add(page);
    }
}
