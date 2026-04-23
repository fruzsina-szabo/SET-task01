package com.set.image_service_api.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ImageDto {


    private Integer id;
    private String object_path;
    private String object_size;
    private Instant time_added;
    private Instant time_updated;
    private List<String> lables;
    private ImageStatus status;

    public ImageDto() {
        this.lables = new ArrayList<>();
    }

    public ImageDto(
        Integer id,
        String object_path,
        String object_size,Instant time_added,
        Instant time_updated,
        List<String> lables,
        ImageStatus status
    ) {
        this.id = id;
        this.object_path = object_path;
        this.object_size = object_size;
        this.time_added = time_added;
        this.time_updated = time_updated;
        this.lables = lables != null ? lables : new ArrayList<>();
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObject_path() {
        return object_path;
    }

    public void setObject_path(String object_path) {
        this.object_path = object_path;
    }

    public String getObject_size() {
        return object_size;
    }

    public void setObject_size(String object_size) {
        this.object_size = object_size;
    }

    public Instant getTime_added() {
        return time_added;
    }

    public void setTime_added(Instant time_added) {
        this.time_added = time_added;
    }

    public Instant getTime_updated() {
        return time_updated;
    }

    public void setTime_updated(Instant time_updated) {
        this.time_updated = time_updated;
    }

    public List<String> getLables() {
        return lables;
    }

    public void setLables(List<String> lables) {
        this.lables = lables;
    }

    public ImageStatus getStatus() {
        return status;
    }

    public void setStatus(ImageStatus status) {
        this.status = status;
    }
}
