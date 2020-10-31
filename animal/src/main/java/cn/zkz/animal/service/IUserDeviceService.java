package cn.zkz.animal.service;

import cn.zkz.animal.model.po.UserDevice;

import java.util.List;

public interface IUserDeviceService {


    List<UserDevice> findAll(String openId);

    void save(UserDevice vo);
}
