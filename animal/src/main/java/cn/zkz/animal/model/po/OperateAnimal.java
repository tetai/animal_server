package cn.zkz.animal.model.po;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_operate_animal")
public class OperateAnimal implements Serializable {

    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private String id;
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "animal_id")
    private String animalId;

    @Column(name = "likeit")
    private Integer likeIt;

    @Column(name = "noticeit")
    private Integer noticeIt;

    @Column(name = "move_into")
    private Integer moveInto;

    @Column(name = "move_out")
    private Integer moveOut;

    @Column(name = "like_time")
    private Date likeTime;

    @Column(name = "notice_time")
    private Date noticeTime;

    @Column(name = "move_out_time")
    private Date moveOutTime;

    @Column(name = "move_into_time")
    private Date moveIntoTime;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAnimalId() {
        return animalId;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public Integer getLikeIt() {
        return likeIt;
    }

    public void setLikeIt(Integer likeIt) {
        this.likeIt = likeIt;
    }

    public Integer getNoticeIt() {
        return noticeIt;
    }

    public void setNoticeIt(Integer noticeIt) {
        this.noticeIt = noticeIt;
    }

    public Integer getMoveInto() {
        return moveInto;
    }

    public void setMoveInto(Integer moveInto) {
        this.moveInto = moveInto;
    }

    public Integer getMoveOut() {
        return moveOut;
    }

    public void setMoveOut(Integer moveOut) {
        this.moveOut = moveOut;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }

    public Date getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(Date noticeTime) {
        this.noticeTime = noticeTime;
    }

    public Date getMoveOutTime() {
        return moveOutTime;
    }

    public void setMoveOutTime(Date moveOutTime) {
        this.moveOutTime = moveOutTime;
    }

    public Date getMoveIntoTime() {
        return moveIntoTime;
    }

    public void setMoveIntoTime(Date moveIntoTime) {
        this.moveIntoTime = moveIntoTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
