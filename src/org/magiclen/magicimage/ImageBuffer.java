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

import com.icafe4j.image.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.List;
import java.util.Hashtable;
import org.apache.commons.imaging.Imaging;

/**
 *
 * 轉換各種圖片的格式。
 *
 * @author Magic Len
 */
public final class ImageBuffer {

    // -----類別方法-----
    /**
     * 複製BufferedImage物件。
     *
     * @param bi 傳入BufferedImage物件
     * @return 傳回複製的BufferedImage物件，若複製失敗，傳回null。
     */
    public static BufferedImage cloneBufferedImage(final BufferedImage bi) {
	if (bi == null) {
	    return null;
	}
	final ColorModel cm = bi.getColorModel();
	final boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
	final WritableRaster raster = bi.copyData(null);
	final Hashtable<String, Object> properties = new Hashtable<>();
	final String[] keys = bi.getPropertyNames();
	if (keys != null) {
	    for (final String key : keys) {
		properties.put(key, bi.getProperty(key));
	    }
	}
	return new BufferedImage(cm, raster, isAlphaPremultiplied, properties);
    }

    /**
     * 從圖片檔案取得BufferedImage。
     *
     * @param file 傳入圖片檔案
     * @return 傳回BufferedImage物件，若轉換失敗，傳回null。
     */
    public static BufferedImage[] getBufferedImages(final File file) {
	try {
	    final List<BufferedImage> imageList = Imaging.getAllBufferedImages(file);
	    final BufferedImage[] bufferedImages = new BufferedImage[imageList.size()];
	    imageList.toArray(bufferedImages);
	    return bufferedImages;
	} catch (final Exception ex) {
	    try {
		final BufferedImage bufferedImage = ImageIO.read(file);
		return new BufferedImage[]{bufferedImage};
	    } catch (final Exception ex2) {
		ex.printStackTrace(System.out);
		ex2.printStackTrace(System.out);
		return null;
	    }
	}
    }

    // -----建構子-----
    /**
     * 私有的建構子，將無法被實體化。
     */
    private ImageBuffer() {
    }
}
