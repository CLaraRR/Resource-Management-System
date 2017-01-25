package servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	// 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";
    
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadServlet() {
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
		
		// 配置上传参数
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// 设置临时存储目录
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		 
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		         
		// 设置最大文件上传值
		upload.setFileSizeMax(MAX_FILE_SIZE);
		         
		// 设置最大请求值 (包含文件和表单数据)
		upload.setSizeMax(MAX_REQUEST_SIZE);
		
		// 构造临时路径来存储上传的文件
		// 这个路径相对当前应用的目录
		String uploadPath = getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY+ File.separator;   
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request); 
		String dirName=null;
		// 检测是否为多媒体上传
		if(isMultipart){
			try {
			
				// 解析请求的内容提取文件数据
				List<FileItem> formItems = upload.parseRequest(request);
			 
				if (formItems != null && formItems.size() > 0) {
					// 迭代表单数据
					for (FileItem item : formItems) {
						// 处理在表单中的字段
						if(item.isFormField()){
							// Field name  
							Map<String,String> params = new HashMap<String,String>();  
	                        String name = item.getFieldName();  
							String value=item.getString("utf-8");
							params.put(name, value.trim());  
							dirName = (String)params.get("taskname");
							uploadPath+=dirName;
							//System.out.println(uploadPath);
							
							// 如果目录不存在则创建
							File uploadDir = new File(uploadPath);
							if (!uploadDir.exists()) {
								uploadDir.mkdir();
							}
						}
						else{ 
							
							String fileName = new File(item.getName()).getName();
                            fileName=codeString(fileName);
							//fileName = new String(fileName.getBytes("ISO-8859-1"),"UTF-8").toString();
                            System.out.println(fileName);
			                String filePath = uploadPath + File.separator +fileName;
			                
			                File storeFile = new File(filePath);
			                
			                // 在控制台输出文件的上传路径
			                // 保存文件到硬盘
			                
			             // 获得流，读取数据写入文件
			                InputStream in = item.getInputStream();
			                FileOutputStream fos = new FileOutputStream(storeFile);
			                
			                int len;
			                byte[] buffer = new byte[1024];
			                while((len=in.read(buffer))>0){
			                    
			                	fos.write(buffer,0,len);
			                }
			                   
			                fos.close();
			                in.close();
			                item.delete();    // 删除临时文件
			                
			                request.setAttribute("message","文件上传成功!");
			               }

					}
				}
			} catch (Exception ex) {
				request.setAttribute("message","错误信息: " + ex.getMessage());
			}
		}

		
		// 跳转到 message.jsp
		getServletContext().getRequestDispatcher("/student/message.jsp").forward(request, response);
	}


	
	//处理中文字符串的函数  
    public String codeString(String str){  
        String s = str;  
        try {  
            byte[] temp = s.getBytes("utf-8");  
            s = new String(temp).toString();  
            return s ;  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            return s;  
        }  
    }  
}
