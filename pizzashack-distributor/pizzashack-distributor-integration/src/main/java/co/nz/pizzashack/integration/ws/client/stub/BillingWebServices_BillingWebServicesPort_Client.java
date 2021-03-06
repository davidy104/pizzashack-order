
package co.nz.pizzashack.integration.ws.client.stub;

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
 * 2014-01-25T21:20:24.969+13:00
 * Generated source version: 2.7.7
 * 
 */
public final class BillingWebServices_BillingWebServicesPort_Client {

    private static final QName SERVICE_NAME = new QName("http://ws.integration.billing.pizzashack.nz.co/", "BillingWebServicesService");

    private BillingWebServices_BillingWebServicesPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = BillingWebServicesService.WSDL_LOCATION;
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
      
        BillingWebServicesService ss = new BillingWebServicesService(wsdlURL, SERVICE_NAME);
        BillingWebServices port = ss.getBillingWebServicesPort();  
        
        {
        System.out.println("Invoking accountAuthentication...");
        co.nz.pizzashack.integration.ws.client.stub.AccountDto _accountAuthentication_arg0 = null;
        try {
            co.nz.pizzashack.integration.ws.client.stub.AccountTransactionRespDto _accountAuthentication__return = port.accountAuthentication(_accountAuthentication_arg0);
            System.out.println("accountAuthentication.result=" + _accountAuthentication__return);

        } catch (FaultMessage e) { 
            System.out.println("Expected exception: FaultMessage has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking getAllTransactionsForAccount...");
        co.nz.pizzashack.integration.ws.client.stub.AccountDto _getAllTransactionsForAccount_arg0 = null;
        try {
            java.util.List<co.nz.pizzashack.integration.ws.client.stub.BillingTransactionDto> _getAllTransactionsForAccount__return = port.getAllTransactionsForAccount(_getAllTransactionsForAccount_arg0);
            System.out.println("getAllTransactionsForAccount.result=" + _getAllTransactionsForAccount__return);

        } catch (FaultMessage e) { 
            System.out.println("Expected exception: FaultMessage has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking billingProcess...");
        co.nz.pizzashack.integration.ws.client.stub.BillingTransactionDto _billingProcess_arg0 = null;
        try {
            co.nz.pizzashack.integration.ws.client.stub.AccountTransactionRespDto _billingProcess__return = port.billingProcess(_billingProcess_arg0);
            System.out.println("billingProcess.result=" + _billingProcess__return);

        } catch (FaultMessage e) { 
            System.out.println("Expected exception: FaultMessage has occurred.");
            System.out.println(e.toString());
        }
            }

        System.exit(0);
    }

}
