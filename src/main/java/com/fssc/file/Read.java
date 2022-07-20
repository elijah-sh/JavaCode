package com.fssc.file;

import com.common.OddLines;
import com.util.StringPattenUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Read {
    public final static String localPath = "/Users/shenshuaihu/SoftWare/MyFile/code/IdeaProjects/JavaCode/src/main/resources/datas/test.html";
    public final static String path = "/Users/shenshuaihu/IdeaProjects/fssc/fssc-parent/fssc-erbp/fssc-erbp-core/src/main/webapp/WEB-INF/view/clms/template/cmf_clm_pay_application_template.html";
    public final static String fileClmPath = "/Users/shenshuaihu/IdeaProjects/fssc/fssc-parent/fssc-erbp/fssc-erbp-core/src/main/webapp/WEB-INF/view/clms/";
    public final static String fileClmTempPath = "/Users/shenshuaihu/IdeaProjects/fssc/fssc-parent/fssc-erbp/fssc-erbp-core/src/main/webapp/WEB-INF/view/clms/template/";

    public static void main(String[] args) {
        Read read = new Read();
      // getPathFilesName();
        //getFilesData(path);
        // getClaimFilesName();
        // getClaimTempFilesName();

       // getClaimFilesNameByText();

        // 匹配所有
     //   read.getClaimTempFilesName();

        // LOV
       // getClaimLOVByText();

       // getFilesData(path);

        getClaimFilesNameByText();
    }


    // 按照模版
    public void getClaimTempFilesName() {


         // Files.lines方法来作为内存高效的替代。这个方法读取每一行，并使用函数式数据流来对其流式处理，而不是一次性把所有行都读进内存
        //try  {
        //    Stream<String> stream = Files.lines(Paths.get(path));
        //    stream
        //            .filter(line -> (line.contains("<#include") ))
        //            .map(String::trim)
        //            .forEach(System.out::println);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

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
            // System.out.println("walk(): " + joined);
            String[] paths = joined.split(";");
          //  System.out.println("匹配数量L：" + paths.length);
            for (String p : paths) {

                String[] tempName = p.split("/template/");
                if ("demo_template.html".equals(tempName[1])) {
                    continue;
                }
                System.out.println("========================= TMPL ================================");
                System.out.println(tempName[1]);

                Map<String, String> map = new HashMap<>();
                 // 模版维度
                List<String> lines = Files.readAllLines(Paths.get(p));
                //
                List<String> claimLines = new ArrayList<>();

                List<String> macroList = new ArrayList<>();
                List<String> claimPaths = new ArrayList<>();
                // 循环模版内容
                for (int i = 0; i < lines.size(); i++) {
                    // 获取@标签
                    if (lines.get(i).contains("<@") && lines.get(i).contains("/>")
                            && !lines.get(i).contains(" ")
                            && !lines.get(i).contains(";")
                            && !lines.get(i).contains("\"")) {
                        String[] ats = lines.get(i).split("@");
                        String at = ats[1].substring(0, ats[1].length()-2);
                        macroList.add(at);
                    }

                    // 在一行中
                    if (lines.get(i).contains("<#include") && lines.get(i).contains("/>")) {
                        // 解析数据
                        String[] s = lines.get(i).split("\"");
                        String pa = s[1].substring(0, 3);
                        if ("../".equals(pa)) {
                            // 拼接文件目录 引入行或者头文件路径
                            String linePath = fileClmTempPath.substring(0, fileClmTempPath.length() - 9) + s[1].substring(3, s[1].length());
                            claimPaths.add(linePath);
                        }
                    }
                }

                // 整合模版数据
                for (String path :  claimPaths) {
                    // 匹配成功之后将行内容拼接到模版中
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
                        claimLines.addAll(line);
                    }
                }

                // 整合模版内容
                lines.addAll(claimLines);

                // 通过模版匹配数据
               // getClaimFilesByText(lines);

                // LOV
                this.getClaimLOVByText(lines);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    // 直接匹配数据 没有按照模版来

    /**
     * 获取区域与定应的数据
     */
    public static void getClaimFilesNameByText() {


        // 逐行读取数据
        Path start = Paths.get(fileClmPath);
        int maxDepth = 5;
        //  Files.walk()方法用于遍历子文件(包括文件夹)。
        try (Stream<Path> stream = Files.walk(start, maxDepth)) {
            String joined = stream
                    .map(String::valueOf)
                    .filter(path -> path.endsWith(".html"))
                    .sorted()
                    .collect(Collectors.joining(";"));
            // System.out.println("walk(): " + joined);
            String[] paths = joined.split(";");
            System.out.println("匹配数量L：" + paths.length);
            List<String> goLines = new ArrayList<>();

            for (String p : paths) {




                 getFilesData(p);

                List<String> lines = Files.readAllLines(Paths.get(p));

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

            }

            // 读取“base_grid_html”关键词，把前端组件标识和后台数据库字段对应起来
            for (String s : goLines) {
                if (s.contains("id=") && s.contains("${(hashMap.")) {

                    String[] aaa = s.split("hashMap.");
                    String[] aaaa = aaa[1].split(".alias");

                 //   System.out.println(aaaa[0]);

                    //String s1 = s.substring(21, s.length());
                    String[] aa = s.split("\"");
                 //   System.out.println(aa[1]);
                    // 匹配两个字符是否相等
                    if (!aaaa[0].equals(aa[1])) {
                    //    System.out.println(s);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // 直接匹配数据 没有按照模版来
    public static void getClaimFilesName() {


            // Files.lines方法来作为内存高效的替代。这个方法读取每一行，并使用函数式数据流来对其流式处理，而不是一次性把所有行都读进内存
            //try  {
            //    Stream<String> stream = Files.lines(Paths.get(path));
            //    stream
            //            .filter(line -> (line.contains("<#include") ))
            //            .map(String::trim)
            //            .forEach(System.out::println);
            //} catch (IOException e) {
            //    e.printStackTrace();
            //}

        // 逐行读取数据
        Path start = Paths.get(fileClmPath);
        int maxDepth = 5;
        //  Files.walk()方法用于遍历子文件(包括文件夹)。
        try (Stream<Path> stream = Files.walk(start, maxDepth)) {
            String joined = stream
                    .map(String::valueOf)
                    .filter(path -> path.endsWith(".html"))
                    .sorted()
                    .collect(Collectors.joining(";"));
            // System.out.println("walk(): " + joined);
            String[] paths = joined.split(";");
            System.out.println("匹配数量L：" + paths.length);
            for (String p : paths) {
               // getFilesData(p);

                List<String> lines = Files.readAllLines(Paths.get(p));
                for (int i = 0; i < lines.size(); i++) {
                    // 在一行中
                    if (lines.get(i).contains("<@base_grid_html") && lines.get(i).contains("</@base_grid_html>")) {
                        System.out.println("1 " + lines.get(i));
                    }
                    //
                    // 在两行中
                    if (lines.get(i).contains("<@base_grid_html") && !lines.get(i).contains("</@base_grid_html>") && lines.get(i+1).contains("</@base_grid_html>")) {
                        System.out.println("2 " + lines.get(i) + lines.get(i+1));
                    }

                    // 在三行中
                    if (lines.get(i).contains("<@base_grid_html")
                            && !lines.get(i).contains("</@base_grid_html>")
                            && !lines.get(i+1).contains("</@base_grid_html>") && lines.get(i+2).contains("</@base_grid_html>")) {
                        System.out.println("3 " + lines.get(i)  + lines.get(i+1) +  lines.get(i+2));
                    }
                }
                for (String line : lines) {


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        /**
         * 查找在目录及其子目录下的文件
         *
         * 目录路径start是起始点
         * maxDepth定义了最大搜索深度
         * 是一个匹配谓词
         */
    public static void getPathFilesName() {

        // 如果你需要更多的精细控制，你需要构造一个新的BufferedReader来代替：
        Path path1 = Paths.get(path);

        try (BufferedReader reader = Files.newBufferedReader(path1)) {
            long countPrints = reader
                    .lines()
                   // .filter(line -> line.contains("print"))
                    .count();
            System.out.println(countPrints);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader reader = Files.newBufferedReader(path1)) {
            System.out.println(reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 逐行读取数据
        Path start = Paths.get(fileClmPath);
        int maxDepth = 5;
        //  Files.walk()方法用于遍历子文件(包括文件夹)。
        try (Stream<Path> stream = Files.walk(start, maxDepth)) {
            String joined = stream
                    .map(String::valueOf)
                    .filter(path -> path.endsWith(".html"))
                    .sorted()
                    .collect(Collectors.joining(";"));
            System.out.println("walk(): " + joined);
            String[] paths = joined.split(";");
            System.out.println("匹配数量L：" + paths.length);
            for (String p : paths) {
                System.out.println("walk(): " + p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 列出文件
        try (Stream<Path> stream = Files.list(Paths.get(fileClmPath))) {
            String joined = stream
                    .map(String::valueOf)
                    .filter(path -> !path.startsWith("."))
                    .sorted()
                    .collect(Collectors.joining(";  \n "));
            System.out.println("List: " + joined);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Stream<Path> stream = Files.find(start, maxDepth, (path, attr) ->
                String.valueOf(path).endsWith(".html"))) {
            String joined = stream
                    .sorted()
                    .map(String::valueOf)
                    .collect(Collectors.joining(";  \n  "));
            System.out.println("Found: " + joined);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getFilesData(String path) {

        // 这些方法对内存并不十分高效，因为整个文件都会读进内存。文件越大，所用的堆区也就越大。
        List<String> lines = null;
        try {
            // 从指定的文件把所有行读进字符串列表中
            lines = Files.readAllLines(Paths.get(path));
            lines.add("print('foobar');");
            // 写到另一个文件中
           // Files.write(Paths.get(localPath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (String line : lines) {

            // 匹配替换注释
            Boolean singleLineComment = StringPattenUtil.isSingleLineComment(line);
            if (singleLineComment) {
                line = line.split("//")[0];
            }
            Boolean multiLineComment = StringPattenUtil.isMultiLineComment(line);
            if (multiLineComment) {
                line = "";
            }

            // 要匹配多内容
            //   String commentsStr = "is single line//this i" +   "//s single line comments";
            // 匹配规则  单行注释
            // ^表示字符串的开始  .表示任意字符  *0或多个前面出现的正则表达式  $表示字符串行末或换行符之前
            // 去空格 匹配
        }


        // Files.lines方法来作为内存高效的替代。这个方法读取每一行，并使用函数式数据流来对其流式处理，而不是一次性把所有行都读进内存
        //try {
        //    Stream<String> stream = Files.lines(Paths.get(path));
        //    stream
        //            //.filter(line -> (line.contains("<@base_grid_html") && !line.contains(">")))
        //            //.filter(line -> (line.contains("<#include") && !line.contains(">")))
        //            .map(String::trim)
        //            .forEach(System.out::println);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
        //try {
        //    Stream<String> stream = Files.lines(Paths.get(path));
        //    stream.filter(line -> {
        //        // 匹配到第一个标签 没有匹配到第二个标签
        //        if (line.contains("<@base_grid_html") && !line.contains("</@base_grid_html>")) {
        //            // 获取第二行数据
        //        }
        //        return line.contains("<@base_grid_html");
        //    })
        //            .map(String::trim)
        //            .forEach(System.out::println);
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

    }
    /**
     * 读写文件
     *
     * Files.readAllLines从指定的文件把所有行读进字符串列表中
     * Files.write写到另一个文件中
     */
    public static void getFilesData() {


        // Files.lines方法来作为内存高效的替代。这个方法读取每一行，并使用函数式数据流来对其流式处理，而不是一次性把所有行都读进内存
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream
                    .filter(line -> line.contains("<@base_grid_html"))
                    .map(String::trim)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 这些方法对内存并不十分高效，因为整个文件都会读进内存。文件越大，所用的堆区也就越大。
        List<String> lines = null;
        try {
            // 从指定的文件把所有行读进字符串列表中
            lines = Files.readAllLines(Paths.get(path));
            lines.add("print('foobar');");
            // 写到另一个文件中
            Files.write(Paths.get(localPath), lines);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    // 按照模版
    public static void getClaimTempFilesNames() {




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
            // System.out.println("walk(): " + joined);
            String[] paths = joined.split(";");
            //  System.out.println("匹配数量L：" + paths.length);
            for (String p : paths) {

                String[] tempName = p.split("/template/");
                if ("demo_template.html".equals(tempName[1])) {
                    continue;
                }
                System.out.println(tempName[1]);

                Map<String, String> map = new HashMap<>();
                // 模版维度
                List<String> lines = Files.readAllLines(Paths.get(p));
                //
                List<String> claimLines = new ArrayList<>();
                // 循环模版内容
                for (int i = 0; i < lines.size(); i++) {
                    // 在一行中
                    if (lines.get(i).contains("<#include") && lines.get(i).contains("/>")) {
                        // System.out.println("include " + lines.get(i));
                        // 解析数据
                        String[] s = lines.get(i).split("\"");
                        //System.out.println(s[1]);
                        String pa = s[1].substring(0, 3);
                        if ("../".equals(pa)) {
                            // 拼接文件目录 引入行或者头文件路径
                            String linePath = fileClmTempPath.substring(0, fileClmTempPath.length() - 9) + s[1].substring(3, s[1].length());
                            //System.out.println(linePath);

                            // 匹配成功之后将行内容拼接到模版中
                            List<String> line = Files.readAllLines(Paths.get(linePath));
                            // 读取之后批量带有@ 内容
                            //<#macro cmfClmClaimPayFlow>
                            for (String l : line) {
                                if (l.contains("<#macro") && l.contains(">")) {
                                    // 截取出去  匹配模版内容
                                    // claimLines.addAll(line);
                                }
                            }
                            claimLines.addAll(line);
                        }

                    }

                    // 读取数据
                    //if (lines.get(i).contains("<@") && lines.get(i).contains("/>")
                    //        && !lines.get(i).contains(" ")
                    //        && !lines.get(i).contains(";")
                    //        && !lines.get(i).contains("\"")) {
                    //    System.out.println("<@ " + lines.get(i));
                    //    String at = lines.get(i).substring(2, lines.get(i).length() -2);
                    //    System.out.println(at);
                    //}
                }

                // 整合模版内容
                lines.addAll(claimLines);

                // 通过模版匹配数据
                getClaimFilesByText(lines);





            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * 获取区域与定应的数据 LOV
     */
    public void getClaimLOVByText(List<String> lines) {


        List<String> goLines = new ArrayList<>();

        for (int i = 0; i < lines.size(); i++) {
            // 找到关键字 kendoLov
            if (lines.get(i).contains("kendoLov(")) {
                System.out.println("1 " + lines.get(i));
                // 往下匹配到括号结束
                String[] v = lines.get(i).split("kendoLov");
                StringBuilder lov = new StringBuilder();
                lov.append(v[1]);
                // 下匹配200行
                for (int j = 1; j < 200; j++) {
                    if (i+j >= lines.size()) {
                        lov.append(lines.get(i+j -1) + "\n");
                        break;
                    }
                    lov.append(lines.get(i+j) + "\n");
                }


                String lovv = isValid(lov.toString());
                if (lovv.length() != lov.toString().length()) {
                   // System.out.println(lov);
                }

                // 匹配数据  textField valueField
                String valueField = "";
                String textField = "";

                if (lovv.contains("textField") && lovv.contains("valueField") ) {
                    String[] ts = lovv.split("valueField");
                    String[] tss = ts[1].split(",");
                    if (tss.length > 1) {
                        // textField: 'verInvoice',  去空格 前2 后1
                        String t = tss[0].replace(" ", "");
                       // System.out.println(t.substring(2, t.length()-1));
                        valueField = t.substring(2, t.length()-1);
                    }

                    String[] vs = lovv.split("textField");
                    String[] vss = vs[1].split(",");

                    if (vss.length > 1) {
                        // textField: 'verInvoice',  去空格 前2 后1
                        String t = vss[0].replace(" ", "");
                      //  System.out.println(t.substring(2, t.length()-1));
                        textField = t.substring(2, t.length()-1);
                    }
                  }

                // 向上匹配 找到IF标签 内容的             <#if (hashMap.cmfClmClaimRecInvLine.customerNumber.displayFlag)!true>
                List<String> ifLine = new ArrayList<>();
                for (int j = 1; j < 100; j++) {
                    if (i - j <= 0) {
                        ifLine.add(lines.get(i - j));
                        break;
                    }
                    ifLine.add(lines.get(i - j));
                }

                String ifc =  ""; //isValidIf(ifLine);
                //System.out.println(ifc);
                String regcon =  "";
                for (String s : ifLine) {

                    if (s.contains("<#if") && s.contains("hashMap") && s.contains("displayFlag")) {
                        String[] aaa = s.split("hashMap.");
                        String[] aaaa = aaa[1].split(".displayFlag");
                        String[] aaaaa = aaaa[0].split("\\.");
                        if (aaaaa.length > 1) {
                            // 字段
                            ifc = aaaaa[1];
                            // 区域
                            regcon = aaaaa[0];
                        }

                    }
                }

                // 如果字段等于LOV的一个
                if (textField.equals(ifc) || valueField.equals(ifc)) {
                    if (!valueField.equals(ifc)) {
                        System.out.println(regcon + "  "   +ifc + " " + valueField);
                    }
                }
            }
            // 括号匹配
            // 在两行中

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



    // 括号匹配
    public static String isValid(String s) {
        StringBuilder slov = new StringBuilder();
        Stack<Character> stack = new Stack<Character>();
        char l = "()".charAt(0);
        char r = "()".charAt(1);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            slov.append(c);
            if (l != c && r != c) {
                continue;
            }
            // 左（
            if (l == c) {
                // 入栈
                stack.push(c);
            }
            // 右 ）
            if (r == c) {
                stack.pop();
                if (stack.empty()) {
                    return slov.toString();
                }
            }
        }
        return slov.toString();
    }

    // 括号匹配
    public static List<String> isValidLine(List<String> lines) {
        List<String> list = new ArrayList<>();
        StringBuilder slov = new StringBuilder();
        Stack<Character> stack = new Stack<Character>();
        char l = "()".charAt(0);
        char r = "()".charAt(1);

        for (String s : lines) {
            for (int i = 0; i < s.length(); i++) {
                // 如果遇到注释行就不匹配
                list.add(s);
                char c = s.charAt(i);
                slov.append(c);
                if (l != c && r != c) {
                    continue;
                }
                // 左（
                if (l == c) {
                    // 入栈
                    stack.push(c);
                }
                // 右 ）
                if (r == c) {
                    stack.pop();
                    if (stack.empty()) {
                        return list;
                    }
                }
            }
        }


        return list;
    }


    // 括号IF
    public String isValidIf(List<String> lines) {
        for (String s : lines) {

            if (s.contains("<#if") && s.contains("hashMap") && s.contains("displayFlag")) {
                String[] aaa = s.split("hashMap.");
                String[] aaaa = aaa[1].split(".displayFlag");
                String[] aaaaa = aaaa[0].split("\\.");
                return aaaaa[1];
            }
        }

        return "";
    }



}
