package com.tuozhi.zhlw.admin.dao;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/02 23:16
 **/
public class BaseDaoImpl<T, Long extends Serializable> extends SimpleJpaRepository<T, Long>
        implements BaseDao<T, Long> {
    //用于操作数据库
    private final EntityManager em;

    //父类没有不带参数的构造方法，这里手动构造父类
    public BaseDaoImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.em = entityManager;
    }

    //通过EntityManager来完成查询
    @Override
    public List<Object[]> findListBySQL(String sql) {
        return em.createNativeQuery(sql).getResultList();
    }

    @Override
    public void saveEntity(Object entity) {
        em.persist(entity);
    }

    @Override
    public void update(Object entity) {
        em.merge(entity);
    }

    @Override
    public <T1> void delete(Class<T1> entityClass, Object entityid) {
        delete(entityClass, new Object[]{entityid});
    }

    @Override
    public <T1> void delete(Class<T1> entityClass, Object[] entityids) {
        for (Object id : entityids) {
            em.remove(em.getReference(entityClass, id));
        }
    }

    @Override
    public <T1> T1 find(Class<T1> entityClass, Object entityId) {
        return em.find(entityClass, entityId);
    }


}
