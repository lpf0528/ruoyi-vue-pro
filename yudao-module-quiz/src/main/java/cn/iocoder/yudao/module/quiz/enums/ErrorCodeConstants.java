
package cn.iocoder.yudao.module.quiz.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;


public interface ErrorCodeConstants {

    ErrorCode PROJECT_CATEGORY_NOT_EXISTS = new ErrorCode(1_009_000_000, "项目分类不存在");
    // ========== 项目 TODO 补充编号 ==========
    ErrorCode PROJECT_NOT_EXISTS = new ErrorCode(1_009_000_001, "项目不存在");


}
