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
import com.icafe4j.image.ImageParam;
import com.icafe4j.image.ImageType;
import com.icafe4j.image.options.BMPOptions;
import com.icafe4j.image.options.GIFOptions;
import com.icafe4j.image.options.JPEGOptions;
import com.icafe4j.image.options.PNGOptions;
import com.icafe4j.image.options.TIFFOptions;
import com.icafe4j.image.tiff.TiffFieldEnum;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * 輸出圖片。
 *
 * @author Magic Len
 */
public final class ImageExport {

    // -----類別方法-----
    /**
     * 將BufferedImage物件輸出成檔案，背景顏色為白色。
     *
     * @param bi 傳入要輸出的BufferedImage物件
     * @param outputFile 傳入要輸出的檔案物件，副檔名必須為JPG或是JPEG，否則會自動重新命名
     * @param quality 傳入JPEG的壓縮品質(0~1)，愈小畫質愈差
     * @param overwrite 傳入是否覆蓋已存在的檔案，若不覆蓋，則自動重新命名輸出的檔名
     * @return 若有輸出成功，傳回新的檔案物件，否則傳回null
     */
    public static File exportToJPEG(final BufferedImage bi, final File outputFile, final float quality, final boolean overwrite) {
	return exportToJPEG(bi, outputFile, quality, overwrite, Color.WHITE);
    }

    /**
     * 將BufferedImage物件輸出成JPG圖片檔案。
     *
     * @param bi 傳入要輸出的BufferedImage物件
     * @param outputFile 傳入要輸出的檔案物件，副檔名必須為JPG或是JPEG，否則會自動重新命名
     * @param quality 傳入JPEG的壓縮品質(0~1)，愈小畫質愈差
     * @param overwrite 傳入是否覆蓋已存在的檔案，若不覆蓋，則自動重新命名輸出的檔名
     * @param backgroundColor 傳入背景顏色
     * @return 若有輸出成功，傳回新的檔案物件，否則傳回null
     */
    public static File exportToJPEG(final BufferedImage bi, final File outputFile, final float quality, final boolean overwrite, final Color backgroundColor) {
	if (bi == null || outputFile == null || quality < 0 || quality > 1 || backgroundColor == null) {
	    return null;
	}
	//調整輸出檔案路徑
	final File adjustOutputFile = adjustOutputFile(outputFile, overwrite, "jpg", new String[]{"jpg", "jpeg"});

	final int width = bi.getWidth(), height = bi.getHeight();
	final BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
	final Graphics2D graphics = dest.createGraphics();
	graphics.setBackground(backgroundColor);
	graphics.clearRect(0, 0, width, height);
	graphics.dispose();

	final ColorConvertOp colorConv = new ColorConvertOp(bi.getColorModel().getColorSpace(), dest.getColorModel().getColorSpace(), null);
	colorConv.filter(bi, dest);
	try {
	    final JPEGOptions jpegOptions = new JPEGOptions();
	    jpegOptions.setQuality(Math.round(quality * 100));
	    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(adjustOutputFile))) {
		ImageIO.write(dest, bos, ImageType.JPG, ImageParam.getBuilder().imageOptions(jpegOptions).build());
		bos.flush();
	    }

	    dest.flush();  // 釋放記憶體
	    return adjustOutputFile;
	} catch (final Exception ex) {
	    ex.printStackTrace(System.out);
	    return null;
	}
    }

    /**
     * 將BufferedImage物件輸出成TIFF圖片檔案。
     *
     * @param bi 傳入要輸出的BufferedImage物件
     * @param outputFile 傳入要輸出的檔案物件，副檔名必須為TIFF，否則會自動重新命名
     * @param quality 傳入TIFF的壓縮品質(0~1)，愈小畫質愈差
     * @param lossless 傳入是否使用無損壓縮
     * @param overwrite 傳入是否覆蓋已存在的檔案，若不覆蓋，則自動重新命名輸出的檔名
     * @return 若有輸出成功，傳回新的檔案物件，否則傳回null
     */
    public static File exportToTIFF(final BufferedImage bi, final File outputFile, final float quality, final boolean lossless, final boolean overwrite) {
	return exportToTIFF(bi, outputFile, quality, lossless, overwrite, Color.WHITE);
    }

    /**
     * 將BufferedImage物件輸出成TIFF圖片檔案。
     *
     * @param bi 傳入要輸出的BufferedImage物件
     * @param outputFile 傳入要輸出的檔案物件，副檔名必須為TIFF，否則會自動重新命名
     * @param compressionquality 傳入TIFF的壓縮品質(0~1)，若不是無損壓縮，愈小畫質愈差
     * @param lossless 傳入是否使用無損壓縮
     * @param overwrite 傳入是否覆蓋已存在的檔案，若不覆蓋，則自動重新命名輸出的檔名
     * @param backgroundColor 傳入背景顏色
     * @return 若有輸出成功，傳回新的檔案物件，否則傳回null
     */
    public static File exportToTIFF(final BufferedImage bi, final File outputFile, final float compressionquality, final boolean lossless, final boolean overwrite, final Color backgroundColor) {
	if (bi == null || outputFile == null || compressionquality < 0 || compressionquality > 1 || backgroundColor == null) {
	    return null;
	}
	//調整輸出檔案路徑
	final File adjustOutputFile = adjustOutputFile(outputFile, overwrite, "tiff", new String[]{"tiff"});

	final int width = bi.getWidth(), height = bi.getHeight();
	final BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
	final Graphics2D graphics = dest.createGraphics();
	graphics.setBackground(backgroundColor);
	graphics.clearRect(0, 0, width, height);
	graphics.dispose();

	final ColorConvertOp colorConv = new ColorConvertOp(bi.getColorModel().getColorSpace(), dest.getColorModel().getColorSpace(), null);
	colorConv.filter(bi, dest);
	try {
	    final TIFFOptions tiffOptions = new TIFFOptions();
	    if(lossless){
		tiffOptions.setTiffCompression(TiffFieldEnum.Compression.DEFLATE);
		tiffOptions.setDeflateCompressionLevel(Math.round(9.0f - compressionquality * 9));
	    }else{
		tiffOptions.setTiffCompression(TiffFieldEnum.Compression.JPG);
		tiffOptions.setJPEGQuality(Math.round(compressionquality * 100));
	    }
	    
	    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(adjustOutputFile))) {
		ImageIO.write(dest, bos, ImageType.TIFF, ImageParam.getBuilder().imageOptions(tiffOptions).build());
		bos.flush();
	    }
	    dest.flush();  // 釋放記憶體
	    return adjustOutputFile;
	} catch (final Exception ex) {
	    ex.printStackTrace(System.out);
	    return null;
	}
    }

    /**
     * 將BufferedImage物件輸出成BMP圖片檔案。
     *
     * @param bi 傳入要輸出的BufferedImage物件
     * @param outputFile 傳入要輸出的檔案物件，副檔名必須為BMP，否則會自動重新命名
     * @param overwrite 傳入是否覆蓋已存在的檔案，若不覆蓋，則自動重新命名輸出的檔名
     * @return 若有輸出成功，傳回新的檔案物件，否則傳回null
     */
    public static File exportToBMP(final BufferedImage bi, final File outputFile, final boolean overwrite) {
	return exportToBMP(bi, outputFile, overwrite, Color.WHITE);
    }

    /**
     * 將BufferedImage物件輸出成BMP圖片檔案。
     *
     * @param bi 傳入要輸出的BufferedImage物件
     * @param outputFile 傳入要輸出的檔案物件，副檔名必須為BMP，否則會自動重新命名
     * @param overwrite 傳入是否覆蓋已存在的檔案，若不覆蓋，則自動重新命名輸出的檔名
     * @param backgroundColor 傳入背景顏色
     * @return 若有輸出成功，傳回新的檔案物件，否則傳回null
     */
    public static File exportToBMP(final BufferedImage bi, final File outputFile, final boolean overwrite, final Color backgroundColor) {
	if (bi == null || outputFile == null || backgroundColor == null) {
	    return null;
	}
	//調整輸出檔案路徑
	final File adjustOutputFile = adjustOutputFile(outputFile, overwrite, "bmp", new String[]{"bmp"});

	final int width = bi.getWidth(), height = bi.getHeight();
	final BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	final Graphics2D graphics = dest.createGraphics();
	graphics.setBackground(backgroundColor);
	graphics.clearRect(0, 0, width, height);
	graphics.dispose();

	final ColorConvertOp colorConv = new ColorConvertOp(bi.getColorModel().getColorSpace(), dest.getColorModel().getColorSpace(), null);
	colorConv.filter(bi, dest);
	try {
	    final BMPOptions bmpOptions = new BMPOptions();
	    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(adjustOutputFile))) {
		ImageIO.write(dest, bos, ImageType.BMP, ImageParam.getBuilder().imageOptions(bmpOptions).build());
		bos.flush();
	    }
	    dest.flush();  // 釋放記憶體
	    return adjustOutputFile;
	} catch (final Exception ex) {
	    ex.printStackTrace(System.out);
	    return null;
	}
    }

    /**
     * 將BufferedImage物件輸出成PNG圖片檔案。
     *
     * @param bi 傳入要輸出的BufferedImage物件
     * @param outputFile 傳入要輸出的檔案物件，副檔名必須為PNG，否則會自動重新命名
     * @param overwrite 傳入是否覆蓋已存在的檔案，若不覆蓋，則自動重新命名輸出的檔名
     * @return 若有輸出成功，傳回新的檔案物件，否則傳回null
     */
    public static File exportToPNG(final BufferedImage bi, final File outputFile, final boolean overwrite) {
	return exportToPNG(bi, outputFile, 0, overwrite);
    }

    /**
     * 將BufferedImage物件輸出成PNG圖片檔案。
     *
     * @param bi 傳入要輸出的BufferedImage物件
     * @param outputFile 傳入要輸出的檔案物件，副檔名必須為PNG，否則會自動重新命名
     * @param compressionQuality 傳入PNG的壓縮品質(0~1)，愈小檔案愈小，為無損壓縮，並不影響畫質
     * @param overwrite 傳入是否覆蓋已存在的檔案，若不覆蓋，則自動重新命名輸出的檔名
     * @return 若有輸出成功，傳回新的檔案物件，否則傳回null
     */
    public static File exportToPNG(final BufferedImage bi, final File outputFile, final float compressionQuality, final boolean overwrite) {
	if (bi == null || outputFile == null || compressionQuality < 0 || compressionQuality > 1) {
	    return null;
	}
	//調整輸出檔案路徑
	final File adjustOutputFile = adjustOutputFile(outputFile, overwrite, "png", new String[]{"png"});

	try {
	    final PNGOptions pngOptions = new PNGOptions();
	    pngOptions.setCompressionLevel(Math.round(9.0f - compressionQuality * 9));
	    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(adjustOutputFile))) {
		ImageIO.write(bi, bos, ImageType.PNG, ImageParam.getBuilder().hasAlpha(true).imageOptions(pngOptions).build());
		bos.flush();
	    }

	    return adjustOutputFile;
	} catch (final Exception ex) {
	    ex.printStackTrace(System.out);
	    return null;
	}
    }

    /**
     * 將BufferedImage物件輸出成GIF圖片檔案。
     *
     * @param bi 傳入要輸出的BufferedImage物件
     * @param outputFile 傳入要輸出的檔案物件，副檔名必須為PNG，否則會自動重新命名
     * @param overwrite 傳入是否覆蓋已存在的檔案，若不覆蓋，則自動重新命名輸出的檔名
     * @return 若有輸出成功，傳回新的檔案物件，否則傳回null
     */
    public static File exportToGIF(final BufferedImage bi, final File outputFile, final boolean overwrite) {
	if (bi == null || outputFile == null) {
	    return null;
	}
	//調整輸出檔案路徑
	final File adjustOutputFile = adjustOutputFile(outputFile, overwrite, "gif", new String[]{"gif"});

	try {
	    final GIFOptions gifOptions = new GIFOptions();
	    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(adjustOutputFile))) {
		ImageIO.write(bi, bos, ImageType.GIF, ImageParam.getBuilder().hasAlpha(true).imageOptions(gifOptions).build());
		bos.flush();
	    }

	    return adjustOutputFile;
	} catch (final Exception ex) {
	    ex.printStackTrace(System.out);
	    return null;
	}
    }

    /**
     * 調整輸出檔案。
     *
     * @param outputFile 傳入參考的輸出檔案
     * @param overwrite 傳入是否覆寫
     * @param ext //傳入副檔名
     * @param extTokens //傳入查找副檔名的等效關鍵字
     * @return 傳回調整後的輸出檔案
     */
    private static File adjustOutputFile(final File outputFile, final boolean overwrite, final String ext, final String[] extTokens) {
	//調整輸出檔案路徑
	File adjustOutputFile = outputFile.getAbsoluteFile();
	final File adjustOutputFileParent = adjustOutputFile.getParentFile();
	//取得檔名與副檔名
	final String fileName = adjustOutputFile.getName();
	final String[] fileNameSplit = fileName.split("[\\.]");
	String mainName, extendName;
	if (fileNameSplit.length > 1) {
	    extendName = fileNameSplit[fileNameSplit.length - 1];
	    mainName = fileName.substring(0, fileName.length() - extendName.length() - 1);
	} else {
	    extendName = "";
	    mainName = fileName;
	}
	//副檔名強制轉換
	boolean foundExt = false;
	for (final String token : extTokens) {
	    if (extendName.equalsIgnoreCase(token)) {
		foundExt = true;
		break;
	    }
	}
	if (!foundExt) {
	    adjustOutputFile = new File(adjustOutputFileParent, mainName.concat(".").concat(ext));
	    extendName = ext;
	}
	if (overwrite) {
	    // 覆寫已存在的檔案，若檔案已存在就先刪除已存在的檔案
	    if (adjustOutputFile.exists()) {
		if (!adjustOutputFile.delete()) {
		    // 若刪除失敗，則無法輸出
		    return null;
		}
	    }
	} else {
	    // 略過已存在的檔案，若檔案已存在，就計數更名
	    int count = 1;
	    while (adjustOutputFile.exists()) {
		adjustOutputFile = new File(adjustOutputFileParent, mainName.concat("-").concat(String.valueOf(count++).concat(".").concat(extendName)));
	    }
	}
	return adjustOutputFile;
    }

    // -----建構子-----
    /**
     * 私有的建構子，將無法被實體化。
     */
    private ImageExport() {

    }
}
