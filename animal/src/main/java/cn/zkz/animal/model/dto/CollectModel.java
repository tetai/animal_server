package cn.zkz.animal.model.dto;

public class CollectModel {


    private String zhongzu;
    private Integer size;
    private String pipei = "";

    public String getPipei() {
        return pipei;
    }

    public void setPipei(String pipei) {
        this.pipei = pipei;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getZhongzu() {
        return zhongzu;
    }

    public void setZhongzu(String zhongzu) {
        this.zhongzu = zhongzu;
    }
}
