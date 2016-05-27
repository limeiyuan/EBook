package li.ebook;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiMeiyuan on 2016/5/27 13:55.
 * description: 获取网页中的文本
 */
public abstract class Snap {
    /**
     * 从指定网页中获取文本内容，保存到temp文件中
     *
     * @param file 临时文件
     * @param website 网址
     * @param charset 网站编码
     */
    public void dump(File file, String website, Charset charset) throws IOException {
        URL url = new URL(website);
        InputStream is;
        try {
            is = url.openConnection().getInputStream();
        } catch (IOException e) {
            System.out.println(website + "不存在");
            return;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));

        OutputStream out = new FileOutputStream(file, true);
        String line;
        while ((line = reader.readLine()) != null) {
            out.write((line + "\r\n").getBytes());
        }
        out.flush();
        out.close();
        reader.close();
    }

    public abstract void analyse(File tempFile, String filePath, String fileName) throws IOException;

    public void makeDir(String filePath) {
        File path = new File(filePath);
        if (!path.exists()) {
            path.mkdirs();
        }
    }

    public abstract List<String> getUrls();

    public abstract Charset getWebCharset();

    public void process(String filePath, String fileName) throws IOException {
        List<String> urls = getUrls();
        String tmpName = "temp-file-name" + Math.random();
        System.out.println("临时文件名" + tmpName);
        File file = File.createTempFile("temp-file-name", ".tmp");
        for (String url : urls) {
            dump(file, url, getWebCharset());
        }
        makeDir(filePath);
        analyse(file, filePath, fileName);
    }
}
