package com.alipay.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016093000634617";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCg94cxEjosIfnditdIcrsMxJOlqL1Upecw1Ks/DP24KirpBNnk8E+S8hzD1uQIJz9BAJ+Eqvkry+bGGdeR+dT0rRDQ3Y66TgJzG6RjqtWQfKfgs1TTpEvl3z64YGSgyg3DAMyyVu2evxODOKReeDTzZzFuoEnOeQmgSlFVS9ADbp3mRkcNsCDLQ814nJ88GWJQi3xYEgFouOb7sS8Lxb+5djScSw/iIEGVnmGgXh4ZLJMJq+21fyrYutGm4tsXl2PWLExS9XC+ny6KpAaM08caUAS1losxbvDO7D7Dc26rbJ8wEV7rgKfXC7hok6ghbCSFQcaLf8ciVIHHhtZBC9sDAgMBAAECggEBAIypghRZr0gdsLHuwE5pJBf2OGgNWtX73FoJnlQM0DL9dl4GhTrQ3Vi2BnkE8mRFl7sABmXy2qdxGYuHzZ+cyIHAMu+yjSU1QmLAhxkpklMe+70aOUhxTHnmanLImWcA6qG7syUtXZzZsP18o/aB6RPC26mmnJi3oLfX+dcl1vMHdYlCY/dOPH5UH4aTzS5LV/XIIR+edCbUWSQChMp13fXlHS3do0tzgNHbgP8R+qtSiOU1UVMszzw2dYBGxK3p0uJsm+Vihwr/1MiehIRYaFCKVDEHV1x2R0Wta4kKFurU9XSpTjkvrYOvQ+6o2IgutZAqaY5w6rlolJ5kGbiZxJECgYEA2Wv29mvlPVurATAvxrVMye5Pyz7L7DnTz1wcaINtW6G12xBM0JkglIBq62hEw5ufv7hVRrp5WLyxa7Dz3itpUWrAS3AMJscZDhxMX55AHNvqWW7c9zqEhMZvxORiHMokdpjtW5o1gsxXTAP5hd4GWpC6vEtm+FgSR38VAnaeZkcCgYEAvYcxXntJXXKv+K0lvwdoxNQzhoJZRzcZhGeuIEco9jqWDutc0cXclcCCisv0gzBdYRvnCqSGzomwQft+OybQ3iVMRgnDWqkCC+cLwSGsobzKHejB7SJQBwu/DDEqlbRZdNVVZzG5+NqMxE0oW1B/ji/IW5MhaXGMktACXI2p92UCgYBQZNyPp7u/IWss6E1yM02uiSEWLSUoNwW78fpLJvXD4RqmYvOUWlqyrn1aiFaM4bmDIbzeh0shZbxQ7sBt0S2DPm7WNhs4J3x7PDl9Tb21LhgMc7Le89NWaS2g5zXkIVa0R73UlkPdFoPdBsc4Ga3NxSBXSaZMg/xWVYZGP+g4NwKBgC/hsNJK2V0XdWeg7tFVIN3hMmdAFSgNvo+Z1f7nm2yhnnmEcr24fCMFMLW6Ezr1hBHdh8BDkp8pX/M7g0eUZd5Jk/x5yPXljtPM+kY/qKpv1Kw7uoqbSZaOHCULYtPRbWw73Uil4nMBW+DYEWMIckFMxxSDlkRaYIZSsNYLIC6VAoGBAKcRXphlgcaOCKPDlkjOQ8sXljSbhAfEdS2zr5PunuY0Weu+Ij3EqxNV+vlsr9lxJyvowNRkCbzU08BNy0dr46HVO8vRi2KnHnRIgGM/5HsmfSUPaYg5q9KPQzjyaq5f/iiHwzZ9z5ml10axC+RLLWywO5BVLUlbNWSSl6tvWA+I";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArhyZU1K7SlsK7DxRkSEr5TORH+WKGlNfi8Kk14owPQx57+wjVY0wSsLnVxSafaPW82f1ygn5dHio1YQBd6vuKPDdt8h6Kn5u4yh9ytqaLsOnjqiQAwTn/cNQHiydk7tTbmpHkE+lfVpnOf+C/DsjjObR9+mdDes/fLQsnYZSIymD4wQE0UaYUHG9omShKcbzSOPIcoCapXbs3+mjZSs8CrwM5mWZoCvfSBaUinVIywGgb/XDtvY1cDHrbvkO02Ggno3gMazyNnfiigQTgqswH2Fx4RuyL9fsT0PYcw/KDKHEYJwroEFGYppl4fk0rANMn1oUl/vped8x6ingpUYBFQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/book/OrderServlet/alipayNotifyNotice";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/book/OrderServlet/alipayReturnNotice";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

