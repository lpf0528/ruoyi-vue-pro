package cn.iocoder.yudao.module.quiz.dal.dataobject.projectcategory;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 项目分类 DO
 *
 * @author 01Coder
 */
@TableName("quiz_project_category")
@KeySequence("quiz_project_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuizProjectCategoryDO extends BaseDO {

    /**
     * 项目分类编号
     */
    @TableId
    private Long id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 图标地址
     */
    private String picUrl;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private Integer sort;


}