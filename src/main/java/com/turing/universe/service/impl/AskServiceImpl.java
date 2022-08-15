package com.turing.universe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turing.universe.entity.Ask;
import com.turing.universe.mapper.AskMapper;
import com.turing.universe.service.AskService;
import org.springframework.stereotype.Service;

@Service
public class AskServiceImpl extends ServiceImpl<AskMapper, Ask> implements AskService {

    @Override
    public Ask selectOneByContent(String word) {
        QueryWrapper<Ask> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(Ask::getContent, word);
        return this.getOne(queryWrapper);
    }
}
