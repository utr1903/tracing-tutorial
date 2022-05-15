package com.tracing.tutorial.fifth.service.zipkin.model;

public class LocalEndpoint {

    private String serviceName;
    private String ipv4;
    private int port;

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
}
