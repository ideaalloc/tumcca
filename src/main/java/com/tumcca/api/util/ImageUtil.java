package com.tumcca.api.util;

import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;

/**
 * Title.
 * <p>
 * Description.
 *
 * @author Bill Lv {@literal <billcc.lv@hotmail.com>}
 * @version 1.0
 * @since 2015-03-23
 */
public class ImageUtil {
    public static BufferedImage createThumbnail(BufferedImage img) {
        img = Scalr.resize(img, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
                150, 100, Scalr.OP_ANTIALIAS);
        // Let's add a little border before we return result.
        // return pad(img, 4);
        return img;
    }
}
