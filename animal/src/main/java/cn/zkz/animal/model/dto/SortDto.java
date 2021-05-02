package cn.zkz.animal.model.dto;

import java.io.Serializable;

public class SortDto implements Serializable {

    public SortDto() {
        super();
    }

    public SortDto(String descOrAsc, String sortName) {
        this.descOrAsc = descOrAsc;
        this.sortName = sortName;
    }

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
