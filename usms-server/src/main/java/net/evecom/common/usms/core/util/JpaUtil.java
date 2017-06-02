/*
 * Copyright (c) 2005, 2016, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.core.util;

import java.util.List;

import javax.persistence.EntityManager;

/**
 * Jpa工具类
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/05/25 10:45:52
 */
public class JpaUtil {

    private JpaUtil() {
    }

    /**
     * 获得单个结果
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getSingleResult(List<T> list) {
        switch (list.size()) {
            case 0:
                return null;
            case 1:
                return list.get(0);
            default:
                throw new IllegalStateException("Expected single result, got " + list.size());
        }
    }

    /**
     * 更新或插入操作
     *
     * @param entityManager
     * @param entity
     * @param <T>
     * @return
     */
    public static <T> T saveOrUpdate(EntityManager entityManager, T entity) {
        T tmp = entityManager.merge(entity);
        entityManager.flush();
        return tmp;
    }
}
