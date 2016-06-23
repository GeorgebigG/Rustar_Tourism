package com.rustaronline.mobile.rustartourism.WSDL;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by gio on 6/23/16.
 */
public class CallSoap {

    public static String getXmlFromSoap(String[] params, String[] names,String SOAP_ACTION, String METHOD_NAME, String WSDL_URL, String XML_VERSION_TAG, String SOAP_ADRESS) {
        SoapObject request = new SoapObject(WSDL_URL, METHOD_NAME);

        if (names.length != params.length)
            return "Error...";

        for (int i = 0; i < params.length; i++) {
            PropertyInfo propertyInfo = new PropertyInfo();
            propertyInfo.setName(names[i]);
            propertyInfo.setValue(params[i]);
            propertyInfo.setType(String.class);
            request.addProperty(propertyInfo);
        }

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);

        try {
            HttpTransportSE transportSE = new HttpTransportSE(SOAP_ADRESS);
            transportSE.setXmlVersionTag(XML_VERSION_TAG);
            transportSE.debug = true;
            transportSE.call(SOAP_ACTION, envelope);

            return  transportSE.responseDump;
        } catch (Exception ex) {
            return "Error...";
        }
    }
}
