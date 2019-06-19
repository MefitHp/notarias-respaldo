/**
 * DgieWS.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ws.client;

public interface DgieWS extends javax.xml.rpc.Service {
    public java.lang.String getDgieWSPortAddress();

    public ws.client.DgieWSPort_PortType getDgieWSPort() throws javax.xml.rpc.ServiceException;

    public ws.client.DgieWSPort_PortType getDgieWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
