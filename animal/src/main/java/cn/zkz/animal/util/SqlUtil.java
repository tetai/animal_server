package cn.zkz.animal.util;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Map;

public class SqlUtil {

    public static Query getQuery(EntityManager entityManager, String sql, Map<String, Object> params, Integer pageSize, Integer pageNum) {
        Query namedQuery = entityManager.createQuery(sql);
        if (pageSize!= null && pageNum != null && pageNum > 0) {
            namedQuery.setMaxResults(pageSize);
            namedQuery.setFirstResult((pageNum-1)*pageSize);
        }

        if (params != null && params.size() > 0) {
            for (String key : params.keySet()) {
                namedQuery.setParameter(key, params.get(key));
            }
        }

        return namedQuery;
    }
}
