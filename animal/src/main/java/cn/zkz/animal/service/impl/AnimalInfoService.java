package cn.zkz.animal.service.impl;

import cn.zkz.animal.dao.IAnimalInfoDao;
import cn.zkz.animal.model.dto.SortDto;
import cn.zkz.animal.model.po.AnimalInfo;
import cn.zkz.animal.service.IAnimalInfoService;
import cn.zkz.animal.util.SqlUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
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
    public List<AnimalInfo> findByCondition(AnimalInfo vo, Integer pageSize, Integer pageNum, List<SortDto> sortList) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder sql = new StringBuilder("from AnimalInfo where 1=1 ");
        sql.append(getSql(vo, params));

        String sortSql = getOrderBy(sortList);
        sql.append(" order by ").append(sortSql);

        sql.append(" animalBridthMon, animalBridthDay ");
        List<AnimalInfo> resultList = SqlUtil.getQuery(entityManager, sql.toString(), params, pageSize, pageNum).getResultList();

        return resultList;
    }

    public String getOrderBy(List<SortDto> sortList) {
        String sortSql = " ";
        if (sortList != null && sortList.size() > 0) {
            for (SortDto dto : sortList) {
                String byColName = AnimalInfo.findByColName(dto.getSortName());
                if (StringUtils.isNotBlank(byColName)) {
                    sortSql += byColName + " " + dto.getDescOrAsc() + ", ";
                }

            }
        }

        return sortSql;
    }

    public String getSql(AnimalInfo vo, Map<String, Object> params) {
        StringBuilder sql = new StringBuilder(" ");

        if (vo != null) {
            if (!StringUtils.isBlank(vo.getAnimalChinaName())) {
                sql.append(" and animalChinaName like  concat('%',:animalChinaName ,'%') ");
                params.put("animalChinaName", vo.getAnimalChinaName());
            }
            if (!StringUtils.isBlank(vo.getAnimalSex())) {
                sql.append(" and animalSex = :animalSex ");
                params.put("animalSex", vo.getAnimalSex());
            }
//            if (vo.getAnimalBridthMon() != null) {
//                sql.append(" and animalBridthMon = :animalBridthMon ");
//                params.put("animalBridthMon", vo.getAnimalBridthMon());
//            }
            // 生日区间，年月，逗号分隔，如 01-01,01-12
            if (!StringUtils.isBlank(vo.getStartTime())) {
                sql.append(" and CONCAT(case when animal_bridth_mon<10 then concat('0',animal_bridth_mon) else animal_bridth_mon end ," +
                        "case when animal_bridth_day<10 then concat('0',animal_bridth_day) else animal_bridth_day end ) > :startTime");
                params.put("startTime", vo.getStartTime());
            }
            if (!StringUtils.isBlank(vo.getEndTime())) {
                sql.append(" and CONCAT(case when animalBridthMon<10 then concat('0',animalBridthMon) else animalBridthMon end ," +
                        "case when animalBridthDay<10 then concat('0',animalBridthDay) else animalBridthDay end ) < :endTime");
                params.put("endTime", vo.getEndTime());
            }

            if (!StringUtils.isBlank(vo.getAnimalXingge())) {
                sql.append(" and animalXingge like  concat('%',:animalXingge ,'%') ");
                params.put("animalXingge", vo.getAnimalXingge());
            }
            if (!StringUtils.isBlank(vo.getAnimalZhongzu())) {
                sql.append(" and animalZhongzu  like  concat('%',:animalZhongzu ,'%') ");
                params.put("animalZhongzu", vo.getAnimalZhongzu());
            }
            if (!StringUtils.isBlank(vo.getAnimalLove())) {
                sql.append(" and animalLove  like  concat('%',:animalLove ,'%')  ");
                params.put("animalLove", vo.getAnimalLove());
            }
        }

        return sql.toString();
    }

    @Override
    public Long findCountByCondition(AnimalInfo vo) {
        return animalInfoDao.count();
    }

    @Override
    public AnimalInfo findLastOrNextData(AnimalInfo vo, Integer pageType, Integer id, List<SortDto> sortList) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        String conditionSql = getSql(vo, params);
        String lastOrNext = pageType == 1 ? "-" : "+";

        String sortSql = getOrderBy(sortList);

        // 最后必须按生日排
        sortSql += "  animal_bridth_mon ASC, animal_bridth_day ASC ";

        sql.append("  select * from (SELECT @rownum2\\:=@rownum2+1 AS rownum2, A.* FROM (SELECT @rownum2\\:=0) r, t_animal_info A where 1=1 " +
                conditionSql + " ORDER BY "+sortSql+" ) T where rownum2=((select rownum from (SELECT @rownum\\:=@rownum+1 AS rownum, A.* FROM " +
                "(SELECT @rownum\\:=0) r, t_animal_info A  where 1=1 " + conditionSql + " ORDER BY "+sortSql+" ) T where id= :id) "+lastOrNext+" 1)");
        params.put("id", id);

        Query namedQuery = entityManager.createNativeQuery(sql.toString(), AnimalInfo.class);
        SqlUtil.setParams(namedQuery, params);
        List<AnimalInfo> resultList = namedQuery.getResultList();
        if (resultList == null || resultList.size() == 0) {
            return null;
        }
        return resultList.get(0);
    }
}
