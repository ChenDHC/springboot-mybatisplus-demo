package com.example.demo;

import cn.hutool.core.io.FileUtil;
import com.turing.universe.DemoApplication;
import com.turing.universe.entity.Log;
import com.turing.universe.mapper.LogMapper;
import com.turing.universe.service.LogService;
import com.turing.universe.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private LogService logService;
    @Autowired
    private LogMapper logMapper;

    @Test
    public void contextLoads() {
//        System.out.println(logService.getById(1));
//        Set<String> logs = new HashSet<String>();
//        int count = 18000000;
//        for (int i = 1; i < count; i++) {
//            System.out.println(i);
//            Log log = logService.getById(i);
//            if (null == log) {
//                continue;
//            }
//            String q = log.getQuestion();
//            if (StringUtils.isEmpty(q)) {
//                continue;
//            }
//            logs.add(q);
//            if (logs.size() == 100000) {
//                FileUtils.writeToTxt("E:\\log_1.txt", StringUtils.join(logs.toArray(), "\n"));
//                logs.clear();
//            }
//        }
//        for (int i = 0; i < 180; i++) {
//            System.out.println(i);
//            Long start = i * 100000L;
//            Long end = (i + 1) * 100000L;
//            List<Log> logList = logMapper.getLogs(start, end);
//            if (CollectionUtils.isEmpty(logList)) {
//                continue;
//            }
//            Set<String> qs = new HashSet<>();
//            for (Log log : logList) {
//                if (log==null || StringUtils.isBlank(log.getQuestion())) {
//                    continue;
//                }
//                qs.add(log.getQuestion());
//            }
//            FileUtils.writeToTxt("E:\\log_2.txt", StringUtils.join(qs.toArray(), "\n"));
//        }
        List<String> lines = FileUtil.readUtf8Lines("E:\\log_2.txt");
        Set<String> logs = lines.stream().collect(Collectors.toSet());
        FileUtils.writeToTxt("E:\\log_3.txt", StringUtils.join(logs.toArray(), "\n"));
    }

}
