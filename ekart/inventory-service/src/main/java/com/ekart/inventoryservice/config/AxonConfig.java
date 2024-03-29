//package com.ekart.inventoryservice.config;
//
//import java.util.Collection;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.security.NoTypePermission;
//import com.thoughtworks.xstream.security.NullPermission;
//import com.thoughtworks.xstream.security.PrimitiveTypePermission;
//
//@Configuration
//public class AxonConfig {
// 
//    @Bean
//    public XStream xStream() {
//        XStream xstream = new XStream();
////        xstream.addPermission(NoTypePermission.NONE);
////     // allow some basics
////     xstream.addPermission(NullPermission.NULL);
////     xstream.addPermission(PrimitiveTypePermission.PRIMITIVES);
////     xstream.allowTypeHierarchy(Collection.class);
//     xstream.allowTypesByWildcard(new String[] {
//                "com.ekart.**"
//        });
//        return xstream;
//    }
//}