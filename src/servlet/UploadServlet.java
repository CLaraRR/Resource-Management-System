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
       
	
	// �ϴ��ļ��洢Ŀ¼
    private static final String UPLOAD_DIRECTORY = "upload";
    
    // �ϴ�����
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
		
		// �����ϴ�����
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		// ������ʱ�洢Ŀ¼
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		 
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		         
		// ��������ļ��ϴ�ֵ
		upload.setFileSizeMax(MAX_FILE_SIZE);
		         
		// �����������ֵ (�����ļ��ͱ�����)
		upload.setSizeMax(MAX_REQUEST_SIZE);
		
		// ������ʱ·�����洢�ϴ����ļ�
		// ���·����Ե�ǰӦ�õ�Ŀ¼
		String uploadPath = getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY+ File.separator;   
		
		boolean isMultipart = ServletFileUpload.isMultipartContent(request); 
		String dirName=null;
		// ����Ƿ�Ϊ��ý���ϴ�
		if(isMultipart){
			try {
			
				// ���������������ȡ�ļ�����
				List<FileItem> formItems = upload.parseRequest(request);
			 
				if (formItems != null && formItems.size() > 0) {
					// ����������
					for (FileItem item : formItems) {
						// �����ڱ��е��ֶ�
						if(item.isFormField()){
							// Field name  
							Map<String,String> params = new HashMap<String,String>();  
	                        String name = item.getFieldName();  
							String value=item.getString("utf-8");
							params.put(name, value.trim());  
							dirName = (String)params.get("taskname");
							uploadPath+=dirName;
							//System.out.println(uploadPath);
							
							// ���Ŀ¼�������򴴽�
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
			                
			                // �ڿ���̨����ļ����ϴ�·��
			                // �����ļ���Ӳ��
			                
			             // ���������ȡ����д���ļ�
			                InputStream in = item.getInputStream();
			                FileOutputStream fos = new FileOutputStream(storeFile);
			                
			                int len;
			                byte[] buffer = new byte[1024];
			                while((len=in.read(buffer))>0){
			                    
			                	fos.write(buffer,0,len);
			                }
			                   
			                fos.close();
			                in.close();
			                item.delete();    // ɾ����ʱ�ļ�
			                
			                request.setAttribute("message","�ļ��ϴ��ɹ�!");
			               }

					}
				}
			} catch (Exception ex) {
				request.setAttribute("message","������Ϣ: " + ex.getMessage());
			}
		}

		
		// ��ת�� message.jsp
		getServletContext().getRequestDispatcher("/student/message.jsp").forward(request, response);
	}


	
	//���������ַ����ĺ���  
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
