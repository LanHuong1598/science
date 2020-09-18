package vn.com.mta.science.api;

import java.io.Serializable;

public class BaseResponseEntity<ID extends Serializable> implements Serializable {

    private ID id;
    private String name;

    public BaseResponseEntity() {
    }

    public BaseResponseEntity(ID id, String name) {
        this.id = id;
        this.name = name;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
