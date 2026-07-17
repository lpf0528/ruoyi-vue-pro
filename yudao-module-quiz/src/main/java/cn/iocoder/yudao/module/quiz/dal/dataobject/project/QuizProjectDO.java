package cn.iocoder.yudao.module.quiz.dal.dataobject.project;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目 DO
 *
 * @author 01Coder
 */
@TableName("quiz_project")
@KeySequence("quiz_project_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizProjectDO extends BaseDO {

    /**
     * 项目ID
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 封面图片地址
     */
    private String picUrl;
    /**
     * 简介
     */
    private String introduction;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 是否热门(小程序)
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean recommendHot;
    /**
     * 是否轮播图(小程序)
     *
     * 枚举 {@link TODO infra_boolean_string 对应的类}
     */
    private Boolean recommendBanner;


}