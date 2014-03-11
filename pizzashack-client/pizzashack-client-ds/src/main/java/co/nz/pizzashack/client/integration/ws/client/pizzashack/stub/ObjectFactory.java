
package co.nz.pizzashack.client.integration.ws.client.pizzashack.stub;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.nz.pizzashack.client.integration.ws.client.pizzashack.stub package. 
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

    private final static QName _GetPizzashackByName_QNAME = new QName("http://ws.integration.pizzashack.nz.co/", "getPizzashackByName");
    private final static QName _GetPizzashackByNameResponse_QNAME = new QName("http://ws.integration.pizzashack.nz.co/", "getPizzashackByNameResponse");
    private final static QName _PizzashackDto_QNAME = new QName("http://ws.integration.pizzashack.nz.co/", "pizzashackDto");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.nz.pizzashack.client.integration.ws.client.pizzashack.stub
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PizzashackDto }
     * 
     */
    public PizzashackDto createPizzashackDto() {
        return new PizzashackDto();
    }

    /**
     * Create an instance of {@link GetPizzashackByName }
     * 
     */
    public GetPizzashackByName createGetPizzashackByName() {
        return new GetPizzashackByName();
    }

    /**
     * Create an instance of {@link Fault }
     * 
     */
    public Fault createFault() {
        return new Fault();
    }

    /**
     * Create an instance of {@link GetPizzashackByNameResponse }
     * 
     */
    public GetPizzashackByNameResponse createGetPizzashackByNameResponse() {
        return new GetPizzashackByNameResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPizzashackByName }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.pizzashack.nz.co/", name = "getPizzashackByName")
    public JAXBElement<GetPizzashackByName> createGetPizzashackByName(GetPizzashackByName value) {
        return new JAXBElement<GetPizzashackByName>(_GetPizzashackByName_QNAME, GetPizzashackByName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPizzashackByNameResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.pizzashack.nz.co/", name = "getPizzashackByNameResponse")
    public JAXBElement<GetPizzashackByNameResponse> createGetPizzashackByNameResponse(GetPizzashackByNameResponse value) {
        return new JAXBElement<GetPizzashackByNameResponse>(_GetPizzashackByNameResponse_QNAME, GetPizzashackByNameResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PizzashackDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.pizzashack.nz.co/", name = "pizzashackDto")
    public JAXBElement<PizzashackDto> createPizzashackDto(PizzashackDto value) {
        return new JAXBElement<PizzashackDto>(_PizzashackDto_QNAME, PizzashackDto.class, null, value);
    }

}
