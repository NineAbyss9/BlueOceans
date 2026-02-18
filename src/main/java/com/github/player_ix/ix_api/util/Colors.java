
package com.github.player_ix.ix_api.util;

import org.NineAbyss9.annotation.PFMAreNonnullByDefault;

import java.awt.*;

/**@author Player_IX
 */
@PFMAreNonnullByDefault
@SuppressWarnings("unused")
public class Colors {
    public static final float[] RED = new float[] {
            0.7F, 0.3F, 0.3F
    };
    public static final float[] LIGHT_RED = new float[] {
            1, 0, 0
    };
    public static final float[] DARK_RED = new float[] {
            0.4F, 0.0F, 0.0F
    };
    public static final float[] GREEN = new float[] {
            0.3F, 0.7F, 0.3F
    };
    public static final float[] BLUE = new float[] {
            0.3F, 0.3F, 0.7F
    };
    public static final float[] LIGHT_BLUE = new float[] {
            0.3F, 0.7F, 0.7F
    };
    public static final float[] PURPLE = new float[] {
            0.7F, 0.3F, 0.7F
    };
    public static final float[] DARK_PURPLE = new float[] {
            0.3F, 0, 0.3F
    };
    public static final float[] LIGHT_PURPLE = new float[] {
            1, 0, 1
    };

    public Colors() {
    }

    public static double[] toDouble(float[] floats) {
        return new double[] {
                floats[0], floats[1], floats[2]
        };
    }

    public static Color newColor(int colorCode) {
        return Color.decode(Integer.toString(colorCode));
    }

    public static Color hexToColor(String hex) {
        String color = hex.replace("#", "").replace("0x", "");
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new Color(r, g, b);
    }

    public static int toColorCode(String hex) {
        return hexToColor(hex).getRGB();
    }
}
