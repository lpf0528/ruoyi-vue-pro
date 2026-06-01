package cn.iocoder.yudao.module.zc.service.workshopuser;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.workshopuser.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.workshopuser.ZcWorkshopUserDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.workshopuser.ZcWorkshopUserMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 车间员工 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcWorkshopUserServiceImpl implements ZcWorkshopUserService {

    @Resource
    private ZcWorkshopUserMapper workshopUserMapper;

    @Override
    public Long createWorkshopUser(ZcWorkshopUserSaveReqVO createReqVO) {
        // 插入
        ZcWorkshopUserDO workshopUser = BeanUtils.toBean(createReqVO, ZcWorkshopUserDO.class);
        workshopUserMapper.insert(workshopUser);

        // 返回
        return workshopUser.getId();
    }

    @Override
    public void updateWorkshopUser(ZcWorkshopUserSaveReqVO updateReqVO) {
        // 校验存在
        validateWorkshopUserExists(updateReqVO.getId());
        // 更新
        ZcWorkshopUserDO updateObj = BeanUtils.toBean(updateReqVO, ZcWorkshopUserDO.class);
        workshopUserMapper.updateById(updateObj);
    }

    @Override
    public void deleteWorkshopUser(Long id) {
        // 校验存在
        validateWorkshopUserExists(id);
        // 删除
        workshopUserMapper.deleteById(id);
    }

    @Override
        public void deleteWorkshopUserListByIds(List<Long> ids) {
        // 删除
        workshopUserMapper.deleteByIds(ids);
        }


    private void validateWorkshopUserExists(Long id) {
        if (workshopUserMapper.selectById(id) == null) {
            throw exception(WORKSHOP_USER_NOT_EXISTS);
        }
    }

    @Override
    public ZcWorkshopUserDO getWorkshopUser(Long id) {
        return workshopUserMapper.selectById(id);
    }

    @Override
    public PageResult<ZcWorkshopUserDO> getWorkshopUserPage(ZcWorkshopUserPageReqVO pageReqVO) {
        return workshopUserMapper.selectPage(pageReqVO);
    }

}