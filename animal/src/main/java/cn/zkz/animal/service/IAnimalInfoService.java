package cn.zkz.animal.service;

import cn.zkz.animal.po.AnimalInfo;

import java.util.List;

public interface IAnimalInfoService {
    AnimalInfo getById(int id);

    List<AnimalInfo> findByCondition(AnimalInfo vo, Integer pageSize, Integer pageNum);
    Long findCountByCondition(AnimalInfo vo);
}
