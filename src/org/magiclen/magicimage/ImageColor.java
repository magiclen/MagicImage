/*
 *
 * Copyright 2015-2017 magiclen.org
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
     * 最大化BufferedImage物件的RGB顏色。
     *
     * @param bi 傳入BufferedImage物件
     * @param keepRGBRatio 傳入是否保持RGB的比例
     * @param everyPixel 傳入是否對每個像素做最大化
     * @return 傳回最大化之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage maximize(final BufferedImage bi, final boolean keepRGBRatio, final boolean everyPixel) {
	if (bi == null) {
	    return null;
	}

	final int width = bi.getWidth(), height = bi.getHeight();

	if (everyPixel) {
	    final BufferedImage dest = ImageBuffer.cloneBufferedImage(bi);
	    if (keepRGBRatio) {
		for (int x = 0; x < width; ++x) {
		    for (int y = 0; y < height; ++y) {
			final int rgba = dest.getRGB(x, y);
			final Color color = new Color(rgba, true);

			final float r = color.getRed(), g = color.getGreen(), b = color.getBlue();
			final float rr = 255f / r, gr = 255f / g, br = 255f / b;
			final float ratio = Math.min(rr, Math.min(gr, br));

			final Color newColor = new Color(Math.round(r * ratio), Math.round(g * ratio), Math.round(b * ratio), color.getAlpha());
			dest.setRGB(x, y, newColor.getRGB());
		    }
		}
	    } else {
		for (int x = 0; x < width; ++x) {
		    for (int y = 0; y < height; ++y) {
			final int rgba = dest.getRGB(x, y);
			final Color color = new Color(rgba, true);

			final Color newColor = new Color(255, 255, 255, color.getAlpha());
			dest.setRGB(x, y, newColor.getRGB());
		    }
		}
	    }
	    return dest;
	} else {
	    float minR = Float.POSITIVE_INFINITY, minG = Float.POSITIVE_INFINITY, minB = Float.POSITIVE_INFINITY;

	    for (int x = 0; x < width; ++x) {
		for (int y = 0; y < height; ++y) {
		    final int rgba = bi.getRGB(x, y);
		    final Color color = new Color(rgba, true);
		    final float r = color.getRed(), g = color.getGreen(), b = color.getBlue();
		    final float rr = 255f / r, gr = 255f / g, br = 255f / b;
		    if (minR > rr) {
			minR = rr;
		    }
		    if (minG > gr) {
			minG = gr;
		    }
		    if (minB > br) {
			minB = br;
		    }
		}
	    }
	    if (keepRGBRatio) {
		final float ratio = Math.min(minR, Math.min(minG, minB));
		minR = ratio;
		minG = ratio;
		minB = ratio;
	    }
	    if (Float.isInfinite(minR) || Float.isInfinite(minG) || Float.isInfinite(minB)) {
		return bi;
	    }
	    final BufferedImage dest = ImageBuffer.cloneBufferedImage(bi);
	    for (int x = 0; x < width; ++x) {
		for (int y = 0; y < height; ++y) {
		    final int rgba = dest.getRGB(x, y);
		    final Color color = new Color(rgba, true);
		    final float r = color.getRed(), g = color.getGreen(), b = color.getBlue();

		    final Color newColor = new Color(Math.round(r * minR), Math.round(g * minG), Math.round(b * minB), color.getAlpha());
		    dest.setRGB(x, y, newColor.getRGB());
		}
	    }
	    return dest;
	}
    }

    /**
     * 相加兩BufferedImage物件，兩張圖片的尺寸必須要一樣。
     *
     * @param bi1 傳入第一個BufferedImage物件
     * @param bi2 傳入第二個BufferedImage物件
     * @return 傳回相加之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage union(final BufferedImage bi1, final BufferedImage bi2) {
	if (bi1 == null || bi2 == null) {
	    return null;
	}
	final int width = bi1.getWidth(), height = bi1.getHeight();
	if (width != bi2.getWidth() || height != bi2.getHeight()) {
	    return null;
	}
	final BufferedImage dest = ImageBuffer.cloneBufferedImage(bi1);

	for (int x = 0; x < width; ++x) {
	    for (int y = 0; y < height; ++y) {
		final int rgba1 = dest.getRGB(x, y);
		final int rgba2 = bi2.getRGB(x, y);
		final Color color1 = new Color(rgba1, true);
		final Color color2 = new Color(rgba2, true);

		final Color newColor = new Color(Math.min(color1.getRed() + color2.getRed(), 255), Math.min(color1.getGreen() + color2.getGreen(), 255), Math.min(color1.getBlue() + color2.getBlue(), 255), Math.min(color1.getAlpha() + color2.getAlpha(), 255));
		dest.setRGB(x, y, newColor.getRGB());
	    }
	}
	return dest;
    }

    /**
     * 相減兩BufferedImage物件，兩張圖片的尺寸必須要一樣。
     *
     * @param bi1 傳入第一個BufferedImage物件
     * @param bi2 傳入第二個BufferedImage物件
     * @return 傳回差異化之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage distinct(final BufferedImage bi1, final BufferedImage bi2) {
	if (bi1 == null || bi2 == null) {
	    return null;
	}
	final int width = bi1.getWidth(), height = bi1.getHeight();
	if (width != bi2.getWidth() || height != bi2.getHeight()) {
	    return null;
	}
	final BufferedImage dest = ImageBuffer.cloneBufferedImage(bi1);

	for (int x = 0; x < width; ++x) {
	    for (int y = 0; y < height; ++y) {
		final int rgba1 = dest.getRGB(x, y);
		final int rgba2 = bi2.getRGB(x, y);
		final Color color1 = new Color(rgba1, true);
		final Color color2 = new Color(rgba2, true);

		final Color newColor = new Color(Math.abs(color1.getRed() - color2.getRed()), Math.abs(color1.getGreen() - color2.getGreen()), Math.abs(color1.getBlue() - color2.getBlue()), Math.abs(color1.getAlpha() - color2.getAlpha()));
		dest.setRGB(x, y, newColor.getRGB());
	    }
	}
	return dest;
    }

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
