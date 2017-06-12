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
import java.util.Arrays;

/**
 * 模糊化圖片。
 *
 * @author Magic Len
 *
 */
public class ImageBlurring {

    // -----類別方法-----
    /**
     * 模糊化傳入的BufferedImage物件，將會重複進行數次模糊化。
     *
     * @param bi 傳入BufferedImage物件
     * @param blur 傳入模糊化程度(大於0)，數值愈大愈模糊
     * @return 傳回模糊化化之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage blur(final BufferedImage bi, final int blur) {
	return blur(bi, blur, true);
    }

    /**
     * 模糊化傳入的BufferedImage物件。
     *
     * @param bi 傳入BufferedImage物件
     * @param blur 傳入模糊化程度(大於0)，數值愈大愈模糊
     * @param repeat 使用重複模糊化，避免邊緣問題
     * @return 傳回模糊化化之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage blur(final BufferedImage bi, final int blur, final boolean repeat) {
	if (bi == null || blur <= 0) {
	    return null;
	}
	final int width = bi.getWidth(), height = bi.getHeight();
	final int type = bi.getType();
	BufferedImage buff = new BufferedImage(width, height, type);

	final int n = blur;
	if (repeat) {
	    final float[] matrix = new float[9];
	    Arrays.fill(matrix, 1f / 9);
	    final Kernel kernel = new Kernel(3, 3, matrix);
	    final ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
	    op.filter(bi, buff);
	    for (int i = 1; i < n; ++i) {
		final BufferedImage temp = new BufferedImage(width, height, type);
		op.filter(buff, temp);
		buff.flush();
		buff = temp;
	    }
	} else {
	    final int nn = n * n;
	    final float[] matrix = new float[nn];
	    Arrays.fill(matrix, 1f / nn);
	    final Kernel kernel = new Kernel(n, n, matrix);
	    final ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
	    op.filter(bi, buff);
	}
	return buff;
    }

    /**
     * 高斯模糊化傳入的BufferedImage物件，將會重複進行數次高斯模糊化。
     *
     * @param bi 傳入BufferedImage物件
     * @param blur 傳入模糊化程度(大於0)，數值愈大愈模糊
     * @return 傳回高斯模糊化化之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage gaussianBlur(final BufferedImage bi, final int blur) {
	return gaussianBlur(bi, blur, true);
    }

    /**
     * 高斯模糊化傳入的BufferedImage物件。
     *
     * @param bi 傳入BufferedImage物件
     * @param blur 傳入模糊化程度(大於0)，數值愈大愈模糊
     * @param repeat 使用重複模糊化，避免邊緣問題
     * @return 傳回高斯模糊化化之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage gaussianBlur(final BufferedImage bi, final int blur, final boolean repeat) {
	if (bi == null || blur <= 0) {
	    return null;
	}
	final int width = bi.getWidth(), height = bi.getHeight();
	final int type = bi.getType();
	BufferedImage buff = new BufferedImage(width, height, type);

	if (repeat) {
	    final int n = blur;
	    final float[] matrix = new float[]{0.0625f, 0.125f, 0.0625f, 0.125f, 0.25f, 0.125f, 0.0625f, 0.125f, 0.0625f};
	    final Kernel kernel = new Kernel(3, 3, matrix);
	    final ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
	    op.filter(bi, buff);
	    for (int i = 1; i < n; ++i) {
		final BufferedImage temp = new BufferedImage(width, height, type);
		op.filter(buff, temp);
		buff.flush();
		buff = temp;
	    }
	} else {
	    final int n = 2 * blur + 1;
	    final int nn = n * n;
	    final float[] values = new float[n];
	    for (int i = 0; i < n; ++i) {
		values[i] = (float) Math.pow(2, i);
	    }
	    final float[] matrix = new float[nn];
	    float sum = 0;
	    for (int i = 0; i <= blur; ++i) {
		for (int j = 0; j <= blur; ++j) {
		    final float value = values[(i + j) % n];
		    matrix[i * n + j] = value;
		    sum += value;
		}
		for (int j = blur + 1; j < n; ++j) {
		    final float value = values[n - 1 - ((j - i) % n)];
		    matrix[i * n + j] = value;
		    sum += value;
		}
	    }
	    for (int i = blur + 1; i < n; ++i) {
		for (int j = 0; j <= blur; ++j) {
		    final float value = values[(n - i - 1 + j) % n];
		    matrix[i * n + j] = value;
		    sum += value;
		}
		for (int j = blur + 1; j < n; ++j) {
		    final float value = values[n - 1 - ((j - n + i + 1) % n)];
		    matrix[i * n + j] = value;
		    sum += value;
		}
	    }
	    for (int i = 0; i < nn; ++i) {
		matrix[i] /= sum;
	    }
	    final Kernel kernel = new Kernel(n, n, matrix);
	    final ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
	    op.filter(bi, buff);
	}
	return buff;
    }

    // -----建構子-----
    /**
     * 私有的建構子，將無法被實體化。
     */
    private ImageBlurring() {

    }
}
