package cn.zkz.animal.service.impl;

import cn.zkz.animal.dao.IUserDeviceDao;
import cn.zkz.animal.model.po.UserDevice;
import cn.zkz.animal.service.IAnimalInfoService;
import cn.zkz.animal.service.IUserDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserDeviceService implements IUserDeviceService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private IUserDeviceDao userDeviceDao;

    @Override
    public List<UserDevice> findAll(String openId) {
        return userDeviceDao.findByActiveFlagAndUserId(1, openId);
    }

    @Override
    public void save(UserDevice vo) {
        vo.setUpdateTime(new Date());
        if (vo.getActiveFlag() == null || vo.getActiveFlag() == 0)
            vo.setActiveFlag(1);
        if (vo.getId() == null || vo.getId() == 0) {
            vo.setCreateTime(new Date());
        }

        userDeviceDao.save(vo);
    }
}
