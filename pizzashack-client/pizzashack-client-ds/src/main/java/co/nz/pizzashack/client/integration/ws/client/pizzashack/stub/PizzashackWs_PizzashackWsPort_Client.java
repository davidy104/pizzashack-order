
package co.nz.pizzashack.client.integration.ws.client.pizzashack.stub;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.7
 * 2014-03-10T15:15:05.944+13:00
 * Generated source version: 2.7.7
 * 
 */
public final class PizzashackWs_PizzashackWsPort_Client {

    private static final QName SERVICE_NAME = new QName("http://ws.integration.pizzashack.nz.co/", "PizzashackWsService");

    private PizzashackWs_PizzashackWsPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = PizzashackWsService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        PizzashackWsService ss = new PizzashackWsService(wsdlURL, SERVICE_NAME);
        PizzashackWs port = ss.getPizzashackWsPort();  
        
        {
        System.out.println("Invoking getPizzashackByName...");
        java.lang.String _getPizzashackByName_arg0 = "";
        try {
            co.nz.pizzashack.client.integration.ws.client.pizzashack.stub.PizzashackDto _getPizzashackByName__return = port.getPizzashackByName(_getPizzashackByName_arg0);
            System.out.println("getPizzashackByName.result=" + _getPizzashackByName__return);

        } catch (FaultMessage e) { 
            System.out.println("Expected exception: FaultMessage has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
