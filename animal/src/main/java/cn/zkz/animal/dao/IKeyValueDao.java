package cn.zkz.animal.dao;

import cn.zkz.animal.po.KeyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IKeyValueDao extends JpaRepository<KeyValue, Integer> {

    KeyValue findByKey(String id);

}
