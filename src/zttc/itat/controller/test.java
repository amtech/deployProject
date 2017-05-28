package zttc.itat.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class test {

	public static void main(String[] args) throws UnsupportedEncodingException
    {
//        String [] fileName = getFileName("E:\\tomcat\\apache-tomcat-7.0.75\\webapps");
//        for(String name:fileName)
//        {
//            System.out.println(name);
//        }
//        System.out.println("--------------------------------");
//        ArrayList<String> listFileName = new ArrayList<String>(); 
//        getAllFileName("E:\\tomcat\\apache-tomcat-7.0.75\\webapps",listFileName);
//        for(String name:listFileName)
//        {
//            System.out.println(name);
//        }
//		String userInputString = "http://123.57.4.104:9998/springmvc_hello\\WEB-INF\\classes\\zttc\\itat";
//        userInputString = userInputString.replace("\\", "/");//替换/
//        System.out.println(userInputString);//打印字符串
//		File file=new File("E:\\tomcat\\apache-tomcat-7.0.75\\webapps");
//		String path=file.getAbsolutePath();
//        path.replace("\\", "/"); 
//		//System.out.println(path);
//		String str=new String("E:/tomcat/apache-tomcat-7.0.75/webapps");
//		String str1=str.replace("\\", "/");
//		System.out.println(str1);
		
		
		//
		
		
//		 
//		 List<String> files=getFileName("E:/tomcat/apache-tomcat-7.0.75/webapps/验证");
//		 System.out.println(files);
//		 
//		 String path="http://123.57.4.104:9998";
//		 List list=new ArrayList();
//		 String back="";
//		 
//		 for(int i=0;i<files.size();i++){
//			 String back1="";
//			// int j=files.get(i).indexOf("webapps");
//			// System.out.println(j);
//			// String str=path+"/"+files.get(i).substring(j+7, files.get(i).length());
//			 String str=files.get(i);
//			 System.out.println(str);
//			 String str1=str.replace("\\", "/");
//			 System.out.println(str1);
//		//	 str.replace("\\", "/");
//			 list.add(str1);//该list用于返回上传原型页面服务器上的路径
//			// System.out.println(list.get(i));
//			 back1="<tr><td>"+i+"</td><td><a href="+str1+">"+str1+"</a><td></tr>";
//			 back+=back1;
//			 
//		 }
		
//		File file = new File("E:/tomcat/apache-tomcat-7.0.75/webapps/验证");
//	    File[] files=file.listFiles();
//	    String [] fileName = file.list();
//	    List<String> list=new ArrayList<String>();
//	    List<File> list1=new ArrayList<File>();
//	    for(int i=0;i<fileName.length;i++){
//	    	list.add(new String(fileName[i].getBytes(),"utf-8"));
//	    	list1.add(files[i]);
//	    }
//	    System.out.println(list.size());
//	    System.out.println(list1.size());
//	    
//	    for(int i=0;i<list.size();i++){
//	    	 if(list1.get(i).isDirectory()){
//	    		 list.remove(list.get(i));
//	    		
//	    	 }
//	    	 if(list.get(i).lastIndexOf(".")<0){
//	    		 list.remove(list.get(i));
//	    	 }
//	    	 if (list.get(i).lastIndexOf(".") >= 0) {
//                String extName = list.get(i).substring(list.get(i).lastIndexOf("."));
//              //  System.out.println(extName);
//                if(".zip".equals(extName)){
//                	list.remove(list.get(i));
//                }
//	    		 
//	    	 }
//	    	
//	    	 
//	    }
//	    System.out.println(list);
//		
		List list=getFileName("E:/tomcat/apache-tomcat-7.0.75/webapps/验证");
		List list1=(List) list.get(0);
		List list2=(List) list.get(1);
		System.out.println(list1);
		System.out.println(list2);
		
    }
	
	public static List<List> getFileName(String path) throws UnsupportedEncodingException
	{
		List backList=new ArrayList();
		File file = new File("E:/tomcat/apache-tomcat-7.0.75/webapps/验证");
	    File[] files=file.listFiles();
	    String[] fileName = file.list();
	    List<String> list=new ArrayList<String>();//存放文件夹“验证”下的文件名称
	    List<File> list1=new ArrayList<File>();//存放文件夹“验证”下的文件
	    for(int i=0;i<fileName.length;i++){
	    	list.add(new String(fileName[i].getBytes(),"UTF-8"));
	    	list1.add(files[i]);
	    }
	   
	    for(int i=0;i<list.size();i++){
	    	 if(!list1.get(i).isDirectory()){
	    		 //System.out.println(list1.get(i));
	    		 list.remove(list.get(i));
	    		 list1.remove(list1.get(i));
	    	    
	    	 }
	    	 continue;
	     }
	    backList.add(list);
	    backList.add(list1);
	   
	    
	    return backList;//返回所有文件，去除了文件夹和压缩包,该集合中第一个元素为文件名集合，第二个元素为文件集合
	}
	
	
//	public static List<String> getFileName(String path) throws UnsupportedEncodingException
//	{
//		File file = new File("E:/tomcat/apache-tomcat-7.0.75/webapps/验证");
//	    File[] files=file.listFiles();
//	    String [] fileName = file.list();
//	    List<String> list=new ArrayList<String>();
//	    List<File> list1=new ArrayList<File>();
//	    for(int i=0;i<fileName.length;i++){
//	    	list.add(new String(fileName[i].getBytes(),"UTF-8"));
//	    	list1.add(files[i]);
//	    }
//	    System.out.println(list.size());
//	    System.out.println(list1.size());
//	    for(int i=0;i<list.size();i++){
//	    	 if(list1.get(i).isDirectory()){
//	    		 list.remove(list.get(i));
//	    		
//	    	 }
//	    	 if (list.get(i).lastIndexOf(".") >= 0) {
//                String extName = list.get(i).substring(list.get(i).lastIndexOf("."));
//              //  System.out.println(extName);
//                if(".zip".equals(extName)){
//                	list.remove(list.get(i));
//                }
//	    		 
//	    	 }
//	    	
//	    	 
//	    }
//	   
//	    
//	    return list;
//	}
	
	
	
 
	public static void getAllFileName(String path,ArrayList<String> fileName)
    {
        File file = new File(path);
        File [] files = file.listFiles();
        String [] names = file.list();
        if(names != null)
        for(int i=0;i<names.length;i++){
        	names[i]=path+names[i];
        }
        fileName.addAll(Arrays.asList(names));
        for(File a:files)
        {
            if(a.isDirectory())
            {
                getAllFileName(a.getAbsolutePath(),fileName);
            }
        }
    }
	
}
