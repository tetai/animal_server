package cn.zkz.animal.dao;

import cn.zkz.animal.model.po.OperateAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOpertaAniamlDao extends JpaRepository<OperateAnimal, Integer> {


}
