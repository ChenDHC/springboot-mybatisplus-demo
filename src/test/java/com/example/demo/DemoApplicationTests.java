package com.example.demo;

import cn.hutool.core.map.MapUtil;
import com.turing.universe.DemoApplication;
import com.turing.universe.entity.Ask;
import com.turing.universe.entity.Log;
import com.turing.universe.mapper.LogMapper;
import com.turing.universe.service.AskService;
import com.turing.universe.service.LogService;
import com.turing.universe.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private LogService logService;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private AskService askService;
    private static final Set<String> APP_KEYS = new HashSet<>(Arrays.asList("platform.chat", "os.sys.chat"));
    private static final List<Integer> ASK_PARSETYPE = Arrays.asList(37, 38, 106);
    private static final List<Integer> WIKI_PARSETYPE = Arrays.asList(110);


    @Test
    public void contextLoads() {
        exportLogById();
    }

    private void exportLogById() {
        String date = "2022-06-%s";
        for (int i = 1; i < 30; i++) {
            String today = "";
            String nextDay = "";
            int next = i + 1;
            if (i < 10) {
                today = "0" + i;
            } else {
                today = "" + i;
            }
            if (next < 10) {
                nextDay = "0" + next;
            } else {
                nextDay = "" + next;
            }
            String start = String.format(date, today);
            String end = String.format(date, nextDay);
            System.out.println(start + "\t" + end);
            List<Log> logs = logMapper.getLogsByDate(start, end);
            for (Log log : logs) {
                String q = log.getQuestion();
                if (StringUtils.isBlank(q)) {
                    continue;
                }
                q = StringUtils.strip(q);
                q = FileUtils.replaceStartEndBiaodian(q);
                q = StringUtils.strip(q);
                if (StringUtils.isBlank(q)) {
                    continue;
                }
                // 写入文件
                FileUtils.writeToTxt("E:\\log_6.txt", q);
            }
        }
    }

    private void exportLogByDate() {
        String date = "2022-08-27";
        String start = date;
        String end = date + " 08";
        Map<String, Integer> askMap = new HashMap<>();
        Map<String, Integer> wikiMap = new HashMap<>();
        List<Log> logs = logMapper.getLogsByDate(start, end);
        System.out.println(logs.size());
        int index = 0;
        for (Log log : logs) {
            ++index;
            if (index % 1000 == 0) {
                System.out.println(index);
            }
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
        processWikiAsk(newAskMap, String.format("E:\\问答_%s.txt", date.replace("-", "")));
        processWikiAsk(newWikiMap, String.format("E:\\百科_%s.txt", date.replace("-", "")));
    }

    private void processWikiAsk(Map<String, Integer> map, String filePath) {
        List<String> contents = new ArrayList<>(200);
        for (Map.Entry<String, Integer> item : map.entrySet()) {
            String q = StringUtils.strip(item.getKey());
            Ask ask = askService.selectOneByContent(q);
            if (null != ask) {
                continue;
            }
            contents.add(q);
            if (contents.size() == 200) {
                break;
            }
        }
        // 入库
        for (String content : contents) {
            Ask ask = new Ask();
            ask.setContent(content);
            try {
                askService.save(ask);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 写入文件
        FileUtils.writeToTxt(filePath, StringUtils.join(contents.toArray(), "\n"));
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
