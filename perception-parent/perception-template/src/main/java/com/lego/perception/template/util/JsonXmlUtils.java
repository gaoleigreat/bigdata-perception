package com.lego.perception.template.util;

/**
 * @auther xiaodao
 * @date 2019/9/10 10:15
 */

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * JSON对象与XML相互转换工具类
 *
 * @author gaolei
 */
public class JsonXmlUtils {

    private static final String ENCODING = "UTF-8";

    /**
     * JSON对象转漂亮的xml字符串
     *
     * @param json JSON对象
     * @return 漂亮的xml字符串
     * @throws IOException
     * @throws SAXException
     */
    public static String jsonToPrettyXml(JSONObject json) throws IOException, SAXException {
        Document document = jsonToDocument(json);

        /* 格式化xml */
        OutputFormat format = OutputFormat.createPrettyPrint();

        // 设置缩进为4个空格
        format.setIndent(" ");
        format.setIndentSize(4);

        StringWriter formatXml = new StringWriter();
        XMLWriter writer = new XMLWriter(formatXml, format);
        writer.write(document);

        return formatXml.toString();
    }

    /**
     * JSON对象转xml字符串
     *
     * @param json JSON对象
     * @return xml字符串
     * @throws SAXException
     */
    public static String JsonToXml(JSONObject json) throws SAXException {
        return jsonToDocument(json).asXML();
    }

    /**
     * JSON对象转Document对象
     *
     * @param json JSON对象
     * @return Document对象
     * @throws SAXException
     */
    public static Document jsonToDocument(JSONObject json) throws SAXException {
        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding(ENCODING);

        // root对象只能有一个
        for (String rootKey : json.keySet()) {
            try {
                JSONObject jsonObject = json.getJSONObject(rootKey);

                Element root = jsonToElement(jsonObject, rootKey);
                document.add(root);
            } catch (Exception e) {
                String a = json.getString(rootKey);

                Element root = jsonToElement(a, rootKey);
                document.add(root);
            }

            break;
        }
        return document;
    }

    /**
     * JSON对象转Element对象
     *
     * @param json     JSON对象
     * @param nodeName 节点名称
     * @return Element对象
     */
    public static Element jsonToElement(Object json, String nodeName) {
        Element node = DocumentHelper.createElement(nodeName);
        if (!(json instanceof JSONObject)) {

            node.setText(json.toString());
            //node.add(element);
        } else {
            JSONObject jd = (JSONObject) json;
            for (String key : jd.keySet()) {
                Object child = jd.get(key);
                if (child instanceof JSONObject) {
                    node.add(jsonToElement(jd.getJSONObject(key), key));
                } else {
                    Element element = DocumentHelper.createElement(key);
                    element.setText(jd.getString(key));
                    node.add(element);
                }
            }
        }


        return node;
    }

    /**
     * XML字符串转JSON对象
     *
     * @param xml xml字符串
     * @return JSON对象
     * @throws DocumentException
     */
    public static JSONObject xmlToJson(String xml) throws DocumentException {
        JSONObject json = new JSONObject();

        SAXReader reader = new SAXReader();
        Document document = reader.read(new StringReader(xml));
        Element root = document.getRootElement();

        json.put(root.getName(), elementToJson(root));

        return json;
    }

    /**
     * Element对象转JSON对象
     *
     * @param element Element对象
     * @return JSON对象
     */
    public static JSONObject elementToJson(Element element) {
        JSONObject json = new JSONObject();
        for (Object child : element.elements()) {
            Element e = (Element) child;
            if (e.elements().isEmpty()) {
                json.put(e.getName(), e.getText());
            } else {
                json.put(e.getName(), elementToJson(e));
            }
        }

        return json;
    }

    /**
     * 文件内容转换成字符串
     *
     * @param filePath 文件路径
     * @return 内容字符串
     * @throws IOException
     */
    public static String fileToString(URL filePath) throws IOException {
        return IOUtils.toString(filePath, ENCODING);
    }

    /**
     * 文件内容转换成字符串
     *
     * @param filePath 文件路径
     * @return 内容字符串
     * @throws IOException
     */
    public static String fileToString(String filePath) throws IOException {
        return IOUtils.toString(Paths.get(filePath).toUri(), ENCODING);
    }

    /**
     * 字符串输出到文件
     *
     * @param str      字符串内容
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void stringToFile(String str, String filePath) throws IOException {
        FileUtils.writeStringToFile(Paths.get(filePath).toFile(), str, ENCODING);
    }

    /**
     * 字符串输出到文件
     *
     * @param str      字符串内容
     * @param filePath 文件路径
     * @throws IOException
     */
    public static void stringToFile(String str, URL filePath) throws IOException {
        FileUtils.writeStringToFile(new File(filePath.getPath()), str, ENCODING);
    }

    /**
     * 字符串输出到文件
     *
     * @param str  字符串内容
     * @param file 输出文件
     * @throws IOException
     */
    public static void stringToFile(String str, File file) throws IOException {
        FileUtils.writeStringToFile(file, str, ENCODING);
    }

    public static void main(String[] args) {
        try {
            String filePath = "/pom.xml";
            URL url = JsonXmlUtils.class.getResource(filePath);
            String content = JsonXmlUtils.fileToString(url);
            // System.out.println(content);

            JSONObject json = xmlToJson(content);
            System.out.println(JSON.toJSONString(json, true));

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "xiaodao");

            String xml = JsonToXml(jsonObject);
            System.out.println(xml);

            System.out.println("----------------------------------------\n\n");
            xml = jsonToPrettyXml(json);
            System.out.println(xml);

            /*  stringToFile(xml, "E:\\Temp\\Test\\User.xml");*/
        } catch (Exception e) {
        }
    }


    public static String formatJson(String json) {
        StringBuffer result = new StringBuffer();

        int length = json.length();
        int number = 0;
        char key = 0;

        // 遍历输入字符串。
        for (int i = 0; i < length; i++) {
            // 1、获取当前字符。
            key = json.charAt(i);

            // 2、如果当前字符是前方括号、前花括号做如下处理：
            if ((key == '[') || (key == '{')) {
                // （1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
                if ((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
                    result.append('\n');
                    result.append(indent(number));
                }

                // （2）打印：当前字符。
                result.append(key);

                // （3）前方括号、前花括号，的后面必须换行。打印：换行。
                result.append('\n');

                // （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
                number++;
                result.append(indent(number));

                // （5）进行下一次循环。
                continue;
            }

            // 3、如果当前字符是后方括号、后花括号做如下处理：
            if ((key == ']') || (key == '}')) {
                // （1）后方括号、后花括号，的前面必须换行。打印：换行。
                result.append('\n');

                // （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
                number--;
                result.append(indent(number));

                // （3）打印：当前字符。
                result.append(key);

                // （4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
                if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
                    result.append('\n');
                }

                // （5）继续下一次循环。
                continue;
            }

            // 4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
            /*if ((key == ',')) {
                result.append(key);
                result.append('\n');
                result.append(indent(number));
                continue;
            }*/

            // 5、打印：当前字符。
            result.append(key);
        }

        return result.toString();
    }

    /**
     * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。
     *
     * @param number 缩进次数。
     * @return 指定缩进次数的字符串。
     */
    public static   String indent(int number) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < number; i++) {
            result.append("   ");
        }
        return result.toString();
    }


}
