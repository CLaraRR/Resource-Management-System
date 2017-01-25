package servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import entity.Resource;
import entity.ResourceMgr;
import entity.Task;
import entity.TaskMgr;

/**
 * Servlet implementation class ResourceServlet
 */
@WebServlet("/ResourceServlet")
public class ResourceServlet extends HttpServlet {
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
    public ResourceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		processRequest(request,response);
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
		response.setContentType("text/html");
		
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
				
		boolean isMultipart = ServletFileUpload.isMultipartContent(request); 
		String dirName=null;
		// 检测是否为多媒体上传
		if(isMultipart){
			try {
				// 解析请求的内容提取文件数据
				List<FileItem> formItems = upload.parseRequest(request);
				HttpSession session = request.getSession();
				ResourceMgr resourceMgr = new ResourceMgr();
						
				if (formItems != null && formItems.size() > 0) {
					//首先判断第一个文本域operation的值
					FileItem item1=formItems.get(0);
					String operation=item1.getString("utf-8");
					//根据operation的值分情况讨论		
					if(operation.equals("add")){
						//把所有文本域的值都存到params的键值对里面
						Map<String,String> params = new HashMap<String,String>(); 
						for (FileItem item : formItems) {
							//如果只是普通的文本域
							if(item.isFormField()){
								String name = item.getFieldName();  
								String value=item.getString("utf-8");
								params.put(name, value.trim());
							}
							//如果是上传域
							else{
								//上传文件
								upload(params.get("resourcename"),item);
							}
							 
						}
						
						String resourcename=params.get("resourcename");
						String resourcedes=params.get("resourcedes");
						//String resourcedate=params.get("resourcedate");
						Date date=new Date();
						SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
						String resourcedate=ft.format(date);
						System.out.println(resourcedate);
						String resourceauthor=(String) session.getAttribute("usernum");
						System.out.println(resourceauthor);
						Resource resource = new Resource(resourcename,resourcedes,resourcedate,resourceauthor);
						if(resourceMgr.addResource(resource) ==2){
							request.getRequestDispatcher("/student/main.jsp").forward(request, response);
						}
						else if(resourceMgr.addResource(resource) ==1){
							request.getRequestDispatcher("/student/addResource.jsp").forward(request, response);
						}
						
					}
				}
			}catch (Exception ex) {
				ex.printStackTrace();
			}
		}	
		//如果只是普通的表单而不包含多媒体上传
		else{
			HttpSession session = request.getSession();
			ResourceMgr resourceMgr = new ResourceMgr();
					
			String operation=request.getParameter("operation");
			if(operation.equals("del")){
				String resourcenum=request.getParameter("resourcenum");
				//resourcenum = new String(resourcenum.getBytes("ISO-8859-1"), "UTF-8");
				if(resourceMgr.deleteResource(resourcenum)){
					request.getRequestDispatcher("/student/showResource.jsp").forward(request, response);
							
				}
				else{
					PrintWriter out = response.getWriter();
					out.println("<script> alert('删除不成功');</script>");
				}	
			}
			
			if(operation.equals("edit")){
				String resourcename = request.getParameter("resourcename");
				//resourcename = new String(resourcename.getBytes("ISO-8859-1"), "UTF-8");
				String resourcenum=request.getParameter("resourcenum");
				//resourcenum = new String(resourcenum.getBytes("ISO-8859-1"), "UTF-8");
				String resourcedate = request.getParameter("resourcedate");
				//resourcedate = new String(resourcedate.getBytes("ISO-8859-1"), "UTF-8");
				String resourcedes = request.getParameter("resourcedes");
				//resourcedes = new String(resourcedes.getBytes("ISO-8859-1"), "UTF-8");
				String resourceauthor=(String) session.getAttribute("usernum");
				System.out.println(resourceauthor);
				Resource resource = new Resource(Integer.parseInt(resourcenum),resourcename,resourcedes,resourcedate,resourceauthor);
					if(resourceMgr.editResource(resource) ==2){
						request.getRequestDispatcher("/student/showResource.jsp").forward(request, response);    
					}
					else if(resourceMgr.editResource(resource) ==1){
						request.getRequestDispatcher("/student/editResource.jsp?resourcenum=" + resourcenum).forward(request, response);
					}
			}
						
		}
	}


	private void upload(String dirname,FileItem item) throws IOException {
		// TODO Auto-generated method stub
						
		// 构造临时路径来存储上传的文件
		// 这个路径相对当前应用的目录
		String uploadPath = getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY+ File.separator; 
		uploadPath+=dirname;
		// 如果目录不存在则创建
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		String fileName = new File(item.getName()).getName();
		// fileName=codeString(fileName);
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
	}

}
