/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.upnp.control;

/**
 *
 * @author tectux
 */
abstract class ControlMessage {

    private final String defaultEncodingStyle = "http://schemas.xmlsoap.org/soap/encoding/";
    private String encodingStyle = defaultEncodingStyle;

    public ControlMessage() {
    }

    public String getEncodingStyle() {
        return encodingStyle;
    }

    public void setEncodingStyle(String encodingStyle) {
        if (encodingStyle == null) {
            this.encodingStyle = defaultEncodingStyle;
        } else {
            this.encodingStyle = encodingStyle;
        }
    }
}
