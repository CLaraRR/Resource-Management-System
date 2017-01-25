package servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String FilePath = "D:\\"; 

	
	// 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";
    
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		processRequest(request,response);
	}
	
	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String uploadPath = getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY+ File.separator;   
		String dirname = request.getParameter("name");  
		System.out.println(dirname);
		//dirname=new String(dirname.getBytes("ISO-8859-1"),"UTF-8").toString();
		uploadPath+=dirname;
		
		//System.out.println(uploadPath);
		
        //得到想客服端输出的输出流  
        OutputStream outputStream = response.getOutputStream();  
        //输出文件用的字节数组，每次向输出流发送600个字节  
        byte b[] = new byte[600];  
        //要下载的文件  
        File file = new File(uploadPath);   
        
        //生成的ZIP文件名为Demo.zip    
        String tmpFileName = dirname+".zip"; 
        String strZipPath = FilePath + tmpFileName;
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(    
                strZipPath));
        if(file.isDirectory()){
        	//System.out.println("文件夹");
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
            	File fileload = new File(uploadPath + "\\" + filelist[i]);
            	System.out.println(fileload);
            	FileInputStream fis = new FileInputStream(fileload);    
                out.putNextEntry(new ZipEntry(fileload.getName()));    
                //设置压缩文件内的字符编码，不然会变成乱码   
                
                int len;    
                // 读入需要下载的文件的内容，打包到zip文件    
                while ((len = fis.read(b)) > 0) {    
                    out.write(b, 0, len);    
                }    
                out.closeEntry();    
                fis.close();   
            }
            
            out.close();    
            this.downFile(response, tmpFileName); 
        	 
        }
        else{
        	
        }
       
	}
	
	
	
	 /**   
     * 文件下载   
     * @param response   
     * @param str   
     */    
    private void downFile(HttpServletResponse response, String str) {    
        try {    
            String path = FilePath + str;    
            File file = new File(path);    
            if (file.exists()) {    
                InputStream ins = new FileInputStream(path);    
                BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面    
                OutputStream outs = response.getOutputStream();// 获取文件输出IO流    
                BufferedOutputStream bouts = new BufferedOutputStream(outs);    
                response.setContentType("application/x-download");// 设置response内容的类型    
                response.setHeader(    
                        "Content-disposition",    
                        "attachment;filename="    
                                + URLEncoder.encode(str, "UTF-8"));// 设置头部信息    
                int bytesRead = 0;    
                byte[] buffer = new byte[8192];    
                // 开始向网络传输文件流    
                while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {    
                    bouts.write(buffer, 0, bytesRead);    
                }    
                bouts.flush();// 这里一定要调用flush()方法    
                ins.close();    
                bins.close();    
                outs.close();    
                bouts.close();    
            } else {    
                response.sendRedirect("../error.jsp");    
            }    
        } catch (IOException e) {    
            e.printStackTrace();   
        }    
    }    

}
