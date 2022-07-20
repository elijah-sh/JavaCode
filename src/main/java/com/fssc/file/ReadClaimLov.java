package com.fssc.file;

import com.util.StringPattenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadClaimLov {

    private static Logger logger = LoggerFactory.getLogger(ReadClaimLov.class);


    public final static String fileClmTempPath = "/Users/xxx/src/main/webapp/WEB-INF/view/xx/template/";

    public static void main(String[] args) {
        ReadClaimLov read = new ReadClaimLov();
        // 匹配所有
        read.getClaimPageFilesEntityName();
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
    private  Map<String, Map<String, String>> getClaimLOVByTextV1(List<String> lines) {

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


                Boolean upIfFlag = false;
                Boolean downIfFlag = false;


                for (int j = 1; j < 300; j++) {
                    if (i+j < lines.size()) {

                        // 要在匹配到   </#if> 前 不能匹配到  <#if
                        if (lines.get(i+j).contains("</#if>") && endIfFlag) {
                            endIfLine = i+j;
                            endIfFlag = false;
                        }

                        if (downIfFlag) {
                            continue;
                        }
                        // 如果匹配到了 if开始标签 就结束
                        if (lines.get(i+j).contains("<#if")
                                && !lines.get(i+j).contains("tooltips")
                                && lines.get(i+j).contains("hashMap")) {
                            downIfFlag = true;
                        }

                        // 要在匹配到   </#if> 前 不能匹配到  <#if
                        if (lines.get(i+j).contains("</#if>")
                                && !lines.get(i+j).contains(",")
                                && !lines.get(i+j).contains("+")
                                && !lines.get(i+j).contains("'")
                                && endIfFlag) {
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


                        // 如果匹配到了 if关闭标签 就结束
                        if (lines.get(i - j).contains("</#if>")
                                && !lines.get(i - j).contains(",")
                                && !lines.get(i - j).contains("+")
                                && !lines.get(i - j).contains("'")
                        ) {
                            upIfFlag = true;
                        }

                        if (lines.get(i - j).contains("<#if")
                                && !lines.get(i - j).contains("tooltips")
                                && lines.get(i - j).contains("hashMap")
                                && startIfFlag) {
                            startIfLine =i - j;
                            startIfFlag = false;
                        }

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

    /**
     *  获取区域与定应的数据 LOV
     *  TODO 待完善
     * @param lines 模版数据行
     * @return map
     */
    private  Map<String, Map<String, String>> getClaimLOVByText(List<String> lines) {

        Map<String, Map<String, String>> regionMap = new HashMap<>();

        for (int i = 0; i < lines.size(); i++) {

            // 找到关键字 kendoLov
            if (lines.get(i).contains("kendoLov(")) {
                // input.kendoLov({  code: "CMF_FIN_EXPENDITURE_TYPE_LIKE",
                //      $("#projectId").kendoLov($.extend(${lovProvider.getLov(base.contextPath, base.locale, "BUDGET_PRJ_TO_COST_CENTER")},{
                // 匹配快码内的
                String code = "code";
                for (int j = 0; j < 5; j++) {
                    if (i+j < lines.size()) {
                        // 要在匹配到  LOV code
                        String lineStr = lines.get(i+j).replace(" ", "");
                        if (lineStr.contains("getLov") && lineStr.contains("base.locale") ) {
                            String[] lc = lineStr.split("base.locale");
                            String[] lcv = lc[1].split("\"");
                            code = lcv[1];
                        }
                        if (lineStr.contains("code:") && lineStr.contains(",")) {
                            lineStr = lineStr.replace("code:", "").replace(",", "");
                            code = lineStr.substring(1, lineStr.length()-1);
                        }
                    }
                }
                String textField = "t", pageTextField = "p",  pageValueField = "v";

                if ("CMF_FIN_TAX_CODE".equals(code)) {
                    System.out.println("");
                }

                // 匹配数据  textField valueField
                String fieldName =  "f";
                String region =  "";

                int startIfLine = 1;
                int endIfLine = 0;
                Boolean startIfFlag = true;
                Boolean endIfFlag = true;

                Boolean startDataFlag = true;
                Boolean selectFlag = true;
                Boolean endTextFlag = true;

                // 获取的值
                String selectValue = "selectValue";
                String selectValues = "";
                String valueField = "v";


                Boolean upIfFlag = false, downIfFlag = false;


                Boolean endValueFlag = true;
                int valueLine = 10;
                // 两个行号不能相差超过 5 行
                int textLine = 0;



                for (int j = 1; j < 300; j++) {
                    if (i+j < lines.size()) {
                        if (downIfFlag) {
                            continue;
                        }
                        // 向下匹配到的行数据

                        String downLine = lines.get(i+j);
                        // 如果匹配到了 if开始标签 就结束
                        if (downLine.contains("<#if")
                                && !downLine.contains("tooltips")
                                && downLine.contains("hashMap")) {
                            downIfFlag = true;
                        }

                        // 要在匹配到   </#if> 前 不能匹配到  <#if
                        if (downLine.contains("</#if>")
                                && !downLine.contains(",")
                                && !downLine.contains("+")
                                && !downLine.contains("'")
                                && endIfFlag) {
                            endIfLine = i+j;
                            endIfFlag = false;
                        }

                        if (downLine.contains("textField") && downLine.contains(":")  && downLine.contains(",") && endTextFlag) {
                            String[] tf = downLine.split("textField");
                            String[] tff = tf[1].split(",");
                            if (tff.length > 0) {
                                // textField: 'verInvoice',  去空格 前2 后1
                                String t = tff[0].replace(" ", "");
                                pageTextField = t.substring(2, t.length()-1);
                                endTextFlag = false;
                            }
                        }


                        if (downLine.contains("valueField") && downLine.contains(":")  && downLine.contains(",") && endValueFlag) {
                            String[] vf = downLine.split("valueField");
                            String[] vff = vf[1].split(",");
                            if (vff.length > 0) {
                                // textField: 'verInvoice',  去空格 前2 后1
                                String v = vff[0].replace(" ", "");
                                pageValueField = v.substring(2, v.length()-1);
                                valueLine = i+j;
                                endValueFlag = false;
                            }
                        }

                        // 匹配select select: function (e) {
                        if (downLine.contains("select")
                                && downLine.contains(":")
                                && downLine.contains("function")
                                && selectFlag) {

                            Boolean selectCFlag = true;

                            // 下匹配 options.model.set v
                            // TODO 匹配值
                            for (int k = 0; k < 20; k++) {

                                if (i+j+k < lines.size() && selectCFlag) {

                                    String lineStr = lines.get(i+j+k).replace(" ", "");

                                    // 大括号结束
                                    if (lineStr.contains("\\},") && !lineStr.contains(")")) {
                                        selectCFlag = false;
                                    }
                                    //        options.model.set('receiptPartyId', e.item.partyId);
                                    if (lineStr.contains("options.model.set") && lineStr.contains("item") && !lineStr.contains("+")
                                            && lineStr.contains(pageValueField)) {
                                        //     options.model.set('regionOfProperty', e.item.assetKey);
                                        String[] lc = lineStr.split(".set");
                                        String[] lcv = lc[1].split(",");
                                        if (lcv[0].length() > 4) {
                                            selectValue = lcv[0].substring(2, lcv[0].length()-1);
                                            selectFlag = false;
                                        }
                                    }

                                    // 存所有的set属性 区分非数据库字段
                                    if (lineStr.contains("options.model.set") && lineStr.contains("item") && !lineStr.contains("+")) {
                                        //     options.model.set('regionOfProperty', e.item.assetKey);
                                        String[] lc = lineStr.split(".set");
                                        String[] lcv = lc[1].split(",");
                                        if (lcv[0].length() > 4) {
                                            if (StringUtils.isEmpty(selectValues)) {
                                                selectValues = lcv[0].substring(2, lcv[0].length()-1);
                                            } else {

                                                selectValues = selectValues + "/" + lcv[0].substring(2, lcv[0].length()-1) ;
                                            }
                                        }
                                    }
                                }
                            }
                        }


                    }

                    // 向上匹配 找到IF标签 内容的             <#if (hashMap.cmfClmClaimRecInvLine.customerNumber.displayFlag)!true>
                    if (i - j > 0) {

                        if (upIfFlag) {
                            continue;
                        }

                        // 向上匹配到的行数据
                        String upLine = lines.get(i-j);

                        // 如果匹配到了 if关闭标签 就结束
                        if (upLine.contains("</#if>")
                                && !upLine.contains(",")
                                && !upLine.contains("+")
                                && !upLine.contains("'")
                        ) {
                            upIfFlag = true;
                        }

                        if (upLine.contains("<#if")
                                && !upLine.contains("tooltips")
                                && upLine.contains("hashMap")
                                && startIfFlag) {
                            startIfLine =i - j;
                            startIfFlag = false;
                        }
                        if (upLine.contains("<#if")
                                && upLine.contains("hashMap")
                                && upLine.contains("displayFlag")
                                && startDataFlag) {
                            String[] ifh = upLine.split("hashMap.");
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

                if (code != "code" && !StringUtils.isEmpty(region)) {
                    logger.info("区域：" + region + "." + fieldName + " ===========--->  lov: " + code
                            + " ---> pageText:  {" + pageTextField + " ： " +  pageValueField+ " } ，" +
                            "存值： "  + selectValue + " ---> " + selectValues);
                }
                if (endIfLine > startIfLine
                        && (pageTextField.equals(fieldName)
                        ||  textField.equals(fieldName)
                        || valueField.equals(fieldName)
                        || selectValues.contains(fieldName)
                        || selectValue.equals(fieldName))) {
                    if (!selectValue.equals(fieldName) && !"selectValue".equals(selectValue)  && !"basicInfornation".equals(region)) {

                        Map<String, String> map = new HashMap<>();
                        if (regionMap.get(region) != null) {
                            map = regionMap.get(region);
                        }
                        map.put(fieldName, selectValue);
                        map.put(fieldName + "sel", selectValues);
                        regionMap.put(region, map);
                        logger.info(region + "." +fieldName + " ---> " +  code + " ---> "
                                + pageTextField + " ---> " +  pageValueField+ " ---> " +selectValue + " ---> " + selectValues + "\n");
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

            if (!"clms/template/cmf_clm_composite_template.html".equals(tempName[1])) {
               // continue;
            }
            System.out.println("\n"  +  "======================= TMPL ================================");
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

                // 在一行中 <#include "../module/zzz_invoice_summary_line.html"/>
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
                //<#macro cmfClmxxxPayFlow>
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
