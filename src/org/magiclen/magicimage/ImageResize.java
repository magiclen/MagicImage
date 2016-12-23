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

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.ResampleOp;
import java.awt.image.BufferedImage;

/**
 * 調整圖片大小。
 *
 * @author Magic Len
 * @see ImageSharpen
 */
public final class ImageResize {

    // -----類別方法-----
    /**
     * 縮小傳入的BufferedImage物件。
     *
     * @param bi 傳入BufferedImage物件
     * @param maxSide 傳入最大邊要修改成的長度，自動依照比例計算實際另一邊的長度
     * @param sharpen 傳入JPEG的銳化程度(0~10)，愈大愈銳利，建議值為0~1.5；若小於0，則自動計算銳化程度
     * @return 傳回未縮小或是縮小之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage shrink(final BufferedImage bi, final int maxSide, final float sharpen) {
        if (bi == null || maxSide <= 0) {
            return null;
        }
        final int originalWidth = bi.getWidth();
        final int originalHeight = bi.getHeight();
        if (originalWidth > originalHeight && originalWidth > maxSide) {
            return resize(bi, originalWidth, originalHeight, maxSide, -1, sharpen, true);
        } else if (originalWidth < originalHeight && originalHeight > maxSide) {
            return resize(bi, originalWidth, originalHeight, -1, maxSide, sharpen, true);
        } else {
            return bi;
        }
    }

    /**
     * 縮小傳入的BufferedImage物件。
     *
     * @param bi 傳入BufferedImage物件
     * @param maxWidth 傳入最大的寬度
     * @param maxHeight 傳入最大的高度
     * @param sharpen 傳入JPEG的銳化程度(0~10)，愈大愈銳利，建議值為0~1.5；若小於0，則自動計算銳化程度
     * @return 傳回未縮小或是縮小之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage shrink(final BufferedImage bi, int maxWidth, int maxHeight, final float sharpen) {
        if (bi == null || (maxWidth <= 0 && maxHeight <= 0)) {
            return null;
        }

        final int originalWidth = bi.getWidth();
        final int originalHeight = bi.getHeight();

        if (maxWidth <= 0) {
            maxWidth = originalWidth;
        }
        if (maxHeight <= 0) {
            maxHeight = originalHeight;
        }

        final float wRatio = maxWidth * 1.0f / originalWidth;
        final float hRatio = maxHeight * 1.0f / originalHeight;
        if (wRatio < hRatio && originalWidth > maxWidth) {
            return resize(bi, originalWidth, originalHeight, maxWidth, -1, sharpen, true);
        } else if (wRatio > hRatio && originalHeight > maxHeight) {
            return resize(bi, originalWidth, originalHeight, -1, maxHeight, sharpen, true);
        } else {
            return bi;
        }
    }

    /**
     * 重新縮放傳入的BufferedImage物件。
     *
     * @param bi 傳入BufferedImage物件
     * @param maxSide 傳入最大邊要修改成的長度，自動依照比例計算實際另一邊的長度
     * @param sharpen 傳入JPEG的銳化程度(0~10)，愈大愈銳利，建議值為0~1.5；若小於0，則自動計算銳化程度
     * @param onlyShrink 傳入是否僅進行縮圖
     * @param sharpenOnlyShrink 傳入是否只在縮圖時使用銳化，僅在onlyShrink為false時有效用
     * @return 傳回未縮放或是重新縮放之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage resize(final BufferedImage bi, final int maxSide, final float sharpen, final boolean onlyShrink, final boolean sharpenOnlyShrink) {
        if (bi == null || maxSide <= 0) {
            return null;
        }
        final int originalWidth = bi.getWidth();
        final int originalHeight = bi.getHeight();
        if (originalWidth > originalHeight && (!onlyShrink || originalWidth > maxSide)) {
            return resize(bi, originalWidth, originalHeight, maxSide, -1, sharpen, sharpenOnlyShrink);
        } else if (originalWidth < originalHeight && (!onlyShrink || originalHeight > maxSide)) {
            return resize(bi, originalWidth, originalHeight, -1, maxSide, sharpen, sharpenOnlyShrink);
        } else {
            return bi;
        }
    }

    /**
     * 重新縮放傳入的BufferedImage物件。
     *
     * @param bi 傳入BufferedImage物件
     * @param width 傳入要修改成的寬度，若小於等於0，則依照比例自動計算
     * @param height 傳入要修改成的高度，若小於等於0，則依照比例自動計算
     * @param sharpen 傳入JPEG的銳化程度(0~10)，愈大愈銳利，建議值為0~1.5；若小於0，則自動計算銳化程度
     * @param sharpenOnlyShrink 傳入是否只在縮圖時使用銳化
     * @return 傳回重新縮放之後的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage resize(final BufferedImage bi, final int width, final int height, final float sharpen, final boolean sharpenOnlyShrink) {
        if (bi == null || (width <= 0 && height <= 0)) {
            return null;
        }
        final int originalWidth = bi.getWidth();
        final int originalHeight = bi.getHeight();

        return resize(bi, originalWidth, originalHeight, width, height, sharpen, sharpenOnlyShrink);
    }

    /**
     * 重新縮放傳入的BufferedImage物件。
     *
     * @param bi 傳入BufferedImage物件
     * @param originalWidth 傳入原始圖片的寬度
     * @param originalHeight 傳入原始圖片的高度
     * @param width 傳入要修改成的寬度，若小於等於0，則依照比例自動計算
     * @param height 傳入要修改成的高度，若小於等於0，則依照比例自動計算
     * @param sharpen 傳入JPEG的銳化程度(0~10)，愈大愈銳利，建議值為0~1.5；若小於0，則自動計算銳化程度
     * @param sharpenOnlyShrink 傳入是否只在縮圖時使用銳化
     * @return 傳回重新縮放之後的新的BufferedImage物件，若失敗，則傳回null
     */
    private static BufferedImage resize(final BufferedImage bi, final int originalWidth, final int originalHeight, final int width, final int height, final float sharpen, final boolean sharpenOnlyShrink) {
        assert (bi != null && (width > 0 || height > 0));

        BufferedImage result;

        final float ratio = originalWidth * 1.0f / originalHeight;
        final int resizeWidth, resizeHeight;
        if (width > 0) {
            resizeWidth = width;
        } else {
            resizeWidth = Math.round(height * ratio);
        }
        if (height > 0) {
            resizeHeight = height;
        } else {
            resizeHeight = Math.round(width / ratio);
        }

        // 縮放
        final ResampleOp resampleOp = new ResampleOp(resizeWidth, resizeHeight);
        resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.None);
        final BufferedImage scaledBi = resampleOp.filter(bi, null); // 縮放原始的BufferedImage
        result = scaledBi;

        // 銳化
        final float originPixels = originalWidth * originalHeight, resizePixels = resizeWidth * resizeHeight; // 計算解析度之乘積
        final boolean isShrink = originPixels > resizePixels; // 判斷是否正在縮圖

        if (!sharpenOnlyShrink || isShrink) { //若正在縮圖，且只在縮圖時銳利化，或是隨時都可以銳利化時，則進行銳利化。
            float adjustSharpen = sharpen;
            if (adjustSharpen < 0) { // 銳利度小於0，計算銳利度
                // 計算原圖尺寸與縮放尺寸的差異，和縮放尺寸的大小之關聯
                final float pixelRatio, resizeLevel = resizePixels / 1049088;
                if (originPixels == resizePixels) {
                    pixelRatio = 1;
                } else if (originPixels > resizePixels) {
                    pixelRatio = originPixels / resizePixels;
                } else {
                    pixelRatio = resizePixels / originPixels;
                }
                if (pixelRatio >= 8.5) {
                    adjustSharpen = 1.1f + (resizeLevel - 1) / 1.8f;
                } else if (pixelRatio >= 7.5) {
                    adjustSharpen = 1 + (resizeLevel - 1) / 2;
                } else {
                    adjustSharpen = 0.9f + (resizeLevel - 1) / 2.2f;
                }

                if (adjustSharpen > 1.5f) {
                    adjustSharpen = 1.5f;
                }
            }
            if (adjustSharpen > 0) { // 銳利度大於0，進行銳利化
                result = ImageSharpen.sharpen(scaledBi, adjustSharpen);
                scaledBi.flush();
            }
        }
        return result;
    }

    /**
     * 剪裁傳入的BufferedImage物件。
     *
     * @param bi 傳入BufferedImage物件
     * @param width 傳入要剪裁的寬度
     * @param height 傳入要剪裁的高度
     * @param x 傳入要剪裁的寬度起點
     * @param y 傳入要剪裁的高度起點
     * @return 傳回剪裁出來的新的BufferedImage物件，若失敗，則傳回null
     */
    public static BufferedImage crop(final BufferedImage bi, final int width, final int height, final int x, final int y) {
        if (bi == null || (width <= 0 && height <= 0)) {
            return null;
        }

        final int originalWidth = bi.getWidth();
        final int originalHeight = bi.getHeight();

        if (x >= originalWidth || y >= originalHeight || x + width < 0 || y + height < 0) { //若定位點無法形成圖片
            return null;
        }

        int cropWidth = width;

        if (cropWidth + x > originalWidth) { // 若太寬
            cropWidth = originalWidth - x;
        }

        int cropHeight = height;

        if (cropHeight + y > originalHeight) { // 若太高
            cropHeight = originalHeight - y;
        }

        int cropX = x;
        if (cropX < 0) {
            cropX = 0;
        }

        int cropY = y;
        if (cropY < 0) {
            cropY = 0;
        }

        final BufferedImage cropImage = bi.getSubimage(cropX, cropY, cropWidth, cropHeight);

        return cropImage;
    }

    // -----建構子-----
    /**
     * 私有的建構子，將無法被實體化。
     */
    private ImageResize() {

    }
}
