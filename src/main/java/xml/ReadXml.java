package xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by ShenShuaihu on 2018/7/18.
 * DOM  方式
 */
public class ReadXml {

    public static void main(String[] args) {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse("code/lang.xml");
            Element root = document.getDocumentElement();               // 获取根标签
            System.out.println("xmlns:"+root.getAttribute("xmlns"));  // 取属性的值
            NodeList list = root.getElementsByTagName("dependency");    // 取标签的内容
            for (int i = 0; i<list.getLength(); i++){
                Element lan = (Element) list.item(i);
                System.out.println("-------------------");
                System.out.println("id= "+lan.getAttribute("id"));
                NodeList   nodesList= lan.getChildNodes();
                for (int j = 0; j<nodesList.getLength(); j++){
                  Node node = nodesList.item(j);
                  if (node instanceof Element) {   // 过了隐藏的节点
                      System.out.println(node.getNodeName() + " -- " + node.getTextContent());
                  }
                }
            }

        } catch (ParserConfigurationException e) {
        }catch (SAXException e){
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
