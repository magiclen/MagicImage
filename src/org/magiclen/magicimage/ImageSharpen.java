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

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * 銳化圖片。
 *
 * @author Magic Len
 */
public final class ImageSharpen {

    // -----類別方法-----
    /**
     * 銳化傳入的BufferedImage物件。
     *
     * @param bi 傳入BufferedImage物件
     * @param sharpen 傳入銳化程度(大於0)，數值愈大愈銳利
     * @return 傳回銳化之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage sharpen(final BufferedImage bi, final float sharpen) {
	if (bi == null || sharpen <= 0) {
	    return null;
	}
	final BufferedImage buff = new BufferedImage(bi.getWidth(), bi.getHeight(), bi.getType());
	final float side = -sharpen / 8.0f;
	final float center = 1 + sharpen;
	final Kernel kernel = new Kernel(3, 3, new float[]{side, side, side, side, center, side, side, side, side});
	final ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
	op.filter(bi, buff);
	return buff;
    }

    // -----建構子-----
    /**
     * 私有的建構子，將無法被實體化。
     */
    private ImageSharpen() {

    }
}
