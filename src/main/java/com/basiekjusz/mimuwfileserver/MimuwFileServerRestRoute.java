package com.basiekjusz.mimuwfileserver;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
import org.apache.camel.model.rest.RestBindingMode;


@Component
public class MimuwFileServerRestRoute extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    restConfiguration().component("servlet");

    rest("/").get("/{file}").produces("application/octet-stream").route().process(new MimuwFileServerLogger()).process(new MimuwFileServerProcessor()).transform().simple("${body}"); 
  }
}