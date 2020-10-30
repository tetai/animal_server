package cn.zkz.animal.service.impl;

import cn.zkz.animal.dao.IAnimalInfoDao;
import cn.zkz.animal.dao.IKeyValueDao;
import cn.zkz.animal.po.AnimalInfo;
import cn.zkz.animal.po.KeyValue;
import cn.zkz.animal.service.IAnimalInfoService;
import cn.zkz.animal.service.IKeyValueService;
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
public class KeyValueService implements IKeyValueService {


    @Autowired
    private IKeyValueDao keyValueDao;

    @Override
    public KeyValue getById(String key) {
        return keyValueDao.findByKey(key);
    }


}
