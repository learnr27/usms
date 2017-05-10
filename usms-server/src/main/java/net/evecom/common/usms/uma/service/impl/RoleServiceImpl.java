/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.common.usms.uma.service.impl;

import net.evecom.common.usms.entity.RoleEntity;
import net.evecom.common.usms.entity.UserEntity;
import net.evecom.common.usms.uma.dao.RoleDao;
import net.evecom.common.usms.uma.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Pisces Lu
 * @version 1.0
 * @created 2017-5-8 18:29
 */
@Transactional
@Service
public class RoleServiceImpl  implements RoleService{

    /**
     * roleDao的注入
     */
    @Autowired
    private RoleDao roleDao;

    /**
     * 根据用户ID来查找角色列表
     * @return
     * @param userID
     */
    @Override
    public List<RoleEntity> findRolesByUserId(long userID) {
        return roleDao.findRolesByUserId(userID);
    }

    /**
     * 判断是否拥有该角色
     * @return
     * @param userID,roleName
     */
    @Override
    public boolean hasRole(long userID, String roleName) {
        return roleDao.hasRole(userID,roleName);
    }

    /**
     * 根据角色编码查询用户列表
     * @param roleName
     * @return
     */
    @Override
    public List<UserEntity> getUsersByRoleName(String roleName) {
        return roleDao.getUsersByRoleName(roleName);
    }

}
