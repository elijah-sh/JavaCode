package com.fssc.file;

import com.common.OddLines;
import com.util.StringPattenUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadClaim {
    public final static String fileClmTempPath = "/Users/xxx/src/main/webapp/WEB-INF/view/clms/template/";

    public static void main(String[] args) {
        ReadClaim read = new ReadClaim();
        // 匹配所有
        read.getClaimPageFilesEntityName();
    }


    // 按照模版
    public void getClaimTempFilesName1() {


        Map<String, Map<String, List<Map<String, String>>>> tempMap = new HashMap<>();

        // 逐模版读取数据
        Path start = Paths.get(fileClmTempPath);
        int maxDepth = 5;
        //  Files.walk()方法用于遍历子文件(包括文件夹)。
        try (Stream<Path> stream = Files.walk(start, maxDepth)) {
            String joined = stream
                    .map(String::valueOf)
                    .filter(path -> path.endsWith(".html"))
                    .sorted()
                    .collect(Collectors.joining(";"));
            String[] paths = joined.split(";");
            // 编辑模版文件
            for (String p : paths) {

                String[] tempName = p.split("/view/");
                // 排除demo
                if ("clms/template/demo_template.html".equals(tempName[1])) {
                    continue;
                }
                System.out.println("========================= TMPL ================================");
                System.out.println(tempName[1]);

                 // 模版维度
                List<String> lines = Files.readAllLines(Paths.get(p));
                // 行信息
                List<String> moduleLines = new ArrayList<>();

                // 模版中 <@cmfClmClaimRecInvLineList/>
                List<String> macroList = new ArrayList<>();
                // 行路径
                List<String> modulePaths = new ArrayList<>();

                // 循环模版内容
                for (int i = 0; i < lines.size(); i++) {
                    // 获取@标签
                    if (lines.get(i).contains("<@") && lines.get(i).contains("/>")
                            && !lines.get(i).contains(" ")
                            && !lines.get(i).contains(";")
                            && !lines.get(i).contains("\"")) {
                        // <@customer_innerTrade/>
                        String[] ats = lines.get(i).split("@");
                        String at = ats[1].substring(0, ats[1].length()-2);
                        macroList.add(at);
                    }

                    // 在一行中 <#include "../module/cmf_clm_invoice_summary_line.html"/>
                    if (lines.get(i).contains("<#include") && lines.get(i).contains("/>")) {
                        // 解析数据
                        String[] s = lines.get(i).split("\"");
                        String pa = s[1].substring(0, 3);
                        if ("../".equals(pa)) {
                            // 拼接文件目录 引入行或者头文件路径
                            String linePath = fileClmTempPath.substring(0, fileClmTempPath.length() - 9) + s[1].substring(3, s[1].length());
                            modulePaths.add(linePath);
                        }
                    }
                }

                // 整合模版数据
                for (String path :  modulePaths) {
                    // 匹配成功之后将行内容拼接到模版中
                    System.out.println(Paths.get(path));
                    List<String> line = Files.readAllLines(Paths.get(path));

                    // 读取之后批量带有@ 内容
                    //<#macro cmfClmClaimPayFlow>
                    String macro = "";
                    for (String l : line) {
                        if (l.contains("<#macro") && l.contains(">")) {
                            // 截取出去  匹配模版内容
                            macro = l.split("#macro ")[1].substring(0, l.split("#macro ")[1].length()-1);
                        }
                    }
                    // 需要在模版行循环之后才行
                    if (macroList.contains(macro)) {
                        moduleLines.addAll(line);
                    }
                }

                // 整合模版内容
                lines.addAll(moduleLines);

                // 通过模版匹配数据
                getClaimFilesByText(lines);

                // LOV
                this.getClaimLOVByText(lines);
                //
                //// Map<String, List<Map<String, String>>> region = new HashMap<>();
                //
                //if (region != null) {
                //    tempMap.put(tempName[1], region);
                //}


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(tempMap.toString());
    }



    /**
     * 获取区域与定应的数据 参数模版+行数据
     */
    public static void getClaimFilesByText(List<String> lines) {

            List<String> goLines = new ArrayList<>();

            for (int i = 0; i < lines.size(); i++) {
                // 在一行中
                if (lines.get(i).contains("<@base_grid_html") && lines.get(i).contains("</@base_grid_html>")) {
                    //  System.out.println("1 " + lines.get(i));
                    goLines.add(lines.get(i));
                }
                //
                // 在两行中
                if (lines.get(i).contains("<@base_grid_html") && !lines.get(i).contains("</@base_grid_html>") && lines.get(i+1).contains("</@base_grid_html>")) {
                    // System.out.println("2 " + lines.get(i) + lines.get(i+1));
                    goLines.add( lines.get(i) + lines.get(i+1));
                }

                // 在三行中
                if (lines.get(i).contains("<@base_grid_html")
                        && !lines.get(i).contains("</@base_grid_html>")
                        && !lines.get(i+1).contains("</@base_grid_html>") && lines.get(i+2).contains("</@base_grid_html>")) {
                    // System.out.println("3 " + lines.get(i)  + lines.get(i+1) +  lines.get(i+2));
                    goLines.add( lines.get(i) + lines.get(i+1) +  lines.get(i+2));
                }
            }


            // 读取“base_grid_html”关键词，把前端组件标识和后台数据库字段对应起来
            for (String s : goLines) {
                if (s.contains("id=") && s.contains("${(hashMap.")) {
                    String[] aaa = s.split("hashMap.");
                    String[] aaaa = aaa[1].split(".alias");
                   // System.out.println(aaaa[0]);

                    //String s1 = s.substring(21, s.length());
                    String[] aa = s.split("\"");
                    System.out.println(aa[1]);
                    // 匹配两个字符是否相等
                    if (!aaaa[0].equals(aa[1])) {
                        System.out.println(s);

                    }
                }
            }
    }


    /**
     * 获取区域与定应的数据 LOV
     */
    public  Map<String, List<Map<String, String>>> getClaimLOVByTex1(List<String> lines) {

        Map<String, List<Map<String, String>>> regionMap = new HashMap<>();

        for (int i = 0; i < lines.size(); i++) {
            // 找到关键字 kendoLov
            if (lines.get(i).contains("kendoLov(")) {

                // 匹配数据  textField valueField
                String valueField = "v";
                String textField = "t";
                String fieldName =  "f";
                String region =  "";

                int startIfLine = 1;
                int endIfLine = 0;
                Boolean startIfFlag = true;
                Boolean endIfFlag = true;

                Boolean startDataFlag = true;
                Boolean endTextFlag = true;
                Boolean endValueFlag = true;

                // 向下匹配的if开始与结束标签  结束标签的行号应该在开始的之前 或者开始标签不存在
                Boolean downIfStartFlag = true;
                Boolean downIfEndFlag = true;
                int downIfStartLine = 1;
                int downIfEndFLine = 0;

                // 向上匹配的if开始与结束标签  结束标签的行号应该在开始的之后 或者结束标签不存在
                Boolean upIfStartFlag = true;
                Boolean upIfEndFlag = true;
                int upIfStartLine = 1;
                int upIfEndLine = 0;

                for (int j = 1; j < 300; j++) {
                    if (i+j < lines.size()) {

                        // 要在匹配到   </#if> 前 不能匹配到  <#if   向下匹配   </#if> 要在 <#if 前
                        if (lines.get(i+j).contains("</#if>") && downIfEndFlag) {
                            downIfStartLine = i+j;
                            downIfEndFlag = false;
                        }

                        if (lines.get(i+j).contains("<#if") && downIfStartFlag) {
                            downIfStartLine =i - j;
                            downIfStartFlag = false;
                        }

                        if (lines.get(i+j).contains("textField") && lines.get(i+j).contains(":")  && lines.get(i+j).contains(",") && endTextFlag) {
                            String[] tf = lines.get(i+j).split("textField");
                            String[] tff = tf[1].split(",");
                            if (tff.length > 0) {
                                // textField: 'verInvoice',  去空格 前2 后1
                                String t = tff[0].replace(" ", "");
                                textField = t.substring(2, t.length()-1);
                                endTextFlag = false;
                            }
                        }

                        if (lines.get(i+j).contains("valueField") && lines.get(i+j).contains(":")  && lines.get(i+j).contains(",") && endValueFlag) {
                            String[] vf = lines.get(i+j).split("valueField");
                            String[] vff = vf[1].split(",");
                            if (vff.length > 0) {
                                // textField: 'verInvoice',  去空格 前2 后1
                                String v = vff[0].replace(" ", "");
                                valueField = v.substring(2, v.length()-1);
                                endValueFlag = false;
                            }
                        }

                    }

                    // 向上匹配 找到IF标签 内容的             <#if (hashMap.cmfClmClaimRecInvLine.customerNumber.displayFlag)!true>
                    if (i - j > 0) {
                        if (lines.get(i - j).contains("<#if") && upIfStartFlag) {
                            upIfStartLine =i - j;
                            upIfStartFlag = false;
                        }
                        if (lines.get(i - j).contains("</#if>") && upIfEndFlag) {
                            upIfEndLine =i - j;
                            upIfEndFlag = false;
                        }
                        if (lines.get(i - j).contains("<#if") && lines.get(i - j).contains("hashMap") && lines.get(i - j).contains("displayFlag") && startDataFlag) {
                            String[] ifh = lines.get(i - j).split("hashMap.");
                            String[] df = ifh[1].split(".displayFlag");
                            String[] data = df[0].split("\\.");
                            if (data.length > 1) {
                                // 字段
                                fieldName = data[1];
                                // 区域
                                region = data[0];
                                startDataFlag = false;
                            }
                        }

                    }
                }

                // 结束位置的</if 行号 小与 <if
                if ((downIfEndFLine != 0 && downIfStartLine == 1
                        && upIfEndLine != 0 && upIfStartLine == 1)
                        || (downIfStartLine > downIfEndFLine &&  upIfEndLine > upIfStartLine)) {
                    System.out.println("????=======================");

                }

                if (textField.equals(fieldName) || valueField.equals(fieldName)) {
                    if (!valueField.equals(fieldName) && !"v".equals(valueField)) {

                        Map<String, String> map = new HashMap<>();
                        map.put(fieldName, valueField);
                        List<Map<String, String>> list = new ArrayList<>();

                        if (regionMap.get(region) != null) {
                            list = regionMap.get(region);
                        }
                        list.add(map);

                        regionMap.put(region, list);
                        System.out.println(region + "." +fieldName + " ---> " + valueField);
                    }
                }
            }
        }

        return regionMap;

    }



    /**
     *  获取区域与定应的数据 LOV
     * @param lines 模版数据行
     * @return map
     */
    private  Map<String, Map<String, String>> getClaimLOVByText(List<String> lines) {

        Map<String, Map<String, String>> regionMap = new HashMap<>();

        for (int i = 0; i < lines.size(); i++) {

            // 模版数据去除 注释
            Boolean singleLineComment = StringPattenUtil.isSingleLineComment(lines.get(i));
            if (singleLineComment) {
                // 获取注释前的数据  也可能是空字符串
                lines.set(i, lines.get(i).split("//")[0]);
            }
            Boolean multiLineComment = StringPattenUtil.isMultiLineComment(lines.get(i));
            if (multiLineComment) {
                lines.set(i, "");
                continue;
            }



            // 匹配注释

            // "\/\/[^\n]*" ^/\\*.*?\\*/$
            //String pattern = "^/\\\\*.*?\\\\*/$";
            String pattern =  "^\\\\+(\\.\\d+)?hn";
             // 创建 Pattern 对象
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(lines.get(i));

            if (m.find()) {
                System.out.println("Found value: " + lines.get(i) );
            }

            if (lines.get(i).contains("//")) {
                String[] li = lines.get(i).split("//");

            }
            // 找到关键字 kendoLov
            if (lines.get(i).contains("kendoLov(")) {




                // input.kendoLov({
                //                                        code: "CMF_FIN_EXPENDITURE_TYPE_LIKE",

                //   .kendoLov($.extend(${lovProvider.getLov(base.contextPath, base.locale,"CMF_FIN_ASSET_KEYS")}, {

                String code = "code";
                for (int j = 0; j < 5; j++) {
                    if (i+j < lines.size()) {
                        // 要在匹配到  LOV code
                        String lineStr = lines.get(i+j).replace(" ", "");
                        if (lineStr.contains("getLov") && lineStr.contains("base.locale") ) {
                            String[] lc = lineStr.split("base.locale");
                            String[] lcv = lineStr.split("\"");
                            code = lcv[1];
                            System.out.println("LOV:" + code);
                        }
                        if (lineStr.contains("code:") && lineStr.contains("\",")) {
                            lineStr = lineStr.replace("code:", "").replace(",", "");
                            code = lineStr.substring(1, lineStr.length()-1);
                            System.out.println("LOV:" + code);
                        }
                    }
                }

                // 匹配数据  textField valueField
                String valueField = "v";
                String textField = "t";
                String fieldName =  "f";
                String region =  "";

                int startIfLine = 1;
                int endIfLine = 0;
                Boolean startIfFlag = true;
                Boolean endIfFlag = true;

                Boolean startDataFlag = true;
                Boolean endTextFlag = true;
                Boolean endValueFlag = true;
                // 两个行号不能相差超过 5 行
                int textLine = 0;
                int valueLine = 10;

                for (int j = 1; j < 300; j++) {
                    if (i+j < lines.size()) {

                        // 要在匹配到   </#if> 前 不能匹配到  <#if
                        if (lines.get(i+j).contains("</#if>") && endIfFlag) {
                            endIfLine = i+j;
                            endIfFlag = false;
                        }

                        if (lines.get(i+j).contains("textField") && lines.get(i+j).contains(":")  && lines.get(i+j).contains(",") && endTextFlag) {
                            String[] tf = lines.get(i+j).split("textField");
                            String[] tff = tf[1].split(",");
                            if (tff.length > 0) {
                                // textField: 'verInvoice',  去空格 前2 后1
                                String t = tff[0].replace(" ", "");
                                textField = t.substring(2, t.length()-1);
                                textLine = i+j;
                                endTextFlag = false;
                            }
                        }

                        if (lines.get(i+j).contains("valueField") && lines.get(i+j).contains(":")  && lines.get(i+j).contains(",") && endValueFlag) {
                            String[] vf = lines.get(i+j).split("valueField");
                            String[] vff = vf[1].split(",");
                            if (vff.length > 0) {
                                // textField: 'verInvoice',  去空格 前2 后1
                                String v = vff[0].replace(" ", "");
                                valueField = v.substring(2, v.length()-1);
                                valueLine = i+j;
                                endValueFlag = false;
                            }
                        }

                    }

                    // 向上匹配 找到IF标签 内容的             <#if (hashMap.cmfClmClaimRecInvLine.customerNumber.displayFlag)!true>
                    if (i - j > 0) {
                        if (lines.get(i - j).contains("<#if") && startIfFlag) {
                            startIfLine =i - j;
                            startIfFlag = false;
                        }
                        String lineStr = lines.get(i-j).replace(" ", "");

                        if (lineStr.length() > 13) {
                            if (lineStr.substring(0,10).contains("function")) {
                                System.out.println("function ---> " + lines.get(i - j));

                            }
                        }

                        if (lines.get(i - j).contains("<#if") && lines.get(i - j).contains("hashMap") && lines.get(i - j).contains("displayFlag") && startDataFlag) {
                            String[] ifh = lines.get(i - j).split("hashMap.");
                            String[] df = ifh[1].split(".displayFlag");
                            String[] data = df[0].split("\\.");
                            if (data.length > 1) {
                                // 字段
                                fieldName = data[1];
                                // 区域
                                region = data[0];
                                startDataFlag = false;
                            }
                        }

                    }
                }

                int lineL = textLine - valueLine;
                if (lineL < 0) {
                    lineL = - lineL;
                }
                if (endIfLine > startIfLine && ( textField.equals(fieldName) || valueField.equals(fieldName)) && lineL < 10) {
                    if (!valueField.equals(fieldName) && !"v".equals(valueField) && !"basicInfornation".equals(region)) {

                        Map<String, String> map = new HashMap<>();
                        if (regionMap.get(region) != null) {
                            map = regionMap.get(region);
                        }
                        map.put(fieldName, valueField);
                        regionMap.put(region, map);
                        System.out.println(region + "." +fieldName + " ---> " + valueField);
                    }
                }
            }
        }

        return regionMap;
    }

    public Map<String, Map<String, Map<String, String>>> getClaimPageFilesEntityName() {



        Map<String, Map<String, Map<String, String>>> tempMap = new HashMap<>();
        // 逐模版读取数据
        Path start = Paths.get(fileClmTempPath);
        int maxDepth = 5;
        //  Files.walk()方法用于遍历子文件(包括文件夹)。
        String filePaths = "";
        try {
            Stream<Path> stream = Files.walk(start, maxDepth);
            filePaths = stream
                    .map(String::valueOf)
                    .filter(path -> path.endsWith(".html"))
                    .sorted()
                    .collect(Collectors.joining(";"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] paths = filePaths.split(";");

        // 编辑模版文件
        for (String p : paths) {
            // 通过分割
            String[] tempName = p.split("/view/");
            if (tempName.length < 1) {
                continue;
            }
            // 排除demo
            if ("clms/template/demo_template.html".equals(tempName[1]) || "clms/template/cmf_clm_overview_template.html".equals(tempName[1])) {
                continue;
            }

            if (!"clms/template/getClaimTempFilesName1.html".equals(tempName[1])) {
                System.out.println("");
            }
            System.out.println("======================= TMPL ================================");
            System.out.println(tempName[1]);

            // 模版维度
            List<String> lines = new ArrayList<>();

            try {
                lines = Files.readAllLines(Paths.get(p));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 行信息
            List<String> moduleLines = new ArrayList<>();

            // 模版中 <@cmfClmClaimRecInvLineList/>
            List<String> macroList = new ArrayList<>();
            // 行路径
            List<String> modulePaths = new ArrayList<>();

            // 循环模版内容
            for (int i = 0; i < lines.size(); i++) {
                // 获取@标签
                if (lines.get(i).contains("<@") && lines.get(i).contains("/>")
                        && !lines.get(i).contains(" ")
                        && !lines.get(i).contains(";")
                        && !lines.get(i).contains("\"")) {
                    // <@customer_innerTrade/>
                    String[] ats = lines.get(i).split("@");
                    String at = ats[1].substring(0, ats[1].length() - 2);
                    macroList.add(at);
                }

                // 在一行中 <#include "../module/cmf_clm_invoice_summary_line.html"/>
                if (lines.get(i).contains("<#include") && lines.get(i).contains("/>")) {
                    // 解析数据
                    String[] s = lines.get(i).split("\"");
                    String pa = s[1].substring(0, 3);
                    if ("../".equals(pa)) {
                        // 拼接文件目录 引入行或者头文件路径
                        String linePath = fileClmTempPath.substring(0, fileClmTempPath.length() - 9) + s[1].substring(3, s[1].length());
                        modulePaths.add(linePath);
                    }
                }
            }

            // 整合模版数据
            for (String path : modulePaths) {
                // 匹配成功之后将行内容拼接到模版中
                List<String> line = new ArrayList<>();
                try {
                    line = Files.readAllLines(Paths.get(path));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // 读取之后批量带有@ 内容
                //<#macro cmfClmClaimPayFlow>
                String macro = "";
                for (String l : line) {
                    if (l.contains("<#macro") && l.contains(">")) {
                        // 截取出去  匹配模版内容
                        macro = l.split("#macro ")[1].substring(0, l.split("#macro ")[1].length() - 1);
                    }
                }
                // 需要在模版行循环之后才行
                if (macroList.contains(macro)) {
                    moduleLines.addAll(line);
                }
            }

            // 整合模版内容
            lines.addAll(moduleLines);

            // LOV
            Map<String, Map<String, String>> region = this.getClaimLOVByText(lines);

            if (region != null) {
                tempMap.put(tempName[1], region);
            }
        }
        return tempMap;
    }


}
