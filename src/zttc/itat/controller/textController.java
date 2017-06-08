package zttc.itat.controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sun.xml.internal.bind.v2.runtime.NameList;

import zttc.itat.tools.fileTools;


@Controller
public class textController {
 
@SuppressWarnings({ "unchecked", "unused" })
@RequestMapping(value = "/doUpload.do", method = RequestMethod.POST)
	public String uploadFile(HttpServletRequest request,HttpServletResponse response,ModelMap model){
	//	 String filepath ="/usr/local/apache-tomcat-8.5.5/webapps/prototype/";
		 String filepath="E:/tomcat/apache-tomcat-7.0.75/webapps/prototype/";
	//	String filepath ="/Users/Arthur/Documents/apache-tomcat-9/webapps/";
		String backfPath="http://123.57.4.104:9998";
		String message="";
		String fileFname="";
		List fileList = null;
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		upload.setHeaderEncoding("utf-8");
		try {
			fileList= upload.parseRequest(request);
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //迭代器,搜索前端发送过来的文件
        Iterator<FileItem> it = fileList.iterator();
        String name = "";
        String extName = "";
        String savePath = request.getSession().getServletContext().getRealPath("");
     //  String savePath="E:/tomcat/apache-tomcat-7.0.75/webapps";
        int i = 0;
        while (it.hasNext()) {
        	System.out.println(i++);
        		FileItem item = it.next();
        		//判断该表单项是否是普通类型
                if (!item.isFormField()) {
	                	  name = item.getName();
	                    long size = item.getSize();
	                    String type = item.getContentType();
	                    if (name == null || name.trim().equals("")) {
	                        continue;
	                    }
	                    // 扩展名格式： extName就是文件的后缀,例如 .txt
	                    if (name.lastIndexOf(".") >= 0) {
	                       extName = name.substring(name.lastIndexOf("."));
	                    }
	                    fileFname=name.substring(name.lastIndexOf("/")+1,name.lastIndexOf(".") );
//	                    savePath = savePath+"/upload/";
	                    savePath =savePath+"/upload/";
	                    File saveFile = new File(savePath + name);
	                    try {
							item.write(saveFile);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                }
        }
        File file = new File(savePath + name);
        FileOutputStream out  = null;
        InputStream is = null;
        try {
        		 is= new FileInputStream(file); 
			 out = new FileOutputStream(filepath+name);
			 int byteRead=0;
			 byte[] buffer = new byte[1024];
			  try {
				while ((byteRead = is.read(buffer, 0,1024)) != -1) {
						  try {
							out.write(buffer, 0, byteRead);
						} catch (IOException e) {
							e.printStackTrace();
						} 
				    }
				if (extName.equals(".zip")) {
					File iffile=new File(filepath+fileFname);
					if(iffile.isDirectory()){
						fileTools.DeleteFolder(filepath+fileFname);
					}
					textController.unZip(savePath + name,filepath);
				}
				File filezip=new File(filepath+name);//将解压缩前的解压缩文件地址
    				filezip.delete();//删除上传的压缩包，只剩下解压文件
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				 try {
					 if(null!=out){
						 out.close();
					 }
					 if(null!=is){
						 is.close();
					 }
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			message+="上传成功";
			model.addAttribute("path",backfPath+"/"+fileFname);
			model.addAttribute("message",message);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			message="上传失败";
			model.addAttribute("path","");
			model.addAttribute("message",message);
		}
        return "scwj";
	}
/**使用GBK编码可以避免压缩中文文件名乱码*/  
private static final String CHINESE_CHARSET = "utf-8";  
/**文件读取缓冲区大小*/  
private static final int CACHE_SIZE = 1024;  
/** 
 * 解压压缩包 
 * @param zipFilePath 压缩文件路径 
 * @param destDir 解压目录 
 */  
public static void unZip(String zipFilePath, String destDir) {  
    ZipFile zipFile = null;  
    try {  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        BufferedOutputStream bos = null;  
        zipFile = new ZipFile(zipFilePath, CHINESE_CHARSET);  
        Enumeration<ZipEntry> zipEntries = zipFile.getEntries();  
        File file, parentFile;  
        ZipEntry entry;  
        byte[] cache = new byte[CACHE_SIZE];  
        while (zipEntries.hasMoreElements()) {  
            entry = (ZipEntry) zipEntries.nextElement();  
            if (entry.isDirectory()) {  
                new File(destDir + entry.getName()).mkdirs();  
                continue;  
            }  
            bis = new BufferedInputStream(zipFile.getInputStream(entry));  
            file = new File(destDir + entry.getName());  
            parentFile = file.getParentFile();  
            if (parentFile != null && (!parentFile.exists())) {  
                parentFile.mkdirs();  
            }  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos, CACHE_SIZE);  
            int readIndex = 0;  
            while ((readIndex = bis.read(cache, 0, CACHE_SIZE)) != -1) {  
                fos.write(cache, 0, readIndex);  
            }  
            bos.flush();  
            bos.close();  
            fos.close();  
            bis.close();  
        }  
    } catch (IOException e) {  
        e.printStackTrace();  
    }finally{  
        try {  
            zipFile.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  



@SuppressWarnings({ "unchecked", "unused" })
@RequestMapping(value = "/showUploaded.do", method = RequestMethod.GET)
	public String ShowUploaded(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException, ServletException  {
	 request.setCharacterEncoding("utf-8");
	 response.setContentType("text/html;   charset=utf-8"); 
	 ArrayList<String> listFileName = new ArrayList<String>();
	 List backlist=getFileName("E:/tomcat/apache-tomcat-7.0.75/webapps/prototype/");
	 List<String> filesname=(List<String>) backlist.get(0);
	 List<File> files=(List<File>) backlist.get(1);
	 String path="http://123.57.4.104:9998/prototype";
	 List list=new ArrayList();
	 String back="";
	 int j=0;
	 List namelist=new ArrayList();
	 for(int i=0;i<filesname.size();i++){
		 String back1="";
		 String str=path+"/"+filesname.get(i);
		 String str2=str.replace("\\", "/");
		 String str1=new String(str2.getBytes(),"utf-8");
		// String str1=str2;
		 String lastModifyTime=getLastModifyTime(files.get(i));
		 j=i+1;
		 back1="<tr><td>"+j+"</td><td><a href="+str1+">"+filesname.get(i)+"</a></td><td>"+lastModifyTime+"</td></tr>";
		 back+=back1;
		 //namelist.add(back1);
	 }
	 model.addAttribute("name",back);
	 return "scwj1";
	
}

@SuppressWarnings("unchecked")
public static List<List> getFileName(String path) throws UnsupportedEncodingException
{
	List backList=new ArrayList();
	File file = new File(path);
    File[] files=file.listFiles();
     String[] fileName = file.list();
    List<String> list=new ArrayList<String>();//存放文件夹“验证”下的文件名称
    List<File> list1=new ArrayList<File>();//存放文件夹“验证”下的文件
    for(int i=0;i<fileName.length;i++){
    	try{
    		list.add(new String(fileName[i].getBytes(),"utf-8"));
    		//list.add(fileName[i]);
        	list1.add(files[i]);
    	}catch(Exception e){
    		e.printStackTrace();
    		break;
    	}
    }
    List<String> templist=new ArrayList<String>();//文件名称
    List<File> templist1=new ArrayList<File>();//文件
     for(int i=0;i<list.size();i++){
    	 if(list1.get(i).isDirectory()){
    		 templist.add(list.get(i));
    		 templist1.add(list1.get(i));
    	 }
    	 continue;
     }
    Collections.sort(templist1,new Comparator<File>(){
    	public int compare(File f1,File f2){
    		Date d1=new Date(f1.lastModified());
    		Date d2=new Date(f2.lastModified());
    		Long l1=d1.getTime();
    		Long l2=d2.getTime();
    		if(l1>l2){
    			return -1;
    		}if(l1<l2){
    			return 1;
    		}else{
    			return 0;
    		}
    	}
    });
    List l=new ArrayList();
    for(int i=0;i<templist1.size();i++){
    	l.add(templist1.get(i).getName());
    }
    backList.add(l);
    backList.add(templist1);
    return backList;//返回所有文件，去除了文件夹和压缩包,该集合中第一个元素为文件名集合，第二个元素为文件集合
}

public ModelAndView getModelAndView(){
	return new ModelAndView();
}

public static String getLastModifyTime(File testfile){
	Date lastModified = new Date(testfile.lastModified()); //文件最后修改时间
	SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String dataTimeStr = fmt.format(lastModified);
	return dataTimeStr;
}

}
