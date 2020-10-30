package cn.zkz.animal.po;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "t_key_value")
public class KeyValue implements Serializable {


    @Column(name = "key")
    @Id
    private String key;
    @Column(name = "value")
    private String value;
    @Column(name = "descrition")
    private String descrition;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }
}
