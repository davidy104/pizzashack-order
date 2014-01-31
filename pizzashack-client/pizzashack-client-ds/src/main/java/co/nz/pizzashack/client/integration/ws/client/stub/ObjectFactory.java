
package co.nz.pizzashack.client.integration.ws.client.stub;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.nz.pizzashack.client.integration.ws.client.stub package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _BillingResponse_QNAME = new QName("http://ws.integration.pizzashack.nz.co/", "billingResponse");
    private final static QName _BillingProcessResponse_QNAME = new QName("http://ws.integration.pizzashack.nz.co/", "billingProcessResponse");
    private final static QName _Account_QNAME = new QName("http://ws.integration.pizzashack.nz.co/", "account");
    private final static QName _BillingProcess_QNAME = new QName("http://ws.integration.pizzashack.nz.co/", "billingProcess");
    private final static QName _BillingRequest_QNAME = new QName("http://ws.integration.pizzashack.nz.co/", "billingRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.nz.pizzashack.client.integration.ws.client.stub
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BillingResponse }
     * 
     */
    public BillingResponse createBillingResponse() {
        return new BillingResponse();
    }

    /**
     * Create an instance of {@link AccountDto }
     * 
     */
    public AccountDto createAccountDto() {
        return new AccountDto();
    }

    /**
     * Create an instance of {@link BillingDto }
     * 
     */
    public BillingDto createBillingDto() {
        return new BillingDto();
    }

    /**
     * Create an instance of {@link BillingProcess }
     * 
     */
    public BillingProcess createBillingProcess() {
        return new BillingProcess();
    }

    /**
     * Create an instance of {@link Fault }
     * 
     */
    public Fault createFault() {
        return new Fault();
    }

    /**
     * Create an instance of {@link BillingProcessResponse }
     * 
     */
    public BillingProcessResponse createBillingProcessResponse() {
        return new BillingProcessResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.pizzashack.nz.co/", name = "billingResponse")
    public JAXBElement<BillingResponse> createBillingResponse(BillingResponse value) {
        return new JAXBElement<BillingResponse>(_BillingResponse_QNAME, BillingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillingProcessResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.pizzashack.nz.co/", name = "billingProcessResponse")
    public JAXBElement<BillingProcessResponse> createBillingProcessResponse(BillingProcessResponse value) {
        return new JAXBElement<BillingProcessResponse>(_BillingProcessResponse_QNAME, BillingProcessResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.pizzashack.nz.co/", name = "account")
    public JAXBElement<AccountDto> createAccount(AccountDto value) {
        return new JAXBElement<AccountDto>(_Account_QNAME, AccountDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillingProcess }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.pizzashack.nz.co/", name = "billingProcess")
    public JAXBElement<BillingProcess> createBillingProcess(BillingProcess value) {
        return new JAXBElement<BillingProcess>(_BillingProcess_QNAME, BillingProcess.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillingDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.pizzashack.nz.co/", name = "billingRequest")
    public JAXBElement<BillingDto> createBillingRequest(BillingDto value) {
        return new JAXBElement<BillingDto>(_BillingRequest_QNAME, BillingDto.class, null, value);
    }

}
