package com.turing.universe.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "ask")
public class Ask {
    private String content;
}
