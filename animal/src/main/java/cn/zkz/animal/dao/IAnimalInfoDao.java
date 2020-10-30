package cn.zkz.animal.dao;

import cn.zkz.animal.po.AnimalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnimalInfoDao extends JpaRepository<AnimalInfo, Integer> {

    AnimalInfo findById(int id);

    AnimalInfo save(AnimalInfo user);
}
