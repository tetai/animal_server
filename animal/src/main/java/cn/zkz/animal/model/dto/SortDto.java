package cn.zkz.animal.model.dto;

import org.springframework.stereotype.Service;

import java.io.Serializable;

public class SortDto implements Serializable {

    private String sortName;
    private String descOrAsc;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getDescOrAsc() {
        return descOrAsc;
    }

    public void setDescOrAsc(String descOrAsc) {
        this.descOrAsc = descOrAsc;
    }
}
