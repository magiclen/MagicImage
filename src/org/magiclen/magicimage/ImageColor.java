/*
 *
 * Copyright 2015-2016 magiclen.org
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.magiclen.magicimage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

/**
 * 圖片顏色。
 *
 * @see ImageBuffer
 * @author Magic Len
 */
public final class ImageColor {

    // -----類別方法-----
    /**
     * 將BufferedImage物件的RGB顏色轉成互補色。
     *
     * @param bi 傳入BufferedImage物件
     * @return 傳回互補化之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage negative(final BufferedImage bi) {
	return negative(bi, true);
    }

    /**
     * 將BufferedImage物件的顏色轉成互補色。
     *
     * @param bi 傳入BufferedImage物件
     * @param exceptAlpha 傳入是否排除透明值
     * @return 傳回互補化之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage negative(final BufferedImage bi, final boolean exceptAlpha) {
	if (bi == null) {
	    return null;
	}
	final BufferedImage dest = ImageBuffer.cloneBufferedImage(bi);
	final int width = dest.getWidth(), height = dest.getHeight();

	if (exceptAlpha) {
	    for (int x = 0; x < width; ++x) {
		for (int y = 0; y < height; ++y) {
		    final int rgba = dest.getRGB(x, y);
		    final Color color = new Color(rgba, true);

		    final Color negativeColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), color.getAlpha());
		    dest.setRGB(x, y, negativeColor.getRGB());
		}
	    }
	} else {
	    for (int x = 0; x < width; ++x) {
		for (int y = 0; y < height; ++y) {
		    final int rgba = dest.getRGB(x, y);
		    final Color color = new Color(rgba, true);

		    final Color negativeColor = new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue(), 255 - color.getAlpha());
		    dest.setRGB(x, y, negativeColor.getRGB());
		}
	    }
	}
	return dest;
    }

    /**
     * 將BufferedImage物件轉成灰階。
     *
     * @param bi 傳入BufferedImage物件
     * @return 傳回灰階之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage gray(final BufferedImage bi) {
	if (bi == null) {
	    return null;
	}
	final int width = bi.getWidth(), height = bi.getHeight();
	final BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
	final Graphics2D graphics = dest.createGraphics();
	graphics.setBackground(Color.BLACK);
	graphics.clearRect(0, 0, width, height);
	graphics.dispose();

	final ColorConvertOp colorConv = new ColorConvertOp(bi.getColorModel().getColorSpace(), dest.getColorModel().getColorSpace(), null);
	colorConv.filter(bi, dest);
	return dest;
    }

    /**
     * 將BufferedImage物件二值化。
     *
     * @param bi 傳入BufferedImage物件
     * @return 傳回二值化之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage binary(final BufferedImage bi) {
	if (bi == null) {
	    return null;
	}
	final int width = bi.getWidth(), height = bi.getHeight();
	final BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
	final Graphics2D graphics = dest.createGraphics();
	graphics.setBackground(Color.BLACK);
	graphics.clearRect(0, 0, width, height);
	graphics.dispose();

	final ColorConvertOp colorConv = new ColorConvertOp(bi.getColorModel().getColorSpace(), dest.getColorModel().getColorSpace(), null);
	colorConv.filter(bi, dest);
	return dest;
    }

    // -----建構子-----
    /**
     * 私有的建構子，將無法被實體化。
     */
    private ImageColor() {

    }
}
