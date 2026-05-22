package cn.iocoder.yudao.module.zc.service.processnode;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.zc.controller.admin.processnode.vo.*;
import cn.iocoder.yudao.module.zc.dal.dataobject.processnode.ZcProcessNodeDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.zc.dal.mysql.processnode.ZcProcessNodeMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.diffList;
import static cn.iocoder.yudao.module.zc.enums.ErrorCodeConstants.*;

/**
 * 工序节点配置 Service 实现类
 *
 * @author 01Coder
 */
@Service
@Validated
public class ZcProcessNodeServiceImpl implements ZcProcessNodeService {

    @Resource
    private ZcProcessNodeMapper processNodeMapper;

    @Override
    public Long createProcessNode(ZcProcessNodeSaveReqVO createReqVO) {
        // 插入
        ZcProcessNodeDO processNode = BeanUtils.toBean(createReqVO, ZcProcessNodeDO.class);
        processNodeMapper.insert(processNode);

        // 返回
        return processNode.getId();
    }

    @Override
    public void updateProcessNode(ZcProcessNodeSaveReqVO updateReqVO) {
        // 校验存在
        validateProcessNodeExists(updateReqVO.getId());
        // 更新
        ZcProcessNodeDO updateObj = BeanUtils.toBean(updateReqVO, ZcProcessNodeDO.class);
        processNodeMapper.updateById(updateObj);
    }

    @Override
    public void deleteProcessNode(Long id) {
        // 校验存在
        validateProcessNodeExists(id);
        // 删除
        processNodeMapper.deleteById(id);
    }

    @Override
        public void deleteProcessNodeListByIds(List<Long> ids) {
        // 删除
        processNodeMapper.deleteByIds(ids);
        }


    private void validateProcessNodeExists(Long id) {
        if (processNodeMapper.selectById(id) == null) {
            throw exception(PROCESS_NODE_NOT_EXISTS);
        }
    }

    @Override
    public ZcProcessNodeDO getProcessNode(Long id) {
        return processNodeMapper.selectById(id);
    }

    @Override
    public PageResult<ZcProcessNodeDO> getProcessNodePage(ZcProcessNodePageReqVO pageReqVO) {
        return processNodeMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ZcProcessNodeDO> getProcessNodeList(ZcProcessNodeListReqVO listReqVO) {
        return processNodeMapper.selectList(listReqVO);
    }

}