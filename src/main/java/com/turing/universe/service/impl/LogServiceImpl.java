package com.turing.universe.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turing.universe.entity.Book;
import com.turing.universe.entity.Log;
import com.turing.universe.mapper.BookMapper;
import com.turing.universe.mapper.LogMapper;
import com.turing.universe.service.BookService;
import com.turing.universe.service.LogService;
import org.springframework.stereotype.Service;

/**
 * @author ChenOT
 * @date 2020-02-11
 * @see
 * @since
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {
}
