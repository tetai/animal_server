package cn.zkz.animal.service;

import cn.zkz.animal.model.dto.SortDto;
import cn.zkz.animal.model.po.AnimalInfo;
import cn.zkz.animal.util.Result;

import java.util.List;

public interface IAnimalInfoService {
    AnimalInfo getById(int id);

    List<AnimalInfo> findByCondition(AnimalInfo vo, Integer pageSize, Integer pageNum, List<SortDto> sortList);
    Long findCountByCondition(AnimalInfo vo);

    AnimalInfo findLastOrNextData(AnimalInfo vo, Integer pageType, Integer id, List<SortDto> sortList);

    Result getQueryData();
}
