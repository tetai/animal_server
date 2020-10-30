package cn.zkz.animal.service.impl;

import cn.zkz.animal.dao.IAnimalInfoDao;
import cn.zkz.animal.po.AnimalInfo;
import cn.zkz.animal.service.IAnimalInfoService;
import cn.zkz.animal.util.SqlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AnimalInfoService implements IAnimalInfoService {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private IAnimalInfoDao animalInfoDao;

    @Override
    public AnimalInfo getById(int id) {
        return animalInfoDao.findById(id);
    }

    @Override
    public List<AnimalInfo> findByCondition(AnimalInfo vo, Integer pageSize, Integer pageNum) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("from AnimalInfo where 1=1 ");
        if (vo != null) {
            if (!StringUtils.isBlank(vo.getAnimalChinaName())) {
                sql.append(" and animalChinaName like  concat('%',:animalChinaName ,'%') ");
                params.put("animalChinaName", vo.getAnimalChinaName());
            }
            if (!StringUtils.isBlank(vo.getAnimalSex())) {
                sql.append(" and animalSex = :animalSex ");
                params.put("animalSex", vo.getAnimalSex());
            }
            if (vo.getAnimalBridthMon() != null) {
                sql.append(" and animalBridthMon = :animalBridthMon ");
                params.put("animalBridthMon", vo.getAnimalBridthMon());
            }

            if (!StringUtils.isBlank(vo.getAnimalXingge())) {
                sql.append(" and animalXingge = :animalXingge ");
                params.put("animalXingge", vo.getAnimalXingge());
            }
            if (!StringUtils.isBlank(vo.getAnimalZhongzu())) {
                sql.append(" and animalZhongzu = :animalZhongzu ");
                params.put("animalZhongzu", vo.getAnimalZhongzu());
            }
            if (!StringUtils.isBlank(vo.getAnimalLove())) {
                sql.append(" and animalLove = :animalLove ");
                params.put("animalLove", vo.getAnimalLove());
            }
            if (!StringUtils.isBlank(vo.getAnimalChinaName())) {
                sql.append(" and animalChinaName like  concat('%',:name ,'%') ");
            }
        }

        sql.append(" order by animalBridthMon, animalBridthDay");
        List<AnimalInfo> resultList = SqlUtil.getQuery(entityManager, sql.toString(), params, pageSize, pageNum).getResultList();

        return resultList;
    }

    @Override
    public Long findCountByCondition(AnimalInfo vo) {
        return animalInfoDao.count();
    }
}
