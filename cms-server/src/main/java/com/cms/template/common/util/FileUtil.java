package com.cms.template.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class FileUtil {
    /**
     * BufferedImage 编码转换为 base64
     *
     * @param bufferedImage
     * @return
     */
    public static String BufferedImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
        return "data:image/png;base64," +
                Base64.getEncoder().encodeToString(bytes).replaceAll("\n", "").replaceAll("\r", "");
    }

    /**
     * base64 编码转换为 BufferedImage
     *
     * @param base64
     * @return
     */
    public static BufferedImage base64ToBufferedImage(String base64) {
        try {
            byte[] bytes1 = Base64.getDecoder().decode(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件转base64字符串
     *
     * @return
     */
    public static String fileToBase64(String filePath) {
        String base64 = null;
        try {
            byte[] bytes = FileToBytes(filePath);
            base64 = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return base64;
    }

    /**
     * base64字符串转文件
     *
     * @param base64
     * @return
     */
    public static String base64ToFile(String base64, String fileName) {
        String filePath = "";
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            filePath = BytesToFile(bytes,"" ,fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * bytes字节流写入文件
     *
     * @param bytes
     * @param fileName
     * @return
     */
    public static String BytesToFile(byte[] bytes, String filePath, String fileName) {
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            //创建文件目录
            if (StringUtils.isEmpty(filePath)) {
                filePath = FileUtil.class.getResource("/").getPath();
            }
            File dir = new File(filePath);
            if (!dir.exists() && !dir.isDirectory()) {
                dir.mkdirs();
            }
            filePath = filePath + "/"+fileName;

            if (StringUtils.isEmpty(filePath)) {
                filePath = FileUtil.class.getResource("/").getPath() + "/" + fileName;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            inputStream = new ByteArrayInputStream(bytes);
            outputStream = new FileOutputStream(file);
            int len = 0;
            byte[] buf = new byte[1024];
            while ((len = inputStream.read(buf, 0, 1024)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;
    }


    /**
     * 文件转换成字节流
     *
     * @param filePath
     * @return
     */
    public static byte[] FileToBytes(String filePath) {
        byte[] bytes = null;
        InputStream in = null;
        try {
            bytes = Files.readAllBytes(Paths.get(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }


}
