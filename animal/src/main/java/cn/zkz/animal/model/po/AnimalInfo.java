package cn.zkz.animal.model.po;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;

@Entity
@Table(name = "t_animal_info")
public class AnimalInfo implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "animal_ri_name")
    private String animalRiName;
    @Column(name = "animal_birthday")
    private String animalBirthday;
    @Column(name = "animal_img")
    private String animalImg;
    @Column(name = "animal_china_name")
    private String animalChinaName;
    @Column(name = "animal_eng_name")
    private String animalEngName;
    @Column(name = "animal_sex")
    private String animalSex;
    @Column(name = "animal_init_ktc")
    private String animalInitKtc;
    @Column(name = "animal_love")
    private String animalLove;
    @Column(name = "animal_color")
    private String animalColor;
    @Column(name = "animal_fgph")
    private String animalFgph;
    @Column(name = "animal_yingao")
    private String animalYingao;
    @Column(name = "animal_zhongzu")
    private String animalZhongzu;
    @Column(name = "animal_zym")
    private String animalZym;
    @Column(name = "animal_bridth_mon")
    private Integer animalBridthMon;
    @Column(name = "animal_bridth_day")
    private Integer animalBridthDay;
    @Column(name = "animal_xingge")
    private String animalXingge;

    private String startTime;
    private String endTime;

    public static String findByColName(String colName) {
        Class<AnimalInfo> clazz = AnimalInfo.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field f : declaredFields) {
            Column annotation = f.getAnnotation(Column.class);
            if (annotation != null && f.getName().equals(colName)) {
                return annotation.name();
            }
        }
        return "";
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAnimalXingge() {
        return animalXingge;
    }

    public void setAnimalXingge(String animalXingge) {
        this.animalXingge = animalXingge;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnimalRiName() {
        return animalRiName;
    }

    public void setAnimalRiName(String animalRiName) {
        this.animalRiName = animalRiName;
    }

    public String getAnimalBirthday() {
        return animalBirthday;
    }

    public void setAnimalBirthday(String animalBirthday) {
        this.animalBirthday = animalBirthday;
    }

    public String getAnimalImg() {
        String temp = animalImg.substring(animalImg.lastIndexOf("/")+1);
        String url = "animalInfo/image/" + temp;
        return url;
    }

    public void setAnimalImg(String animalImg) {
//        https://patchwiki.biligame.com/images/dongsen/1/1a/auyz5rbgrl3lq22lk6032qaw9nsf81x.png


        this.animalImg = animalImg;
    }

    public String getAnimalChinaName() {
        return animalChinaName;
    }

    public void setAnimalChinaName(String animalChinaName) {
        this.animalChinaName = animalChinaName;
    }

    public String getAnimalEngName() {
        return animalEngName;
    }

    public void setAnimalEngName(String animalEngName) {
        this.animalEngName = animalEngName;
    }

    public String getAnimalSex() {
        return animalSex;
    }

    public void setAnimalSex(String animalSex) {
        this.animalSex = animalSex;
    }

    public String getAnimalInitKtc() {
        return animalInitKtc;
    }

    public void setAnimalInitKtc(String animalInitKtc) {
        this.animalInitKtc = animalInitKtc;
    }

    public String getAnimalLove() {
        return animalLove;
    }

    public void setAnimalLove(String animalLove) {
        this.animalLove = animalLove;
    }

    public String getAnimalColor() {
        return animalColor;
    }

    public void setAnimalColor(String animalColor) {
        this.animalColor = animalColor;
    }

    public String getAnimalFgph() {
        return animalFgph;
    }

    public void setAnimalFgph(String animalFgph) {
        this.animalFgph = animalFgph;
    }

    public String getAnimalYingao() {
        return animalYingao;
    }

    public void setAnimalYingao(String animalYingao) {
        this.animalYingao = animalYingao;
    }

    public String getAnimalZhongzu() {
        return animalZhongzu;
    }

    public void setAnimalZhongzu(String animalZhongzu) {
        this.animalZhongzu = animalZhongzu;
    }

    public String getAnimalZym() {
        return animalZym;
    }

    public void setAnimalZym(String animalZym) {
        this.animalZym = animalZym;
    }

    public Integer getAnimalBridthMon() {
        return animalBridthMon;
    }

    public void setAnimalBridthMon(Integer animalBridthMon) {
        this.animalBridthMon = animalBridthMon;
    }

    public Integer getAnimalBridthDay() {
        return animalBridthDay;
    }

    public void setAnimalBridthDay(Integer animalBridthDay) {
        this.animalBridthDay = animalBridthDay;
    }
}
