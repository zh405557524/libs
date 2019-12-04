package com.soul.libs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Text {
    public static void main(String[] args) throws Exception {
        System.out.println("ת����ʼ");
        //		write("F:/��Ƶ/S-CUTE-ps7_18/ps7_18_you6-01_hd.wmv","F:/��Ƶ/S-CUTE-ps7_18/ps7_18_you6-01_hd.stp");
        read("F://1.stp", "F://ps7_18_you6-01_hd.wmv");
        System.out.println("ת�����");
    }

    /**
     * 转码
     *
     * @param yuan
     * @param mudi
     * @throws Exception
     */
    public static void write(String yuan, String mudi) throws Exception {
        File file = new File(yuan);
        File file2 = new File(mudi);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        FileOutputStream fos = new FileOutputStream(file2);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int len = 0;
        while ((len = bis.read()) != -1) {
            bos.write(len ^ 6);
        }
        fis.close();
        fos.close();
    }

    public static void read(String yuan, String mudi) throws Exception {
        File file = new File(yuan);
        File file2 = new File(mudi);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        FileOutputStream fos = new FileOutputStream(file2);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int len = 0;
        while ((len = bis.read()) != -1) {
            bos.write(len ^ 6);
        }
        fis.close();
        fos.close();
    }
}
