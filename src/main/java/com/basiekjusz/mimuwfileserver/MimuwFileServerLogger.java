package com.basiekjusz.mimuwfileserver;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    
import java.io.FileOutputStream;  
import java.io.PrintStream;
import java.io.File;

public class MimuwFileServerLogger implements Processor {
  @Override
  public void process(Exchange exchange) throws Exception {
    String logDirectory = exchange.getContext().resolvePropertyPlaceholders("{{log.dir}}");
    File logFile = new File(logDirectory);
    FileOutputStream logOutputStream = new FileOutputStream(logFile, true);
    PrintStream printStream = new PrintStream(logOutputStream);

    HttpServletRequest request = exchange.getIn().getBody(HttpServletRequest.class);
    String clientIP = request.getRemoteAddr();

    String fileLocation = exchange.getIn().getHeader("file").toString();

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    LocalDateTime now = LocalDateTime.now();
    String date = dtf.format(now);

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("[").append(date).append("]: ");
    stringBuilder.append(clientIP).append(" GET ").append(fileLocation).append("\n");

    printStream.print(stringBuilder.toString());
  }
}
