package com.example.demo;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
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

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private LogService logService;
    @Autowired
    private LogMapper logMapper;
    private static final Set<String> APP_KEYS = new HashSet<>(Arrays.asList("platform.chat", "os.sys.chat"));
    private static final List<Integer> ASK_PARSETYPE = Arrays.asList(37, 38, 106);
    private static final List<Integer> WIKI_PARSETYPE = Arrays.asList(110);


    @Test
    public void contextLoads() {
        exportLogByDate();
//        System.out.println(logService.getById(1));
//        Set<String> logs = new HashSet<String>();
//        int count = 18000;
//        for (int i = 1; i < count; i++) {
//            System.out.println(i);
//            Log log = logService.getById(i);
//            if (null == log) {
//                continue;
//            }
//            String q = log.getAppkey();
//            if (StringUtils.isEmpty(q)) {
//                continue;
//            }
//            logs.add(q);
//            if (logs.size() == 100000) {
//                FileUtils.writeToTxt("E:\\log_1.txt", StringUtils.join(logs.toArray(), "\n"));
//                logs.clear();
//            }
//        }
//        System.out.println(logs);
//        List<String> l = new ArrayList<>();
//
//        l.add("2");
//        l.add("2");
//        l.add("3");
//        l.add("4");
//        l.add("4");
//        l.add("1");
//        l.add("1");
//        l.add("1");
//        l.add("1");
//        l.add("1");
//        Map<String, Integer> map = CollectionUtil.countMap(l);
//        Map<String, Integer> newMap = MapUtil.sortByValue(map, true);
//        System.out.println(newMap);
    }

    private void exportLogByDate() {
        String start = "2022-08-05";
        String end = "2022-08-05 07";
        Map<String, Integer> askMap = new HashMap<>();
        Map<String, Integer> wikiMap = new HashMap<>();
        List<Log> logs = logMapper.getLogsByDate(start, end);
        System.out.println(logs.size());
        for (Log log : logs) {
            if (log == null) continue;
            String q = log.getQuestion();
            if (StringUtils.isBlank(q)) continue;
            q = FileUtils.replaceStartEndBiaodian(q);
            if (StringUtils.isBlank(q)) continue;
            // 判断是否疑问句
            if (!FileUtils.isAsk(q)) continue;
            Integer parseType = log.getParsetype();
            if (parseType == 110) {
                if (q.contains("是谁")) {
                    q = q.substring(0, q.indexOf("是谁"));
                }
                System.out.println(q);
                Integer count = wikiMap.getOrDefault(q, 0);
                ++count;
                wikiMap.put(q, count);
            } else {
                Integer count = askMap.getOrDefault(q, 0);
                ++count;
                askMap.put(q, count);
            }
        }
        Map<String, Integer> newWikiMap = MapUtil.sortByValue(wikiMap, true);
        Map<String, Integer> newAskMap = MapUtil.sortByValue(askMap, true);
        newWikiMap.forEach((k, v) -> {
            FileUtils.writeToTxt("E:\\wiki_freq.txt", k, v);
        });
        newAskMap.forEach((k, v) -> {
            FileUtils.writeToTxt("E:\\ask_freq.txt", k, v);
        });
    }

    private void exportLog() {
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
    }

}
