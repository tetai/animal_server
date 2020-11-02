package cn.zkz.animal.dao;

import cn.zkz.animal.model.po.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDeviceDao extends JpaRepository<UserDevice, Integer> {


    List<UserDevice> findByActiveFlagAndUserId(int i, String openId);
}
