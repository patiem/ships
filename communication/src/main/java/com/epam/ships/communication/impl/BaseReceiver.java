package com.epam.ships.communication.impl;

import com.epam.ships.communication.api.Receiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BaseReceiver implements Receiver {

    private static Logger logger = LogManager.getLogger(BaseReceiver.class);

    private final InputStream inputStream;

    public BaseReceiver(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public JSONObject receive() throws IOException {
//        StringWriter writer = new StringWriter();
// InputStreamReader reader = new InputStreamReader(inputStream);
//        int result = reader.read();
//
//        System.out.println(result);
//        String theString = writer.toString();
//        if(theString == null || theString.isEmpty()) {
//            return new JSONObject("{ \"kupa\":\"dupa\"}");
//        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        byte[] buffer = new byte[1024];
        int readBytes = bufferedInputStream.read(buffer, 0 , buffer.length);

        if(readBytes < 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("end", "true");
            return jsonObject;
        }
        while(readBytes  > 1){
            baos.write(buffer,0,readBytes);
            if(inputStream.available() <= 0) {
                break;
            }
        }

        //System.out.println(theString);
        return new JSONObject(baos.toString());
    }
}
