package com.tracing.tutorial.fifth.service.zipkin.model;

import java.util.Map;

public class ZipkinTrace {

    private String id;
    private String traceId;
    private String parentId;
    private String name;
    private long timestamp;
    private long duration;
    private String kind;
    private LocalEndpoint localEndpoint;
    private RemoteEndpoint remoteEndpoint;
    private Map<String, String> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public LocalEndpoint getLocalEndpoint() {
        return localEndpoint;
    }

    public void setLocalEndpoint(LocalEndpoint localEndpoint) {
        this.localEndpoint = localEndpoint;
    }

    public RemoteEndpoint getRemoteEndpoint() {
        return remoteEndpoint;
    }

    public void setRemoteEndpoint(RemoteEndpoint remoteEndpoint) {
        this.remoteEndpoint = remoteEndpoint;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public void setTags(Map<String, String> tags) {
        this.tags = tags;
    }
}
