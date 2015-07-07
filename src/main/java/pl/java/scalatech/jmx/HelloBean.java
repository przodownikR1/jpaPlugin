package pl.java.scalatech.jmx;

import org.springframework.stereotype.Component;

@Component
public interface HelloBean {
public int add(int x, int y);
public String sayHello();
public void setName(String name);
public String getName();
public String getVersion();
}