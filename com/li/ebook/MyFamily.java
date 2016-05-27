package li.ebook;


import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LiMeiyuan on 2016/5/27 14:15.
 * description: 《我们仨》kanunu8网截取
 */
public class MyFamily extends Snap {
    @Override
    public void analyse(File tempFile, String filePath, String fileName) throws IOException {
        InputStream in = new FileInputStream(tempFile);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        OutputStream out = new FileOutputStream(filePath + fileName);
        while ((line = reader.readLine()) != null) {
            Matcher titleMatcher = Pattern.compile("<title>(.*?)</title>").matcher(line);
            if (titleMatcher.find()) {
                String title = titleMatcher.group().replace("_我们仨_杨绛 小说在线阅读", "");
                out.write((title + "\r\n").getBytes());
                continue;
            }
            if (line.startsWith("&nbsp;&nbsp;&nbsp;&nbsp;")) {
                out.write((line.replace("&nbsp;&nbsp;&nbsp;&nbsp;", "") + "\r\n").getBytes());
            }
        }

        out.flush();
        out.close();
        in.close();
    }

    @Override
    public Charset getWebCharset() {
        return Charset.forName("gbk");
    }

    @Override
    public List<String> getUrls() {
        int start = 197769;
        int end = 197790;
        List<String> urls = new ArrayList<>();
        String url = "http://www.kanunu8.com/book4/8930/${index}.html";
        for (int index = start; index < end; index++) {
            String str = url.replace("${index}", index + "");
            urls.add(str);
        }
        return urls;
    }

    public static void main(String[] args) throws IOException {
        MyFamily myFamily = new MyFamily();
        myFamily.process("D:\\", "temp.txt");
    }
}
