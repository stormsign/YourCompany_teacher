package com.miuhouse.yourcompany.teacher.http;

import android.util.Log;

import com.miuhouse.yourcompany.teacher.utils.Constants;
import com.miuhouse.yourcompany.teacher.utils.Util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MyHttpConnection {

    private static HttpURLConnection getConn(String urlPath) throws MyException {
        try {
            URL url = new URL(urlPath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10 * 1000);
            conn.setReadTimeout(10 * 1000);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("连接服务器失败", e);
        }
    }

    /**
     * 向服务器发post请求
     *
     * @param urlPath 请求的地址
     * @param json    请求的json串
     * @return + Constants.IMEI_VALUE
     * + Constants.VERSIONCODE_VALUE
     * + Constants.APPKEY
     * // 内网测试地址
     * public final static String IMEI = "imei";
     * public final static String DEVICETYPE = "deviceType";
     * public final static String VERSIONCODE = "version_code";
     * + token);
     * public final static String DEVICETYPE_VALUE = "3";
     * public final static String VERSIONCODE_VALUE = "1";
     * public final static String APPKEY = "peibanxue";
     * @throws MyException
     */
    public static String httpPost(String urlPath, String json) throws MyException {
        String md5 = Constants.DEVICETYPE_VALUE + Constants.IMEI_VALUE + Constants.VERSIONCODE_VALUE + Constants.APPKEY;
        Log.i("TAG", "md5sss=" + md5);
        md5 = Util.md5String(md5);
        Log.i("TAG", "httpPost:" + "\n md5=" + md5 + "\n transData=" + json);
        String respJson = null;
        Log.i("TAG", "post.json=" + json);
        try {
            HttpURLConnection conn = getConn(urlPath);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            OutputStream os = conn.getOutputStream();
            // 发送数据：md5和transdata
            Map<String, String> params = new LinkedHashMap<String, String>();

            params.put(Constants.MD5, md5);
            params.put(Constants.TRANSDATA, json);

            os.write(getParams(params).getBytes());
            os.flush();

            int code = conn.getResponseCode();
            Log.i("TAG", "code=" + code);
            if (code == HttpURLConnection.HTTP_OK) {

                // InputStream ins = conn.getInputStream();
                respJson = Util.streamToStringEn(conn);
                // ins.close();
            }
            os.close();
            conn.disconnect();
            return respJson;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("连接服务器失败", e);
        }
    }

    /**
     * 使用&连接要请求的参数
     *
     * @param params
     * @return
     */
    private static String getParams(Map<String, String> params) {
        StringBuilder sb = new StringBuilder();
        try {
            Set<Entry<String, String>> set = params.entrySet();
            for (Entry<String, String> entry : set) {
                sb.append(URLEncoder.encode(entry.getKey(), "utf-8"));
                sb.append('=');
                sb.append(URLEncoder.encode(entry.getValue(), "utf-8"));
                sb.append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 向服务器发get请求
     *
     * @param urlPath
     * @return
     */
    public static InputStream httpGet(String urlPath) {

        try {
            HttpURLConnection conn = getConn(urlPath);
            conn.setRequestMethod("GET");
            conn.setUseCaches(false);
            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                InputStream ins = conn.getInputStream();
                return ins;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
