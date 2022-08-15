package com.turing.universe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.turing.universe.entity.Ask;

public interface AskService extends IService<Ask> {
    /**
     * 根据字查询
     *
     * @param word
     * @return
     */
    Ask selectOneByContent(String word);
}
