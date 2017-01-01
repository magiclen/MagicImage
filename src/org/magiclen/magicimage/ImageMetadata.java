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

import java.io.File;
import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageInfo;
import org.apache.commons.imaging.ImageInfo.ColorType;
import org.apache.commons.imaging.ImageInfo.CompressionAlgorithm;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata.ImageMetadataItem;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldType;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeAscii;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeByte;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeDouble;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeFloat;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeLong;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeRational;
import org.apache.commons.imaging.formats.tiff.fieldtypes.FieldTypeShort;
import org.magiclen.json.JSONArray;
import org.magiclen.json.JSONObject;

/**
 * 讀寫圖片檔案的元數據(Metadata)。
 *
 * @author Magic Len
 */
public final class ImageMetadata {

    // -----類別常數-----
    public static final String BASIC = "Basic";
    public static final String METADATA = "Metadata";
    public static final String BITS_PER_PIXEL = "Bits Per Pixel";
    public static final String COLOR_TYPE = "Color Type";
    public static final String COLOR_TYPE_BW = "BW";
    public static final String COLOR_TYPE_CMYK = "CMYK";
    public static final String COLOR_TYPE_GRAYSCALE = "Gray Scale";
    public static final String COLOR_TYPE_OTHER = "Other";
    public static final String COLOR_TYPE_RGB = "RGB";
    public static final String COLOR_TYPE_YCC = "YCC";
    public static final String COLOR_TYPE_YCCK = "YCCK";
    public static final String COLOR_TYPE_YCBCR = "YCbCr";
    public static final String COLOR_TYPE_UNKNOWN = "Unknown";
    public static final String COMMENTS = "comments";
    public static final String COMPRESSION_ALGORITHM = "Compression Algorithm";
    public static final String COMPRESSION_ALGORITHM_ADAPTIVE_RLE = "Adaptive RLE";
    public static final String COMPRESSION_ALGORITHM_RLE = "RLE";
    public static final String COMPRESSION_ALGORITHM_CCITT_1D = "CCITT 1D";
    public static final String COMPRESSION_ALGORITHM_CCITT_GROUP_3 = "CCITT Group 3";
    public static final String COMPRESSION_ALGORITHM_CCITT_GROUP_4 = "CCITT Group 4";
    public static final String COMPRESSION_ALGORITHM_JPEG = "JPEG";
    public static final String COMPRESSION_ALGORITHM_LZW = "LZW";
    public static final String COMPRESSION_ALGORITHM_NONE = "None";
    public static final String COMPRESSION_ALGORITHM_PACKBITS = "PACKBITS";
    public static final String COMPRESSION_ALGORITHM_PNG_FILTER = "PNG Filter";
    public static final String COMPRESSION_ALGORITHM_PSD = "PSD";
    public static final String COMPRESSION_ALGORITHM_UNKNOWN = "Unknown";
    public static final String FORMAT = "Format";
    public static final String FORMAT_BMP = "BMP";
    public static final String FORMAT_PNG = "PNG";
    public static final String FORMAT_JPEG = "JPEG";
    public static final String FORMAT_DCX = "DCX";
    public static final String FORMAT_GIF = "GIF";
    public static final String FORMAT_ICNS = "ICNS";
    public static final String FORMAT_ICO = "ICO";
    public static final String FORMAT_JBIG2 = "JBIG2";
    public static final String FORMAT_PAM = "PAM";
    public static final String FORMAT_PSD = "PSD";
    public static final String FORMAT_PBM = "PBM";
    public static final String FORMAT_PGM = "PGM";
    public static final String FORMAT_PNM = "PNM";
    public static final String FORMAT_PPM = "PPM";
    public static final String FORMAT_PCX = "PCX";
    public static final String FORMAT_RGBE = "RGBE";
    public static final String FORMAT_TGA = "TGA";
    public static final String FORMAT_TIFF = "TIFF";
    public static final String FORMAT_WBMP = "WBMP";
    public static final String FORMAT_XBM = "XBM";
    public static final String FORMAT_XPM = "XPM";
    public static final String FORMAT_UNKNOWN = "Unknown";
    public static final String FORMAT_DETAILS = "Format Details";
    public static final String WIDTH = "Width";
    public static final String WIDTH_DPI = "Width DPI";
    public static final String WIDTH_INCH = "Width Inch";
    public static final String HEIGHT = "Height";
    public static final String HEIGHT_DPI = "Height DPI";
    public static final String HEIGHT_INCH = "Height Inch";
    public static final String MIME_TYPE = "Mime Type";
    public static final String NUMBER_OF_IMAGES = "Number of Images";
    public static final String METADATA_NAME = "name";
    public static final String METADATA_NAME_UNKNOWN = "Unknown";
    public static final String METADATA_ID = "id";
    public static final String METADATA_TYPE = "type";
    public static final String METADATA_TYPE_BYTE_ARRAY = "Byte Array";
    public static final String METADATA_TYPE_INTEGER = "Integer";
    public static final String METADATA_TYPE_INTEGER_ARRAY = "Integer Array";
    public static final String METADATA_TYPE_REAL = "Real";
    public static final String METADATA_TYPE_REAL_ARRAY = "Real Array";
    public static final String METADATA_TYPE_STRING = "String";
    public static final String METADATA_TYPE_UNKNOWN = "Unknown";
    public static final String METADATA_VALUE = "value";

    // -----類別方法-----
    /**
     * 寫入圖片檔案的Metadata。
     *
     * @param file 傳入圖片檔案物件
     * @param metadata 傳入Metadata
     * @return 傳回Metadata是否寫入成功
     */
    public static boolean writeMetadataToFile(final File file, final JSONArray metadata) {
	// 尚不支援
	return false;
    }

    /**
     * 讀取圖片檔案的Metadata。
     *
     * @param file 傳入圖片檔案物件
     * @return 傳回圖片檔案的Metadata，若圖片檔案無法讀取，傳回null
     */
    public static JSONObject readMetadataFromFile(final File file) {
	if (file == null || !file.isFile() || !file.exists() || !file.canRead()) {
	    return null;
	}

	try {
	    final ImageInfo imageInfo = Imaging.getImageInfo(file);
	    if (imageInfo == null) {
		return null;
	    }
	    final JSONObject result = new JSONObject();

	    // Basic
	    final JSONObject basicInfo = new JSONObject();
	    final ColorType colorType = imageInfo.getColorType();
	    final String colorTypeString;
	    switch (colorType) {
		case BW:
		    colorTypeString = COLOR_TYPE_BW;
		    break;
		case CMYK:
		    colorTypeString = COLOR_TYPE_CMYK;
		    break;
		case GRAYSCALE:
		    colorTypeString = COLOR_TYPE_GRAYSCALE;
		    break;
		case OTHER:
		    colorTypeString = COLOR_TYPE_OTHER;
		    break;
		case RGB:
		    colorTypeString = COLOR_TYPE_RGB;
		    break;
		case YCC:
		    colorTypeString = COLOR_TYPE_YCC;
		    break;
		case YCCK:
		    colorTypeString = COLOR_TYPE_YCCK;
		    break;
		case YCbCr:
		    colorTypeString = COLOR_TYPE_YCBCR;
		    break;
		default:
		    colorTypeString = COLOR_TYPE_UNKNOWN;
		    break;
	    }
	    final CompressionAlgorithm compressionAlgorithm = imageInfo.getCompressionAlgorithm();
	    final String compressionAlgorithmString;
	    switch (compressionAlgorithm) {
		case ADAPTIVE_RLE:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_ADAPTIVE_RLE;
		    break;
		case CCITT_1D:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_CCITT_1D;
		    break;
		case CCITT_GROUP_3:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_CCITT_GROUP_3;
		    break;
		case CCITT_GROUP_4:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_CCITT_GROUP_4;
		    break;
		case JPEG:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_JPEG;
		    break;
		case LZW:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_LZW;
		    break;
		case NONE:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_NONE;
		    break;
		case RLE:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_RLE;
		    break;
		case PACKBITS:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_PACKBITS;
		    break;
		case PNG_FILTER:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_PNG_FILTER;
		    break;
		case PSD:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_PSD;
		    break;
		default:
		    compressionAlgorithmString = COMPRESSION_ALGORITHM_UNKNOWN;
		    break;
	    }
	    final JSONArray comments = new JSONArray();
	    imageInfo.getComments().forEach((final String comment) -> {
		if (comment == null) {
		    return;
		}
		comments.put(comment);
	    });
	    final ImageFormat imageFormat = imageInfo.getFormat();
	    final String format;
	    if (imageFormat != null) {
		final String formatName = imageFormat.getName();
		switch (formatName) {
		    case "BMP":
			format = FORMAT_BMP;
			break;
		    case "DCX":
			format = FORMAT_DCX;
			break;
		    case "GIF":
			format = FORMAT_GIF;
			break;
		    case "ICNS":
			format = FORMAT_ICNS;
			break;
		    case "ICO":
			format = FORMAT_ICO;
			break;
		    case "JBIG2":
			format = FORMAT_JBIG2;
			break;
		    case "JPEG":
			format = FORMAT_JPEG;
			break;
		    case "PAM":
			format = FORMAT_PAM;
			break;
		    case "PSD":
			format = FORMAT_PSD;
			break;
		    case "PBM":
			format = FORMAT_PBM;
			break;
		    case "PGM":
			format = FORMAT_PGM;
			break;
		    case "PNM":
			format = FORMAT_PNM;
			break;
		    case "PPM":
			format = FORMAT_PPM;
			break;
		    case "PCX":
			format = FORMAT_PCX;
			break;
		    case "PNG":
			format = FORMAT_PNG;
			break;
		    case "RGBE":
			format = FORMAT_RGBE;
			break;
		    case "TGA":
			format = FORMAT_TGA;
			break;
		    case "TIFF":
			format = FORMAT_TIFF;
			break;
		    case "WBMP":
			format = FORMAT_WBMP;
			break;
		    case "XBM":
			format = FORMAT_XBM;
			break;
		    case "XPM":
			format = FORMAT_XPM;
			break;
		    default:
			format = FORMAT_UNKNOWN;
		}
	    } else {
		format = FORMAT_UNKNOWN;
	    }
	    final int bitsPerPixel = imageInfo.getBitsPerPixel();
	    final int numberOfImages = imageInfo.getNumberOfImages();
	    final int width = imageInfo.getWidth();
	    final int height = imageInfo.getHeight();
	    final int widthDpi = imageInfo.getPhysicalWidthDpi();
	    final int heightDpi = imageInfo.getPhysicalHeightDpi();
	    final float widthInch = imageInfo.getPhysicalWidthInch();
	    final float heightInch = imageInfo.getPhysicalHeightInch();
	    final String formatDetials = imageInfo.getFormatDetails();
	    final String mimeType = imageInfo.getMimeType();

	    if (bitsPerPixel > 0) {
		basicInfo.put(BITS_PER_PIXEL, bitsPerPixel);
	    }
	    if (!colorTypeString.equals(COLOR_TYPE_UNKNOWN)) {
		basicInfo.put(COLOR_TYPE, colorTypeString);
	    }
	    if (comments.length() > 0) {
		basicInfo.put(COMMENTS, comments);
	    }
	    if (!compressionAlgorithmString.equals(COMPRESSION_ALGORITHM_UNKNOWN)) {
		basicInfo.put(COMPRESSION_ALGORITHM, compressionAlgorithmString);
	    }
	    if (!format.equals(FORMAT_UNKNOWN)) {
		basicInfo.put(FORMAT, format);
	    }
	    if (formatDetials != null) {
		basicInfo.put(FORMAT_DETAILS, formatDetials);
	    }
	    if (width > 0) {
		basicInfo.put(WIDTH, width);
	    }
	    if (height > 0) {
		basicInfo.put(HEIGHT, height);
	    }
	    if (mimeType != null) {
		basicInfo.put(MIME_TYPE, mimeType);
	    }
	    if (numberOfImages > 0) {
		basicInfo.put(NUMBER_OF_IMAGES, numberOfImages);
	    }
	    if (widthDpi > 0) {
		basicInfo.put(WIDTH_DPI, widthDpi);
	    }
	    if (heightDpi > 0) {
		basicInfo.put(HEIGHT_DPI, heightDpi);
	    }
	    if (widthInch > 0) {
		basicInfo.put(WIDTH_INCH, widthInch);
	    }
	    if (heightInch > 0) {
		basicInfo.put(HEIGHT_INCH, imageInfo.getPhysicalHeightInch());
	    }

	    // Metadata
	    final org.apache.commons.imaging.common.ImageMetadata metadata = Imaging.getMetadata(file);
	    final JSONArray metadataArray = new JSONArray();
	    if (metadata != null) {
		TiffImageMetadata exif = null;
		if (metadata instanceof JpegImageMetadata) {
		    exif = ((JpegImageMetadata) metadata).getExif();
		} else if (metadata instanceof TiffImageMetadata) {
		    exif = (TiffImageMetadata) metadata;
		}
		if (exif != null) {
		    exif.getAllFields().forEach((final TiffField field) -> {
			try {
			    final String name = field.getTagName();
			    final int id = field.getTag();
			    final FieldType type = field.getFieldType();
			    final JSONObject obj = new JSONObject();
			    obj.put(METADATA_ID, id);
			    obj.put(METADATA_NAME, name);
			    if (type instanceof FieldTypeByte) {
				obj.put(METADATA_TYPE, METADATA_TYPE_BYTE_ARRAY);
				obj.put(METADATA_VALUE, field.getByteArrayValue());
			    } else if (type instanceof FieldTypeShort || type instanceof FieldTypeLong || type instanceof FieldTypeShort) {
				if (field.getCount() == 1) {
				    obj.put(METADATA_TYPE, METADATA_TYPE_INTEGER);
				    obj.put(METADATA_VALUE, field.getIntValue());
				} else {
				    obj.put(METADATA_TYPE, METADATA_TYPE_INTEGER_ARRAY);
				    obj.put(METADATA_VALUE, field.getIntArrayValue());
				}
			    } else if (type instanceof FieldTypeFloat || type instanceof FieldTypeDouble || type instanceof FieldTypeRational) {
				if (field.getCount() == 1) {
				    obj.put(METADATA_TYPE, METADATA_TYPE_REAL);
				    obj.put(METADATA_VALUE, field.getDoubleValue());
				} else {
				    obj.put(METADATA_TYPE, METADATA_TYPE_REAL_ARRAY);
				    obj.put(METADATA_VALUE, field.getDoubleArrayValue());
				}
			    } else if (type instanceof FieldTypeAscii) {
				obj.put(METADATA_TYPE, METADATA_TYPE_STRING);
				obj.put(METADATA_VALUE, field.getStringValue());
			    } else {
				obj.put(METADATA_TYPE, METADATA_TYPE_UNKNOWN);
				obj.put(METADATA_VALUE, field.getValue());
			    }
			    metadataArray.put(obj);
			} catch (final Exception ex) {
			    ex.printStackTrace(System.out);
			}
		    });
		} else {
		    metadata.getItems().forEach((final ImageMetadataItem item) -> {
			final String info = item.toString();
			final String[] infoSplit = info.split(":");
			final JSONObject obj = new JSONObject();
			if (infoSplit.length > 1) {
			    final String name = infoSplit[0].trim();
			    final String value = info.substring(infoSplit[0].length() + 1).trim();
			    obj.put(METADATA_ID, -1);
			    obj.put(METADATA_NAME, name);
			    obj.put(METADATA_VALUE, value);
			    obj.put(METADATA_TYPE, METADATA_TYPE_STRING);
			} else {
			    obj.put(METADATA_ID, -1);
			    obj.put(METADATA_NAME, METADATA_NAME_UNKNOWN);
			    obj.put(METADATA_VALUE, info);
			    obj.put(METADATA_TYPE, METADATA_TYPE_STRING);
			}
			metadataArray.put(obj);
		    });
		}
	    }
	    result.put(BASIC, basicInfo);
	    if (METADATA.length() > 0) {
		result.put(METADATA, metadataArray);
	    }
	    return result;
	} catch (final Exception ex) {
	    ex.printStackTrace(System.out);
	    return null;
	}
    }

    // -----建構子-----
    /**
     * 私有的建構子，將無法被實體化。
     */
    private ImageMetadata() {

    }
}
