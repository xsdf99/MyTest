package SpringTest.Utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;

public class XmlParseUtil {
    private static final String FILE_PATH = "/application.xml";

    public String getScanPackageName() {
        String packageName = "";
        SAXReader saxReader = new SAXReader();
        InputStream is = this.getClass().getResourceAsStream(FILE_PATH);
        try {
            Document document = saxReader.read(is);
            if (document != null) {
                Element rootElement = document.getRootElement();
                Element element = rootElement.element("scan");
                if (element != null) {
                    packageName = element.attributeValue("package");
                }
            }
            System.out.println(document);
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return packageName;
    }
    
}
