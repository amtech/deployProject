package zttc.itat.controller;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tools.zip.ZipFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;




@Controller
public class uploadController {
	private static final long SIZEMax =1024*1024*100;//�ϴ�����ļ�ʱ�����ֵ10M
	private static final int SizeThreshold =1024*100;//��������������С100k
	private static final long FileSizeMax =1024*1024*1000000;//�ϴ������ļ�ʱ�����ֵ1M
	
	
	
	
//	public static final String ZIP_FILENAME = "D:/haha.zip";// 需要解压缩的文件名
//	 public static final String ZIP_DIR = "D:/haha";// 需要压缩的文件夹
//	 public static final String UN_ZIP_DIR = "D:/test/";// 要解压的文件目录
	 public static final int BUFFER = 1024;// 缓存大小

	/*
	 @RequestMapping(value = "/showUploaded.do", method = RequestMethod.POST)
	 @ResponseBody
		public String ShowUploaded(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) throws IOException, ServletException  {
			
		 File file=new File("E:\\tomcat\\apache-tomcat-7.0.75\\webapps");
		 String[] uploadeds=file.list();
		 ArrayList<String> listFileName = new ArrayList<String>();
		 
		 getAllFileName("E:\\tomcat\\apache-tomcat-7.0.75\\webapps",listFileName);
		 
		 model.addAttribute("uploadedNo",listFileName);
		 String path="http://123.57.4.104:9998";
		 List list=new ArrayList();
		 for(int i=0;i<listFileName.size();i++){
			 int j=listFileName.get(i).indexOf("webapps");
			 String str=path+"/"+listFileName.get(i).substring(j+7, listFileName.get(i).length());
			 str.replace("\\", "/");
			 list.add(str);
			 System.out.println(list.get(i));
			 
		 }
		 return "scwj";
		
	 }
	*/
	 
	 @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
		public String shangchuanPage(HttpServletRequest request,
				HttpServletResponse response, ModelMap model) throws IOException, ServletException  {
			String message="";
			//本机测试使用配置
//			String savePath="/usr/local/apache-tomcat-6.0.45/webapps/";
//			String backfPath="http://123.57.4.104:9090";
//			String tempPath="/usr/local/apache-tomcat-6.0.45/webapps/";
			String savePath="E:\\tomcat\\apache-tomcat-7.0.75\\webapps";
			String backfPath="http://123.57.4.104:9090";
			String tempPath="E:\\tomcat\\apache-tomcat-7.0.75\\webapps";
			
			
			String unzipPath="";
			String fileExtName="";
			String fileFname="";
			System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
		     File tmpFile = new File(tempPath);
		     if (!tmpFile.exists()) {
		      tmpFile.mkdir();
		     }
		     String path="";
		     try{
		      DiskFileItemFactory factory = new DiskFileItemFactory();
		      factory.setSizeThreshold(SizeThreshold);
		      factory.setRepository(tmpFile);
		      ServletFileUpload upload = new ServletFileUpload(factory);
		      upload.setProgressListener(new ProgressListener(){
		       public void update(long pBytesRead, long pContentLength, int arg2) {
		        System.out.println("文件大小为：" + pContentLength + ",当前已处理" + pBytesRead);
		       }
		      });
		      upload.setHeaderEncoding("utf-8"); 
		      upload.setFileSizeMax(FileSizeMax);
		      upload.setSizeMax(SIZEMax);
		      List<FileItem> list = upload.parseRequest(request);
		      for(FileItem item : list){
		    	if(!item.isFormField()){
		    		String filename = item.getName();
		        if(filename==null || filename.trim().equals("")){
		         continue;
		        }
		        filename = filename.substring(filename.lastIndexOf("/")+1);
		        fileFname=filename.substring(filename.lastIndexOf("/")+1,filename.indexOf(".") );
		        fileExtName = filename.substring(filename.lastIndexOf(".")+1);
		        System.out.println("文件后缀名为"+fileExtName);
		        InputStream in = item.getInputStream();
		        String outputPath=savePath+"/"+filename;
		        File file=new File(outputPath);
		        if(file.exists()){
		        	file.delete();
		        	filename=filename.substring(filename.lastIndexOf("/")+1);
		        	outputPath=savePath+"/"+filename;
		        	file=new File(outputPath);
		        }
		        DeleteFolder(savePath+"/"+fileFname);
		        FileOutputStream out = new FileOutputStream(outputPath);
		        byte buffer[] = new byte[10240000];
		        int len=0;
		        while((len=in.read(buffer))>0){
		         out.write(buffer, 0, len);
		       }
		        	if("zip".equals(fileExtName)){
		        		//textController.unzip(outputPath, savePath);
		        		File filezip=new File(outputPath);//将解压缩前的解压缩文件地址
		        		filezip.delete();//删除上传的压缩包，只剩下解压文件
		        	}
		         in.close();
		        out.close();        
		       }
		      }
		     message+="上传成功";
		    model.addAttribute("path",backfPath+"/"+fileFname);
			model.addAttribute("message",message);
			return "scjg";
		}catch(Exception e){
			e.printStackTrace();
			message="上传失败";
			model.addAttribute("path","");
			model.addAttribute("message",message);
			return "scjg";
		}
	 }
	 
	 public static void relaseZipToFile1(String sourceZip, String outFileName){
		 File source = new File(sourceZip);
		 if (source.exists()) {
			 ZipInputStream ziStream = null;
			 BufferedOutputStream bos = null;
			 try {
				ziStream = new ZipInputStream(new FileInputStream(source));
				ZipEntry entry = null;
				try {
					while ((entry = ziStream.getNextEntry()) != null  && !entry.isDirectory()) {
						 File target = new File(source.getParent(), entry.getName());
						 if (!target.getParentFile().exists()) {
		                        // 创建文件父目录
		                        target.getParentFile().mkdirs();
		                    }
		                    // 写入文件
		                    bos = new BufferedOutputStream(new FileOutputStream(target));
		                    int read = 0;
		                    byte[] buffer = new byte[1024 * 10];
		                    while ((read = ziStream.read(buffer, 0, buffer.length)) != -1) {
		                        bos.write(buffer, 0, read);
		                    }
		                    bos.flush();
					}
					ziStream.closeEntry();
					ziStream.close();
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		 }
	 }
//	 //解压方法 
//	 public static void releaseZipToFile(String sourceZip, String outFileName)
//	            throws IOException{
//		 System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding"));
//	          ZipFile zfile=new ZipFile(sourceZip,"utf-8");
//	          System.out.println(zfile.getEncoding());
//	          Enumeration zList=zfile.getEntries();
//	          ZipEntry ze=null;
//	          byte[] buf=new byte[1024];
//	          while(zList.hasMoreElements()){
//	          //从ZipFile中得到一个ZipEntry
//	          ze=(ZipEntry)zList.nextElement();
//	          if(ze.isDirectory()){
//	          continue;
//	          }
//	         File file=getRealFileName(outFileName, ze.getName());
//	         file.delete();
//	          //以ZipEntry为参数得到一个InputStream，并写到OutputStream中
//	          OutputStream os=new BufferedOutputStream(new FileOutputStream(getRealFileName(outFileName, ze.getName())));
//	          InputStream is=new BufferedInputStream(zfile.getInputStream(ze));
//	          int readLen=0;
//	          while ((readLen=is.read(buf, 0, 1024))!=-1) {
//	          os.write(buf, 0, readLen);
//	          }
//	          is.close();
//	          os.close();
//	       //   System.out.println("Extracted: "+ze.getName());
//	          }
//	          zfile.close();
//	}  
		private static File getRealFileName(String baseDir, String absFileName) {
	        String[] dirs = absFileName.split("/");
	        //System.out.println(dirs.length);
	        File ret = new File(baseDir);
	        //System.out.println(ret);
	        if (dirs.length > 1) {
	                for (int i = 0; i < dirs.length - 1; i++) {
	                        ret = new File(ret, dirs[i]);
	                }
	        }
	        if (!ret.exists()) {
	                ret.mkdirs();
	        }
	        ret = new File(ret, dirs[dirs.length - 1]);
	        return ret;
	}
	 /*
		public static void unzip(String zipFilePath, String destDir)  
		  {  
		    System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding")); //防止文件名中有中文时出错  
		    //System.out.println(System.getProperty("sun.zip.encoding")); //ZIP编码方式  
		    //System.out.println(System.getProperty("sun.jnu.encoding")); //当前文件编码方式  
		    //System.out.println(System.getProperty("file.encoding")); //这个是当前文件内容编码方式  
		      
		    File dir = new File(destDir);  
		    // create output directory if it doesn't exist  
		    if (!dir.exists()) dir.mkdirs();  
		    FileInputStream fis;  
		    // buffer for read and write data to file  
		    byte[] buffer = new byte[1024];  
		    try  
		    {  
		      fis = new FileInputStream(zipFilePath);  
		      ZipInputStream zis = new ZipInputStream(fis);  
		      ZipEntry ze = zis.getNextEntry();  
		      while (ze != null)  
		      {  
		        String fileName = ze.getName();  
		        File newFile = new File(destDir + File.separator + fileName);  
		        //System.out.println("Unzipping to " + newFile.getAbsolutePath());  
		        // create directories for sub directories in zip  
		        new File(newFile.getParent()).mkdirs();  
		        FileOutputStream fos = new FileOutputStream(newFile);  
		        int len;  
		        while ((len = zis.read(buffer)) > 0)  
		        {  
		          fos.write(buffer, 0, len);  
		        }  
		        fos.close();  
		        // close this ZipEntry  
		        zis.closeEntry();  
		        ze = zis.getNextEntry();  
		      }  
		      // close last ZipEntry  
		      zis.closeEntry();  
		      zis.close();  
		      fis.close();  
		    }  
		    catch (IOException e)  
		    {  
		      e.printStackTrace();  
		    }  
		  
		  }  
		*/
	 
	 /**
	  * 解压文件API
	  */
	
		
	 /**
	  * 删除文件夹，包括当前目录和子目录
	  * */
		
	 public static  boolean DeleteFolder(String sPath) {  
		    boolean flag = false;  
		    File file = new File(sPath);  
		    // 判断目录或文件是否存在  
		    if (!file.exists()) {  // 不存在返回 false  
		        return flag;  
		    } else {  
		        // 判断是否为文件  
		        if (file.isFile()) {  // 为文件时调用删除文件方法  
		            return deleteFile(sPath);  
		        } else {  // 为目录时调用删除目录方法  
		            return deleteDirectory(sPath);  
		        }  
		    }  
		}  
	 public	static  boolean deleteDirectory(String sPath) {  
		    //如果sPath不以文件分隔符结尾，自动添加文件分隔符  
		    if (!sPath.endsWith(File.separator)) {  
		        sPath = sPath + File.separator;  
		    }  
		    File dirFile = new File(sPath);  
		    //如果dir对应的文件不存在，或者不是一个目录，则退出  
		    if (!dirFile.exists() || !dirFile.isDirectory()) {  
		        return false;  
		    }  
		    boolean flag = true;  
		    //删除文件夹下的所有文件(包括子目录)  
		    File[] files = dirFile.listFiles();  
		    for (int i = 0; i < files.length; i++) {  
		        //删除子文件  
		        if (files[i].isFile()) {  
		            flag = deleteFile(files[i].getAbsolutePath());  
		            if (!flag) break;  
		        } //删除子目录  
		        else {  
		            flag = deleteDirectory(files[i].getAbsolutePath());  
		            if (!flag) break;  
		        }  
		    }  
		    if (!flag) return false;  
		    //删除当前目录  
		    if (dirFile.delete()) {  
		        return true;  
		    } else {  
		        return false;  
		    }  
		}  
	 public static boolean deleteFile(String sPath) {  
		    boolean flag = false;  
		    File file = new File(sPath);  
		    // 路径为文件且不为空则进行删除  
		    if (file.isFile() && file.exists()) {  
		        file.delete();  
		        flag = true;  
		    }  
		    return flag;  
		}  
	 
	 
	 
	 
	 public static String [] getFileName(String path)
	    {
	        File file = new File(path);
	        String [] fileName = file.list();
	        return fileName;
	    }
	 
	 public static void getAllFileName(String path,ArrayList<String> fileName)
	    {
	        File file = new File(path);
	        File [] files = file.listFiles();
	        String [] names = file.list();
	        if(names != null)
	        for(int i=0;i<names.length;i++){
	        	names[i]=path+"\\"+names[i];
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



	
	
	
	

