package com.turing.universe.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ChenOT
 * @date 2020-02-11
 * @see
 * @since
 */
@Data
@TableName("platform_cloud_log_202207")
public class Log {
    private Long id;
    private String question;
}
