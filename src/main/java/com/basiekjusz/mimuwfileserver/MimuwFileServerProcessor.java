package com.basiekjusz.mimuwfileserver;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import java.io.FileInputStream;  
import java.io.File;
import org.springframework.beans.factory.annotation.Value;
import java.io.InputStream;

public class MimuwFileServerProcessor implements Processor {
  @Override
  public void process(Exchange exchange) throws Exception {
    try{
      String rootDirectory = exchange.getContext().resolvePropertyPlaceholders("{{root.dir}}");
      String fileLocation = exchange.getIn().getHeader("file").toString();

      File file = new File(rootDirectory + fileLocation);
    
      InputStream fileContent = new FileInputStream(file);
      exchange.getOut().setBody(fileContent);
    }catch(Exception e){
      exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, "404");
      exchange.getOut().setHeader(Exchange.CONTENT_TYPE, "text/plain");
      exchange.getOut().setBody("Not Found.");
    }
  }
}
