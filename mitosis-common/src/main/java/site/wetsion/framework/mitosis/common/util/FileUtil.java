package site.wetsion.framework.mitosis.common.util;

import site.wetsion.framework.mitosis.common.constant.FileType;

import java.io.*;
import java.util.UUID;

public class FileUtil {

    private static String bytesToHexString(byte[] src){

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private static String getFileContent(String filePath) throws IOException {

        byte[] b = new byte[28];

        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(filePath);
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }

    private static String getFileContent(FileInputStream inputStream) throws IOException {

        byte[] b = new byte[28];

        try {
            inputStream.read(b, 0, 28);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        }
        return bytesToHexString(b);
    }

    /**
     * 判断文件类型
     *
     * @param filePath 文件路径
     * @return 文件类型
     */
    public static String getType(String filePath) throws IOException {
        String fileHead = getFileContent(filePath);
        return guessType(fileHead);
    }

    public static String getType(FileInputStream inputStream) throws IOException {
        String fileHead = getFileContent(inputStream);
        return guessType(fileHead);
    }

    public static String getType(byte[] fileBytes) throws IOException {
        FileInputStream inputStream = byteToFileInputStream(fileBytes);
        String fileHead = getFileContent(inputStream);
        return guessType(fileHead);
    }


    private static String guessType (String fileHead) {
        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileType[] fileTypes = FileType.values();

        for (FileType type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type.name();
            }
        }

        return null;
    }

    public static FileInputStream byteToFileInputStream(byte[] bytes) {
        File file = new File("/tmp/" + UUID.randomUUID().toString().replace("-", ""));
        FileInputStream fileInputStream = null;
        try {
            OutputStream output = new FileOutputStream(file);
            BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
            bufferedOutput.write(bytes);
            fileInputStream = new FileInputStream(file);
            file.deleteOnExit();
            return fileInputStream;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }

    //将文件转换成Byte数组
    public static byte[] getBytesByFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
