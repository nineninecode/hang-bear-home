//package com.wzh.home;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import freemarker.template.TemplateException;
//
//public class YamlTestList {
//    //模板配置对象
//    private Configuration cfg;
//    //Yaml目录
//    private static String yamlPath = "D:\\resources\\yaml";
//
//    /**
//     * 初始化配置
//     */
//    public void init() {
//        String path = this.getClass().getClassLoader().getResource("").getPath();
//        System.out.println(path);
//        cfg=new Configuration(Configuration.getVersion());
//        File yamlFile = null;
//        try {
//            yamlFile = new File(yamlPath);
//            cfg.setDirectoryForTemplateLoading(yamlFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void process(Map<String, Object> map){
//        try {
//            Template template = cfg.getTemplate("spark-1.ftl");
//            //template.
//            template.process(map, new FileWriter(new File(yamlPath + "\\test.yaml")));
//        } catch (IOException | TemplateException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//
//
//        YamlTestList test = new YamlTestList();
//        test.init();
//
//        //生成一个pod的yaml文件
//        Map<String,Object> podMap = new HashMap<String,Object>();
//        podMap.put("kind", "Pod");
//        podMap.put("apiVersion", "v1");
//        podMap.put("metadataName", "test-pod");
//        podMap.put("lablesName", "test-pod");
//        podMap.put("lablesVersion", "1.0");
//
//        //此处是一个循环 一个pod包含多个微服务组件
//        podMap.put("containerName", "tomcat");
//        podMap.put("imageName", "tomcatImage");
//
//        //组装一个container里面的port列表
//        //一个docker container里面可能有多个组件，比如一个container部署了两个tomcat，则此处是port的列表
//        List<Map<String,Object>> portList = new ArrayList<>();
//        Map<String,Object> portMap1 = new HashMap<String,Object>();
//        portMap1.put("containerPort", 8080);
//        Map<String,Object> portMap2 = new HashMap<String,Object>();
//        portMap2.put("containerPort", 8081);
//        portList.add(portMap1);
//        portList.add(portMap2);
//
//        podMap.put("portList", portList);
//        test.process(podMap);
//
//        // myMap[key]?default("")，这里是判断值是否为null，如果为null，设置默认值为空，freemarker不支持null，如果值为null，会抛出异常报错。
//    }
//}