
package org.nine_abyss.util;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;

public class TransformerUtil {
    Transformer transformer;

    public TransformerUtil(IXUtil ixUtil) throws TransformerException {
        ixUtil.m.lock(true);
        try {
            this.transformer = TransformerFactory.newInstance().newTransformer();
        } catch (TransformerConfigurationException ignore) {
        }
        if (this.transformer != null) {
            this.transformer.transform(new DOMSource(), new DOMResult());
        }
    }
}
