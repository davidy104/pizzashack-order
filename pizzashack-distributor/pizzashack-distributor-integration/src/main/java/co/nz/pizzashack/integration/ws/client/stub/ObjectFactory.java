
package co.nz.pizzashack.integration.ws.client.stub;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.nz.pizzashack.integration.ws.client.stub package. 
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

    private final static QName _Billing_QNAME = new QName("http://ws.integration.billing.pizzashack.nz.co/", "billing");
    private final static QName _Account_QNAME = new QName("http://ws.integration.billing.pizzashack.nz.co/", "account");
    private final static QName _AccountAuthentication_QNAME = new QName("http://ws.integration.billing.pizzashack.nz.co/", "accountAuthentication");
    private final static QName _AccountAuthenticationResponse_QNAME = new QName("http://ws.integration.billing.pizzashack.nz.co/", "accountAuthenticationResponse");
    private final static QName _GetAllTransactionsForAccount_QNAME = new QName("http://ws.integration.billing.pizzashack.nz.co/", "getAllTransactionsForAccount");
    private final static QName _AccountTransaction_QNAME = new QName("http://ws.integration.billing.pizzashack.nz.co/", "account-transaction");
    private final static QName _BillingProcess_QNAME = new QName("http://ws.integration.billing.pizzashack.nz.co/", "billingProcess");
    private final static QName _GetAllTransactionsForAccountResponse_QNAME = new QName("http://ws.integration.billing.pizzashack.nz.co/", "getAllTransactionsForAccountResponse");
    private final static QName _BillingProcessResponse_QNAME = new QName("http://ws.integration.billing.pizzashack.nz.co/", "billingProcessResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.nz.pizzashack.integration.ws.client.stub
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BillingTransactionDto }
     * 
     */
    public BillingTransactionDto createBillingTransactionDto() {
        return new BillingTransactionDto();
    }

    /**
     * Create an instance of {@link GetAllTransactionsForAccount }
     * 
     */
    public GetAllTransactionsForAccount createGetAllTransactionsForAccount() {
        return new GetAllTransactionsForAccount();
    }

    /**
     * Create an instance of {@link AccountAuthenticationResponse }
     * 
     */
    public AccountAuthenticationResponse createAccountAuthenticationResponse() {
        return new AccountAuthenticationResponse();
    }

    /**
     * Create an instance of {@link AccountAuthentication }
     * 
     */
    public AccountAuthentication createAccountAuthentication() {
        return new AccountAuthentication();
    }

    /**
     * Create an instance of {@link AccountDto }
     * 
     */
    public AccountDto createAccountDto() {
        return new AccountDto();
    }

    /**
     * Create an instance of {@link AccountTransactionRespDto }
     * 
     */
    public AccountTransactionRespDto createAccountTransactionRespDto() {
        return new AccountTransactionRespDto();
    }

    /**
     * Create an instance of {@link GetAllTransactionsForAccountResponse }
     * 
     */
    public GetAllTransactionsForAccountResponse createGetAllTransactionsForAccountResponse() {
        return new GetAllTransactionsForAccountResponse();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link BillingTransactionDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.billing.pizzashack.nz.co/", name = "billing")
    public JAXBElement<BillingTransactionDto> createBilling(BillingTransactionDto value) {
        return new JAXBElement<BillingTransactionDto>(_Billing_QNAME, BillingTransactionDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.billing.pizzashack.nz.co/", name = "account")
    public JAXBElement<AccountDto> createAccount(AccountDto value) {
        return new JAXBElement<AccountDto>(_Account_QNAME, AccountDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountAuthentication }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.billing.pizzashack.nz.co/", name = "accountAuthentication")
    public JAXBElement<AccountAuthentication> createAccountAuthentication(AccountAuthentication value) {
        return new JAXBElement<AccountAuthentication>(_AccountAuthentication_QNAME, AccountAuthentication.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountAuthenticationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.billing.pizzashack.nz.co/", name = "accountAuthenticationResponse")
    public JAXBElement<AccountAuthenticationResponse> createAccountAuthenticationResponse(AccountAuthenticationResponse value) {
        return new JAXBElement<AccountAuthenticationResponse>(_AccountAuthenticationResponse_QNAME, AccountAuthenticationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllTransactionsForAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.billing.pizzashack.nz.co/", name = "getAllTransactionsForAccount")
    public JAXBElement<GetAllTransactionsForAccount> createGetAllTransactionsForAccount(GetAllTransactionsForAccount value) {
        return new JAXBElement<GetAllTransactionsForAccount>(_GetAllTransactionsForAccount_QNAME, GetAllTransactionsForAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountTransactionRespDto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.billing.pizzashack.nz.co/", name = "account-transaction")
    public JAXBElement<AccountTransactionRespDto> createAccountTransaction(AccountTransactionRespDto value) {
        return new JAXBElement<AccountTransactionRespDto>(_AccountTransaction_QNAME, AccountTransactionRespDto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillingProcess }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.billing.pizzashack.nz.co/", name = "billingProcess")
    public JAXBElement<BillingProcess> createBillingProcess(BillingProcess value) {
        return new JAXBElement<BillingProcess>(_BillingProcess_QNAME, BillingProcess.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllTransactionsForAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.billing.pizzashack.nz.co/", name = "getAllTransactionsForAccountResponse")
    public JAXBElement<GetAllTransactionsForAccountResponse> createGetAllTransactionsForAccountResponse(GetAllTransactionsForAccountResponse value) {
        return new JAXBElement<GetAllTransactionsForAccountResponse>(_GetAllTransactionsForAccountResponse_QNAME, GetAllTransactionsForAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BillingProcessResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.integration.billing.pizzashack.nz.co/", name = "billingProcessResponse")
    public JAXBElement<BillingProcessResponse> createBillingProcessResponse(BillingProcessResponse value) {
        return new JAXBElement<BillingProcessResponse>(_BillingProcessResponse_QNAME, BillingProcessResponse.class, null, value);
    }

}
