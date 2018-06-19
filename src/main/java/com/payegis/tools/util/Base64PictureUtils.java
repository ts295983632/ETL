package com.payegis.tools.util;

import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * company: 北京通付盾数据科技有限公司
 * user: chenzuoli
 * date: 2018/5/24
 * time: 13:28
 * description: 图片与base64码之间的转换
 */
public class Base64PictureUtils {
    private static Logger logger = Logger.getLogger(Base64PictureUtils.class);

    //图片转化成base64字符串
    public static String getImageStr(String picturePath) {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        BASE64Encoder encoder = new BASE64Encoder(); //对字节数组Base64编码
        //读取图片字节数组
        try {
            InputStream in = new FileInputStream(picturePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            logger.error("get image " + picturePath + " base64 exception!", e);
        }
        if (data == null) return "";
        return encoder.encode(data);//返回Base64编码过的字节数组字符串
    }

    //base64字符串转化成图片
    public static boolean generateImage(String imgStr, String resultPath) {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            OutputStream out = new FileOutputStream(resultPath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            logger.error("generate image from base64 " + imgStr + " exception!", e);
            return false;
        }
    }

}
