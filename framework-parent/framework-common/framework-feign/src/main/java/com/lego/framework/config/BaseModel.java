package com.lego.framework.config;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel {

    private Long id;

    private Date creationDate;

    private Long createdBy;

    private Date lastUpdateDate;

    private Long lastUpdatedBy;

    private String lastUpdateUser;

    private String createUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateInfo() {
        Date currentDate=new Date();
        this.creationDate=currentDate;
        this.lastUpdateDate=currentDate;
    }

    public void setUpdateInfo() {
        this.lastUpdateDate=new Date();
    }
}
