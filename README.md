MagicImage
=================================

# Introduction

**MagicImage** is a Java library for image processing. It can support many formats of images in Java programs and provide common functions to make adjustments to the image in post. After you finish adjusting your images, you can export them to many formats, too. **MagicImage** uses [Mson](https://github.com/magiclen/MagicLenJSON "Mson") library to load the metadata of images, [Commons Imaging](http://repository.jboss.org/nexus/content/groups/public/org/apache/commons/commons-imaging/1.0-SNAPSHOT/ "Commons Imaging") library to import the image files, [java-image-scaling](https://github.com/mortennobel/java-image-scaling "java-image-scaling") library to scale images with high quality, and [ICAFE](https://github.com/dragon66/icafe "ICAFE") library to import and export image files.

# Usage

## ImageBuffer Class

**ImageMetadata** class is in the *org.magiclen.magicimage* package.

### Initialize

You don't need to do initialize when you use **ImageMetadata** class. Just use its static methods to do what you want.

### Load the metadata from an image file

You can use **readMetadataFromFile** static method to read the metadata from an image file. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final JSONObject jsonImg = ImageMetadata.readMetadataFromFile(imageFile);
    System.out.println(jsonImg.toString(true));

The result is,

    {
    	"Basic" : {
    		"Bits Per Pixel" : 24,
    		"Width DPI" : 300,
    		"Compression Algorithm" : "JPEG",
    		"Format Details" : "Jpeg/DCM",
    		"Width Inch" : 12.58666706085205,
    		"Height DPI" : 300,
    		"Format" : "JPEG",
    		"Height Inch" : 8.399999618530273,
    		"Height" : 2520,
    		"Mime Type" : "image/jpeg",
    		"Color Type" : "YCbCr",
    		"Width" : 3776,
    		"Number of Images" : 1
    	},
    	"Metadata" : [
    		{
    			"name" : "Make",
    			"id" : 271,
    			"type" : "String",
    			"value" : "Panasonic"
    		},
    		{
    			"name" : "Model",
    			"id" : 272,
    			"type" : "String",
    			"value" : "DMC-LX7"
    		},
    		{
    			"name" : "Orientation",
    			"id" : 274,
    			"type" : "Integer",
    			"value" : 1
    		},
    		{
    			"name" : "XResolution",
    			"id" : 282,
    			"type" : "Real",
    			"value" : 300.0
    		},
    		{
    			"name" : "YResolution",
    			"id" : 283,
    			"type" : "Real",
    			"value" : 300.0
    		},
    		{
    			"name" : "ResolutionUnit",
    			"id" : 296,
    			"type" : "Integer",
    			"value" : 2
    		},
    		{
    			"name" : "Software",
    			"id" : 305,
    			"type" : "String",
    			"value" : "Adobe Photoshop CC (Windows)"
    		},
    		{
    			"name" : "DateTime",
    			"id" : 306,
    			"type" : "String",
    			"value" : "2014:02:16 23:26:53"
    		},
    		{
    			"name" : "ExifOffset",
    			"id" : 34665,
    			"type" : "Integer",
    			"value" : 208
    		},
    		{
    			"name" : "ExposureTime",
    			"id" : 33434,
    			"type" : "Real",
    			"value" : 0.1
    		},
    		{
    			"name" : "FNumber",
    			"id" : 33437,
    			"type" : "Real",
    			"value" : 2.1
    		},
    		{
    			"name" : "ExposureProgram",
    			"id" : 34850,
    			"type" : "Integer",
    			"value" : 3
    		},
    		{
    			"name" : "PhotographicSensitivity",
    			"id" : 34855,
    			"type" : "Integer",
    			"value" : 200
    		},
    		{
    			"name" : "ExifVersion",
    			"id" : 36864,
    			"type" : "Byte Array",
    			"value" : [
    				48,
    				50,
    				51,
    				48
    			]
    		},
    		{
    			"name" : "DateTimeOriginal",
    			"id" : 36867,
    			"type" : "String",
    			"value" : "2014:02:16 17:47:32"
    		},
    		{
    			"name" : "DateTimeDigitized",
    			"id" : 36868,
    			"type" : "String",
    			"value" : "2014:02:16 17:47:32"
    		},
    		{
    			"name" : "ShutterSpeedValue",
    			"id" : 37377,
    			"type" : "Real",
    			"value" : 3.321928
    		},
    		{
    			"name" : "ApertureValue",
    			"id" : 37378,
    			"type" : "Real",
    			"value" : 2.140779
    		},
    		{
    			"name" : "ExposureCompensation",
    			"id" : 37380,
    			"type" : "Real",
    			"value" : 0.0
    		},
    		{
    			"name" : "MaxApertureValue",
    			"id" : 37381,
    			"type" : "Real",
    			"value" : 2.140625
    		},
    		{
    			"name" : "MeteringMode",
    			"id" : 37383,
    			"type" : "Integer",
    			"value" : 5
    		},
    		{
    			"name" : "LightSource",
    			"id" : 37384,
    			"type" : "Integer",
    			"value" : 0
    		},
    		{
    			"name" : "Flash",
    			"id" : 37385,
    			"type" : "Integer",
    			"value" : 16
    		},
    		{
    			"name" : "FocalLength",
    			"id" : 37386,
    			"type" : "Real",
    			"value" : 13.7
    		},
    		{
    			"name" : "ColorSpace",
    			"id" : 40961,
    			"type" : "Integer",
    			"value" : -1
    		},
    		{
    			"name" : "ExifImageWidth",
    			"id" : 40962,
    			"type" : "Integer",
    			"value" : 3776
    		},
    		{
    			"name" : "ExifImageLength",
    			"id" : 40963,
    			"type" : "Integer",
    			"value" : 2520
    		},
    		{
    			"name" : "FocalPlaneXResolution",
    			"id" : 41486,
    			"type" : "Real",
    			"value" : 4903.225799560547
    		},
    		{
    			"name" : "FocalPlaneYResolution",
    			"id" : 41487,
    			"type" : "Real",
    			"value" : 4903.225799560547
    		},
    		{
    			"name" : "FocalPlaneResolutionUnit",
    			"id" : 41488,
    			"type" : "Integer",
    			"value" : 3
    		},
    		{
    			"name" : "SensingMethod",
    			"id" : 41495,
    			"type" : "Integer",
    			"value" : 2
    		},
    		{
    			"name" : "FileSource",
    			"id" : 41728,
    			"type" : "Byte Array",
    			"value" : [
    				3
    			]
    		},
    		{
    			"name" : "SceneType",
    			"id" : 41729,
    			"type" : "Byte Array",
    			"value" : [
    				1
    			]
    		},
    		{
    			"name" : "CustomRendered",
    			"id" : 41985,
    			"type" : "Integer",
    			"value" : 0
    		},
    		{
    			"name" : "ExposureMode",
    			"id" : 41986,
    			"type" : "Integer",
    			"value" : 0
    		},
    		{
    			"name" : "WhiteBalance",
    			"id" : 41987,
    			"type" : "Integer",
    			"value" : 0
    		},
    		{
    			"name" : "DigitalZoomRatio",
    			"id" : 41988,
    			"type" : "Real",
    			"value" : 0.0
    		},
    		{
    			"name" : "FocalLengthIn35mmFormat",
    			"id" : 41989,
    			"type" : "Integer",
    			"value" : 70
    		},
    		{
    			"name" : "SceneCaptureType",
    			"id" : 41990,
    			"type" : "Integer",
    			"value" : 0
    		},
    		{
    			"name" : "GainControl",
    			"id" : 41991,
    			"type" : "Integer",
    			"value" : 1
    		},
    		{
    			"name" : "Contrast",
    			"id" : 41992,
    			"type" : "Integer",
    			"value" : 0
    		},
    		{
    			"name" : "Saturation",
    			"id" : 41993,
    			"type" : "Integer",
    			"value" : 0
    		},
    		{
    			"name" : "Sharpness",
    			"id" : 41994,
    			"type" : "Integer",
    			"value" : 0
    		},
    		{
    			"name" : "Compression",
    			"id" : 259,
    			"type" : "Integer",
    			"value" : 6
    		},
    		{
    			"name" : "XResolution",
    			"id" : 282,
    			"type" : "Real",
    			"value" : 72.0
    		},
    		{
    			"name" : "YResolution",
    			"id" : 283,
    			"type" : "Real",
    			"value" : 72.0
    		},
    		{
    			"name" : "ResolutionUnit",
    			"id" : 296,
    			"type" : "Integer",
    			"value" : 2
    		},
    		{
    			"name" : "JpgFromRawStart",
    			"id" : 513,
    			"type" : "Integer",
    			"value" : 838
    		},
    		{
    			"name" : "JpgFromRawLength",
    			"id" : 514,
    			"type" : "Integer",
    			"value" : 8060
    		}
    	]
    }

You are able to download my sample image [here](https://file.magiclen.org/index.php?file=4d616769634361742e6a7067 "MagicCat").

## ImageBuffer Class

**ImageBuffer** class is in the *org.magiclen.magicimage* package.

### Initialize

You don't need to do initialize when you use **ImageBuffer** class. Just use its static methods to do what you want.

### Load image to BufferedImage from image file

You can use **getBufferedImages** static method to load images from an image file. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
  	final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

There may be more than 1 images in an image file, so the **getBufferedImages** static method returns **BufferedImage** array.

### Copy BufferedImage

You can use **cloneBufferedImage** static method to copy **BufferedImage**. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
  	final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];
  	final BufferedImage cloneBi = ImageBuffer.cloneBufferedImage(bi);

## ImageExport Class

**ImageExport** class is in the *org.magiclen.magicimage* package.

### Initialize

You don't need to do initialize when you use **ImageExport** class. Just use its static methods to do what you want.

### Export to JPEG file

You can use **exportToJPEG** static method to export **BufferedImage** to JPEG format file. You can also decide its quality to compress the file size. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final File outputFile = new File("/home/magiclen/MyCat.jpg");
    System.out.println(ImageExport.exportToJPEG(bi, outputFile, 0.8f, true));

The size of the original JPEG file is 7.1MB. But after **exportToJPEG** static method called, the size of the output JPEG file is 1.4MB. The output size is very smaller than the original, but it is only lost a little details.

### Export to PNG file

You can use **exportToPNG** static method to export **BufferedImage** to PNG format file. You can also decide its compression quality to decrease the file size. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final File outputFile = new File("/home/magiclen/MyCat1.png");
    System.out.println(ImageExport.exportToPNG(bi, outputFile, 1f, true));

    final File outputFile2 = new File("/home/magiclen/MyCat0.png");
    System.out.println(ImageExport.exportToPNG(bi, outputFile2, 0f, true));

You should know that the compression of PNG is lossless. It means that although you decrease the file size, the quality of this image is the same. You can see the `MyCat1.png` and `MyCat0.png` files. They have different compression quality. The file size of `MyCat1.png` is 38.1MB, but the file size of `MyCat0.png` is only 18.4MB.

### Export to BMP file

You can use **exportToBMP** static method to export **BufferedImage** to BMP format file. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final File outputFile = new File("/home/magiclen/MyCat.bmp");
    System.out.println(ImageExport.exportToBMP(bi, outputFile, true));

### Export to GIF file

You can use **exportToGIF** static method to export **BufferedImage** to GIF format file. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final File outputFile = new File("/home/magiclen/MyCat.gif");
    System.out.println(ImageExport.exportToGIF(bi, outputFile, true));

### Export to TIFF file

You can use **exportToTIFF** static method to export **BufferedImage** to TIFF format file. You can also decide its compression quality to decrease the file size, and lossless or lossy. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final File outputFile = new File("/home/magiclen/MyCat.tiff");
    System.out.println(ImageExport.exportToTIFF(bi, outputFile, 0.8f, false, true));
    final File outputFile2 = new File("/home/magiclen/MyCat-lossless.tiff");
    System.out.println(ImageExport.exportToTIFF(bi, outputFile2, 0.8f, true, true));

The file size of `MagicCat.tiff` is 1.4MB, but the file size of `MyCat-lossless.tiff` is 23.4MB. If you want to use lossless compression, we suggest you use PNG format.

## ImageSharpen Class

**ImageSharpen** class is in the *org.magiclen.magicimage* package.

### Initialize

You don't need to do initialize when you use **ImageSharpen** class. Just use its static methods to do what you want.

### Sharpen an image

You can use **sharpen** static method to export **BufferedImage** to sharpen an image. You can also decide how much you want to sharpen. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final BufferedImage result = ImageSharpen.sharpen(bi, 3);
    final File outputFile = new File("/home/magiclen/MyCat-sharpen.jpg");
    System.out.println(ImageExport.exportToJPEG(result, outputFile, 0.8f, true));

We suggest the sharpen value should be between 0 and 1.5.

## ImageBlurring Class

**ImageBlurring** class is in the *org.magiclen.magicimage* package.

### Initialize

You don't need to do initialize when you use **ImageBlurring** class. Just use its static methods to do what you want.

### Blur an image

You can use **blur** static method or **gaussianBlur** static method to blur an image. You can also decide how much you want to blur. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final BufferedImage result = ImageBlurring.blur(bi, 20);
    final File outputFile = new File("/home/magiclen/MyCat-blur.jpg");
    System.out.println(ImageExport.exportToJPEG(result, outputFile, 0.8f, true));

    final BufferedImage result2 = ImageBlurring.gaussianBlur(bi, 20);
    final File outputFile2 = new File("/home/magiclen/MyCat-gaussian-blur.jpg");
    System.out.println(ImageExport.exportToJPEG(result2, outputFile2, 0.8f, true));

Gaussian Blur is smoother than blur.

## ImageResize Class

**ImageResize** class is in the *org.magiclen.magicimage* package.

### Initialize

You don't need to do initialize when you use **ImageResize** class. Just use its static methods to do what you want.

### Crop an image

You can use **crop** static method to crop an image. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final BufferedImage result = ImageResize.crop(bi, 900, 900, 1250, 160);
    final File outputFile = new File("/home/magiclen/MyCat-crop.jpg");
    System.out.println(ImageExport.exportToJPEG(result, outputFile, 0.8f, true));

### Resize an image

You can use **resize** static method to resize an image. It can also sharpen the image resized automatically. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final BufferedImage result = ImageResize.resize(bi, 4000, -1, false, false);
    final File outputFile = new File("/home/magiclen/MyCat-4000.jpg");
    System.out.println(ImageExport.exportToJPEG(result, outputFile, 0.8f, true));

### Shrink an image

You can use **shrink** static method to resize an image. It can also sharpen the image resized automatically. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final BufferedImage result = ImageResize.shrink(bi, 1920, 720, -1); // Limit the width and height
    final File outputFile = new File("/home/magiclen/MyCat-h720.jpg");
    System.out.println(ImageExport.exportToJPEG(result, outputFile, 0.8f, true));

If you don't want to sharpen, set the sharpen value to 0.

## ImageColor Class

**ImageColor** class is in the *org.magiclen.magicimage* package.

### Initialize

You don't need to do initialize when you use **ImageColor** class. Just use its static methods to do what you want.

### Create grayscale image

You can use **grayscale** static method to create a grayscale image. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final BufferedImage result = ImageColor.gray(bi);
    final File outputFile = new File("/home/magiclen/MyCat-gray.png");
    System.out.println(ImageExport.exportToPNG(result, outputFile, true));

### Create binary image

You can use **binary** static method to create a binary image. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final BufferedImage result = ImageColor.binary(bi);
    final File outputFile = new File("/home/magiclen/MyCat-binary.png");
    System.out.println(ImageExport.exportToPNG(result, outputFile, true));

### Create negative image

You can use **negative** static method to create a negative image. For example,

    final File imageFile = new File("/home/magiclen/MagicCat.jpg");
    final BufferedImage bi = ImageBuffer.getBufferedImages(imageFile)[0];

    final BufferedImage result = ImageColor.negative(bi);
    final File outputFile = new File("/home/magiclen/MyCat-negative.jpg");
    System.out.println(ImageExport.exportToJPEG(result, outputFile, 0.8f, true));

### Create the union of two images

You can use **union** static method to create the union of two images.

### Find the distinct pixels of two images

You can use **distinct** static method to find the distinct pixels of two images.

### Create maximized image

You can use **maximize** static method to create a maximized image.

# License

    Copyright 2015-2016 magiclen.org

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

# What's More?

Please check out our web page at

https://magiclen.org/magicimage/
