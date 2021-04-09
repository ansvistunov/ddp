
package webservice.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the webservice.service package. 
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

    private final static QName _CreateCar_QNAME = new QName("http://server.webservice/", "createCar");
    private final static QName _CreateCarResponse_QNAME = new QName("http://server.webservice/", "createCarResponse");
    private final static QName _DestroyCar_QNAME = new QName("http://server.webservice/", "destroyCar");
    private final static QName _DestroyCarResponse_QNAME = new QName("http://server.webservice/", "destroyCarResponse");
    private final static QName _MoveCarTo_QNAME = new QName("http://server.webservice/", "moveCarTo");
    private final static QName _MoveCarToResponse_QNAME = new QName("http://server.webservice/", "moveCarToResponse");
    private final static QName _SetCarName_QNAME = new QName("http://server.webservice/", "setCarName");
    private final static QName _SetCarNameResponse_QNAME = new QName("http://server.webservice/", "setCarNameResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: webservice.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateCar }
     * 
     */
    public CreateCar createCreateCar() {
        return new CreateCar();
    }

    /**
     * Create an instance of {@link CreateCarResponse }
     * 
     */
    public CreateCarResponse createCreateCarResponse() {
        return new CreateCarResponse();
    }

    /**
     * Create an instance of {@link DestroyCar }
     * 
     */
    public DestroyCar createDestroyCar() {
        return new DestroyCar();
    }

    /**
     * Create an instance of {@link DestroyCarResponse }
     * 
     */
    public DestroyCarResponse createDestroyCarResponse() {
        return new DestroyCarResponse();
    }

    /**
     * Create an instance of {@link MoveCarTo }
     * 
     */
    public MoveCarTo createMoveCarTo() {
        return new MoveCarTo();
    }

    /**
     * Create an instance of {@link MoveCarToResponse }
     * 
     */
    public MoveCarToResponse createMoveCarToResponse() {
        return new MoveCarToResponse();
    }

    /**
     * Create an instance of {@link SetCarName }
     * 
     */
    public SetCarName createSetCarName() {
        return new SetCarName();
    }

    /**
     * Create an instance of {@link SetCarNameResponse }
     * 
     */
    public SetCarNameResponse createSetCarNameResponse() {
        return new SetCarNameResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateCar }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice/", name = "createCar")
    public JAXBElement<CreateCar> createCreateCar(CreateCar value) {
        return new JAXBElement<CreateCar>(_CreateCar_QNAME, CreateCar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCarResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link CreateCarResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice/", name = "createCarResponse")
    public JAXBElement<CreateCarResponse> createCreateCarResponse(CreateCarResponse value) {
        return new JAXBElement<CreateCarResponse>(_CreateCarResponse_QNAME, CreateCarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyCar }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DestroyCar }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice/", name = "destroyCar")
    public JAXBElement<DestroyCar> createDestroyCar(DestroyCar value) {
        return new JAXBElement<DestroyCar>(_DestroyCar_QNAME, DestroyCar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DestroyCarResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DestroyCarResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice/", name = "destroyCarResponse")
    public JAXBElement<DestroyCarResponse> createDestroyCarResponse(DestroyCarResponse value) {
        return new JAXBElement<DestroyCarResponse>(_DestroyCarResponse_QNAME, DestroyCarResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveCarTo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MoveCarTo }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice/", name = "moveCarTo")
    public JAXBElement<MoveCarTo> createMoveCarTo(MoveCarTo value) {
        return new JAXBElement<MoveCarTo>(_MoveCarTo_QNAME, MoveCarTo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MoveCarToResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MoveCarToResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice/", name = "moveCarToResponse")
    public JAXBElement<MoveCarToResponse> createMoveCarToResponse(MoveCarToResponse value) {
        return new JAXBElement<MoveCarToResponse>(_MoveCarToResponse_QNAME, MoveCarToResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetCarName }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SetCarName }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice/", name = "setCarName")
    public JAXBElement<SetCarName> createSetCarName(SetCarName value) {
        return new JAXBElement<SetCarName>(_SetCarName_QNAME, SetCarName.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetCarNameResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link SetCarNameResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://server.webservice/", name = "setCarNameResponse")
    public JAXBElement<SetCarNameResponse> createSetCarNameResponse(SetCarNameResponse value) {
        return new JAXBElement<SetCarNameResponse>(_SetCarNameResponse_QNAME, SetCarNameResponse.class, null, value);
    }

}
