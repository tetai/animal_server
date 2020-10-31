package cn.zkz.animal.service.impl;

import cn.zkz.animal.dao.IKeyValueDao;
import cn.zkz.animal.model.po.KeyValue;
import cn.zkz.animal.service.IKeyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
