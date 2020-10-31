package cn.zkz.animal.service.impl;

import cn.zkz.animal.service.IAnimalInfoService;
import cn.zkz.animal.service.IOpertateAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@Transactional
public class OperateAnimalService implements IOpertateAnimalService {

    @Autowired
    private EntityManager entityManager;

}
