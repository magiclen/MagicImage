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
import org.magiclen.magiccommand.Command;
import org.magiclen.magiccommand.CommandListener;

/**
 * <p>
 * 交錯、去交錯化圖片。</p>
 *
 *
 * <p>
 * 注意，這個類別需要ImageMagick的支援，使用之前必須先將ImageMagick的執行檔路徑設定給MAGICK_PATH類別變數。</p>
 *
 * @author Magic Len
 *
 */
public class ImageInterlacer {

    // -----類別列舉-----
    /**
     * 交錯的方式。
     */
    public static enum Interlace {
        /**
         * 不交錯。(RGBRGBRGBRGBRGBRGB...)
         */
        NONE("None"),
        /**
         * 掃描線交錯。(RRR...GGG...BBB...RRR...GGG...BBB...)
         */
        LINE("Line"),
        /**
         * 平面交錯。(RRRRRR...GGGGGG...BBBBBB...)
         */
        PLANE("Plane");

        /**
         * 取得選項字串。
         */
        public final String option;

        Interlace(final String option) {
            this.option = option;
        }
    }

    // -----類別常數-----
    /**
     * 「magick」執行檔的完整路徑。使用前請先設定。
     */
    public static String MAGICK_PATH = null;

    // -----類別方法-----
    /**
     * 判斷「magick」執行檔是否可用。
     *
     * @return 傳回「magick」執行檔是否可用
     */
    public static boolean isAvailable() {
        if (MAGICK_PATH == null) {
            return false;
        }
        try {
            final File magick = new File(MAGICK_PATH);
            if (!magick.exists() || !magick.isFile()) {
                return false;
            }
            magick.setExecutable(true);
            if (!magick.canExecute()) {
                return false;
            }
            final String path = magick.getAbsolutePath();
            final boolean[] success = new boolean[]{false};
            final int[] line = new int[]{0};
            final Command command = new Command(path, "-version");
            command.setCommandListener(new CommandListener() {

                @Override
                public void commandStart(final String id) {
                }

                @Override
                public void commandRunning(final String id, final String message, final boolean isError) {
                    if (!isError) {
                        ++line[0];
                        if (line[0] == 1 && message.contains("ImageMagick")) {
                            success[0] = true;
                        }
                    }
                }

                @Override
                public void commandException(final String id, final Exception exception) {

                }

                @Override
                public void commandEnd(final String id, final int returnValue) {
                    if (returnValue != 0) {
                        success[0] = false;
                    }
                }
            });
            command.run();
            return success[0];
        } catch (final Exception ex) {
            return false;
        }
    }

    /**
     * 判斷檔案是否為交錯式圖片(interlaced image)。
     *
     * @param file 傳入圖片檔案
     * @return 傳回檔案是否為交錯式圖片
     */
    public static boolean isInterlaced(final File file) {
        return isInterlaced(file, true);
    }

    /**
     * 判斷檔案是否為交錯式圖片(interlaced image)。
     *
     * @param file 傳入圖片檔案
     * @param checkExecute 傳入是否要先檢查執行檔(建議檢查)
     * @return 傳回檔案是否為交錯式圖片
     */
    public static boolean isInterlaced(final File file, final boolean checkExecute) {
        if (checkExecute && !isAvailable()) {
            return false;
        }
        final File magick = new File(MAGICK_PATH);
        final String magickPath = magick.getAbsolutePath();
        final String filePath = file.getAbsolutePath();
        final boolean[] isInterlaced = new boolean[]{false};
        final Command command = new Command(magickPath, "identify", "-verbose", filePath);
        command.setCommandListener(new CommandListener() {

            @Override
            public void commandStart(final String id) {
            }

            @Override
            public void commandRunning(final String id, final String message, final boolean isError) {
                if (!isError && !isInterlaced[0]) {
                    if (message.contains("Interlace: Plane") || message.contains("Interlace: Line") || message.contains("Interlace: JPEG") || message.contains("Interlace: PNG") || message.contains("Interlace: GIF")) {
                        isInterlaced[0] = true;
                        command.stopAll();
                    }
                }
            }

            @Override
            public void commandException(final String id, final Exception exception) {

            }

            @Override
            public void commandEnd(final String id, final int returnValue) {

            }
        });
        command.run();
        return isInterlaced[0];
    }

    /**
     * 設定圖片檔案使用的交錯方式。
     *
     * @param file 傳入圖片檔案
     * @param interlace 傳入交錯方式
     * @return 傳回交錯方式是否設定成功
     */
    public static boolean setInterlace(final File file, final Interlace interlace) {
        return setInterlace(file, interlace, true);
    }

    /**
     * 設定圖片檔案使用的交錯方式。
     *
     * @param file 傳入圖片檔案
     * @param interlace 傳入交錯方式
     * @param checkExecute 傳入是否要先檢查執行檔(建議檢查)
     * @return 傳回交錯方式是否設定成功
     */
    public static boolean setInterlace(final File file, final Interlace interlace, final boolean checkExecute) {
        if (checkExecute && !isAvailable()) {
            return false;
        }
        final File magick = new File(MAGICK_PATH);
        final String magickPath = magick.getAbsolutePath();
        final String filePath = file.getAbsolutePath();
        final File imageRe = new File(file.getParentFile().getAbsoluteFile(), String.valueOf(System.currentTimeMillis()).concat("-").concat(file.getName()));
        file.renameTo(imageRe);
        final boolean[] success = new boolean[]{false};
        final Command command = new Command(magickPath, "convert", imageRe.getAbsolutePath(), "-interlace", interlace.option, filePath);
        command.setCommandListener(new CommandListener() {

            @Override
            public void commandStart(final String id) {

            }

            @Override
            public void commandRunning(final String id, final String message, final boolean isError) {
            }

            @Override
            public void commandException(final String id, final Exception exception) {

            }

            @Override
            public void commandEnd(final String id, final int returnValue) {
                if (returnValue == 0) {
                    imageRe.delete();
                    success[0] = true;
                } else {
                    file.delete();
                    imageRe.renameTo(file);
                }
            }
        });
        command.run();
        return success[0];
    }
}
