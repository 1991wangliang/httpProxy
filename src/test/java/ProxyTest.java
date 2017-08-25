

import org.apache.commons.io.IOUtils;
import org.springframework.util.Base64Utils;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

/**
 * create by lorne on 2017/8/25
 */
public class ProxyTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            URL url = new URL("http://www.baidu.com");
            // 创建代理服务器
            InetSocketAddress addr = new InetSocketAddress("127.0.0.1",80);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http 代理
            URLConnection conn = url.openConnection(proxy);

            //以下三行是在需要验证时，输入帐号密码信息
            String headerkey = "Proxy-Authorization";

            String headerValue = "Basic "+ new String(Base64Utils.encode("atco:atco".getBytes())); //帐号密码用:隔开，base64加密方式
            conn.setRequestProperty(headerkey, headerValue);

            InputStream in = conn.getInputStream();
            // InputStream in = url.openStream();
            String s = IOUtils.toString(in, "utf-8");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
