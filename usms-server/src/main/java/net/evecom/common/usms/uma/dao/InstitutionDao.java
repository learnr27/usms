/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.dao;

import net.evecom.common.usms.entity.InstitutionEntity;
import net.evecom.common.usms.entity.UserEntity;

import java.util.List;

/**
 * 描述
 *
 * @author Wash Wang
 * @version 1.0
 * @created 2017/5/8 14:25
 */
public interface InstitutionDao {

    /**
     * 根据登陆名获取机构列表
     *
     * @param loginName
     * @return
     */
    List<InstitutionEntity> findInstByLoginName(String loginName);

    /**
     * 根据名称获取组织机构信息
     *
     * @param name
     * @return
     */
    InstitutionEntity findByName(String name);

    /**
     * 根据组织机构编码构查询用户列表
     *
     * @param instName
     * @return
     */
    List<UserEntity> getUsersByInstName(String instName);

}
