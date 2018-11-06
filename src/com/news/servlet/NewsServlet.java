package com.news.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.realm.NestedCredentialHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.coyote.http11.filters.VoidInputFilter;

import com.alibaba.fastjson.JSON;
import com.news.model.Comment;
import com.news.model.News;
import com.news.model.Page;
import com.news.model.Topic;
import com.news.service.CommentsService;
import com.news.service.NewsService;
import com.news.service.TopicService;
import com.news.service.impl.CommentsServiceImpl;
import com.news.service.impl.NewsServiceImpl;
import com.news.service.impl.TopicServiceImpl;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import com.sun.prism.ReadbackRenderTarget;
@WebServlet("/news.do")
public class NewsServlet extends HttpServlet{
	private NewsService newsService = new NewsServiceImpl();
	private TopicService topicService = new TopicServiceImpl();
	private CommentsService commentsService = new CommentsServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.请求编码
		req.setCharacterEncoding("UTF-8");
		//2.响应格式
		resp.setContentType("text/html;charset=utf-8");
		//3.接受参数
		String opr = req.getParameter("opr");
		switch (opr) {
		case "list":
			getAllNews(req,resp);
			break;
		case "addtopic":{
			addNews(req,resp);
			break;
		}
		case "toAddNews":{
			toAddNews(req,resp);
			break;
		}
		case "addNews":{
			addNews(req,resp);
			break;
		}
		case "getNewsByTid":{
			getNewsByTid(req,resp);
			break;
		}
		case "getNewsCenter":{
			getNewsCenter(req,resp);
			break;
		}
		case "delete": {
			delNews(req, resp);
			break;
		}
		case "toModifyNews":{
			toModifyNews(req,resp);
			break;
		}
		case "modifyNews":{
			modifyNews(req,resp);
			break;
		}
		case "getNewsLimitByNtid":{
			getNewsLimitByNtid(req,resp);
			break;
		}
		case "readNew":{
			readNew(req,resp);
			break;
		}
		default:
			break;
		}
	}
	//加载新闻和评论
	public void readNew(HttpServletRequest req, HttpServletResponse resp){
		System.out.println("readNew");
		int nid = Integer.parseInt(req.getParameter("nid"));
		try {
			News news = newsService.findNewsByNid(nid);
			news.setComments(commentsService.findCommentsByNid(nid));
			req.setAttribute("news", news);
			req.getRequestDispatcher("newspages/news_read.jsp").forward(req, resp);
		} catch (NumberFormatException | SQLException | ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//分页获取新闻
	public void getNewsLimitByNtid(HttpServletRequest req, HttpServletResponse resp){
		System.out.println("getNewsLimitByNtid");
		//1.前端传来数据
		int ntid = Integer.parseInt(req.getParameter("ntid"));//主题id
		int index = Integer.parseInt(req.getParameter("index"));//第几页
		int num = Integer.parseInt(req.getParameter("num"));//每页数量
		try {
		//2.servlet 封装Page
		Page<News> page = new Page<News>();
		page.setCurrPageNo(index);//第几页
		page.setPageSize(num);//每页数量
		//3.调用service
			newsService.findPageNews(ntid, page);
			for (News news : page.getNewsList()) {
				System.out.println("nid"+news.getNid());
			}
		//4.展示Page 转换JSON      
		String json = JSON.toJSONString(page);
			PrintWriter out = resp.getWriter();
			out.write(json);
			out.close();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//加载中间页面
	public void getNewsCenter(HttpServletRequest req, HttpServletResponse resp){
		try {
			System.out.println("getNewsCenter");
			//1.查询所有新闻分类
			List<Topic> topics = topicService.getAll();
			//2.查询所有新闻
			List<News> news = newsService.findAllNews();
			//3.Map存储数据
			Map<String, List<?>> maps =  new HashMap<String, List<?>>();
			maps.put("topicsList", topics);
			maps.put("newsList", news);
			String Topnews = JSON.toJSONString(maps);//通过JSON发个页面
			//响应JSON数据
			PrintWriter out = resp.getWriter();
			out.write(Topnews);
			out.close();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//通过主题id查询指定新闻
	public void getNewsByTid(HttpServletRequest req, HttpServletResponse resp){
		try {
			int ntid = Integer.parseInt(req.getParameter("ntid"));//获取页面上的值
			int num = Integer.parseInt(req.getParameter("num"));
			System.out.println("getNewsByTid"+ntid+"|"+num);
			List<News> list = newsService.findLatestNewsByTid(ntid, num);
			String newsJson = JSON.toJSONString(list);//通过JSON发个页面
			//响应JSON数据
			PrintWriter out = resp.getWriter();
			out.write(newsJson);
			out.close();
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//跳转到添加新闻页面
	public void toAddNews(HttpServletRequest req, HttpServletResponse resp){
		System.out.println("toAddNews");
		//查询所有主题
		TopicService topicService = new TopicServiceImpl();
		req.setAttribute("topics", topicService.getAll()); 
		//转发
		try {
			req.getRequestDispatcher("newspages/news_add.jsp").forward(req, resp);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//新增新闻
	public void addNews(HttpServletRequest req, HttpServletResponse resp){
		System.out.println("addNews");
		News news = new News();
		//处理路径
		//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
        //上传时生成的临时文件保存目录
        String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
        File tmpFile = new File(tempPath);
        if (!tmpFile.exists()) {
            //创建临时目录
            tmpFile.mkdir();
        }
        //处理数据
        try{
        	//1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
            factory.setSizeThreshold(1024*100);//设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
            //设置上传时生成的临时文件的保存目录
            factory.setRepository(tmpFile);
            //2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            //监听文件上传进度
            upload.setProgressListener(new ProgressListener(){
                public void update(long pBytesRead, long pContentLength, int arg2) {
                    System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
                    /**
                     * 文件大小为：14608,当前已处理：4096
                        文件大小为：14608,当前已处理：7367
                        文件大小为：14608,当前已处理：11419
                        文件大小为：14608,当前已处理：14608
                     */
                }
            });
             //解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8"); 
          //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
            upload.setFileSizeMax(1024*1024);
            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
            upload.setSizeMax(1024*1024*10);
            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(req);
            for(FileItem item : list){
                //如果fileitem中封装的是普通输入项的数据
                if(item.isFormField()){                        	
                    String fieldName = item.getFieldName();
                    //解决普通输入项的数据的中文乱码问题
                    if (fieldName.equals("ntid")) {   //主题id
                        news.setNtid(Integer.parseInt(item
                                            .getString("UTF-8")));
                    } else if (fieldName.equals("ntitle")) { //标题
                        news.setNtitle(item.getString("UTF-8"));
                    } else if (fieldName.equals("nauthor")) {//作者
                        news.setNauthor(item.getString("UTF-8"));
                    } else if (fieldName.equals("nsummary")) { //摘要
                        news.setNsummary(item.getString("UTF-8"));
                    } else if (fieldName.equals("ncontent")) { //内容
                        news.setNcontent(item.getString("UTF-8"));
                    }
                }else{//如果fileitem中封装的是上传文件
                    //得到上传的文件名称，
                    String filename = item.getName();
                    System.out.println(filename);
                    
                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\")+1);
                    //得到上传文件的扩展名
                    String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
                    //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                    System.out.println("上传的文件的扩展名是："+fileExtName);
                    //获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    //得到文件保存的名称
                    String saveFilename = makeFileName(filename);
                    //得到文件的保存目录
                    String realSavePath = makePath(saveFilename, savePath);
                    //创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(realSavePath + "\\" + saveFilename);
                    //创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    //判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while((len=in.read(buffer))>0){
                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                        out.write(buffer, 0, len);
                    }
                    //关闭输入流
                    in.close();
                    //关闭输出流
                    out.close();
                    //删除处理文件上传时生成的临时文件
                    item.delete();
                    news.setNpicpath(saveFilename);
                }  
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
		
		
		//新增新闻
		try {
			if(newsService.addNews(news)==1){
				resp.sendRedirect("news.do?opr=list");
			}else{
				PrintWriter out = resp.getWriter();
				out.println("添加新闻失败！");
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = null;
			try {
				out = resp.getWriter();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			out.println("添加新闻失败！");
			out.close();
		}
		
	}
	//查询所有
	public void getAllNews(HttpServletRequest req, HttpServletResponse resp){
		System.out.println("listNews");
		try {
			List<News> list = newsService.findAllNews();
			//转发
			req.setAttribute("list", list);
			req.getRequestDispatcher("newspages/admin.jsp").forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	    * @Method: makeFileName
	    * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	    * @param filename 文件的原始名称
	    * @return uuid+"_"+文件的原始名称
	    */ 
	    private String makeFileName(String filename){  //2.jpg
	        //为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
	        return UUID.randomUUID().toString() + "_" + filename;
	    }
	    
	    /**
	     * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
	    * @Method: makePath
	    * @param filename 文件名，要根据文件名生成存储目录
	    * @param savePath 文件存储路径
	    * @return 新的存储目录
	    */ 
	    private String makePath(String filename,String savePath){
	        //得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
	        int hashcode = filename.hashCode();
	        int dir1 = hashcode&0xf;  //0--15
	        int dir2 = (hashcode&0xf0)>>4;  //0-15
	        //构造新的保存目录
	        String dir = savePath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
	        //File既可以代表文件也可以代表目录
	        File file = new File(dir);
	        //如果目录不存在
	        if(!file.exists()){
	            //创建目录
	            file.mkdirs();
	        }
	        return dir;
	    }
	 // 删除新闻
		public void delNews(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
			System.out.println("delNews");
			int nid=Integer.parseInt(req.getParameter("nid"));
			try {
				PrintWriter out = resp.getWriter();
			if(newsService.deleteNews(nid) == 1){
				//resp.sendRedirect("./news.do?opr=list");
				List<News> newsst = newsService.findAllNews();
				String newsjs = JSON.toJSONString(newsst);
				out.write(newsjs);
			}else{
				out.print("删除失败！");
			}
			out.close();
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
		}
		/**
		 * 转发修改页面
		 */
		public void toModifyNews(HttpServletRequest req,HttpServletResponse resp){
			System.out.println("toModifyNews");
			//放入新闻对象
			try {
			int nid = Integer.parseInt(req.getParameter("nid"));
			News news = newsService.findNewsByNid(nid);
			news.setComments(commentsService.findCommentsByNid(nid));
			req.setAttribute("news", news);
			//放入主题集合
			TopicService topicService = new TopicServiceImpl();
			List<Topic> list = topicService.getAll();
			req.setAttribute("topics", list);
				req.getRequestDispatcher("./newspages/news_modify.jsp").forward(req, resp);
			} catch ( IOException | ServletException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/**
		 * 修改新闻
		 */
		public void modifyNews(HttpServletRequest req, HttpServletResponse resp){
			System.out.println("modifyNews");
			News news = new News();
			//处理路径
			//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
	        String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
	        //上传时生成的临时文件保存目录
	        String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
	        File tmpFile = new File(tempPath);
	        if (!tmpFile.exists()) {
	            //创建临时目录
	            tmpFile.mkdir();
	        }
	        //处理数据
	        try{
	        	//1、创建一个DiskFileItemFactory工厂
	            DiskFileItemFactory factory = new DiskFileItemFactory();
	            //设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
	            factory.setSizeThreshold(1024*100);//设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
	            //设置上传时生成的临时文件的保存目录
	            factory.setRepository(tmpFile);
	            //2、创建一个文件上传解析器
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            //监听文件上传进度
	            upload.setProgressListener(new ProgressListener(){
	                public void update(long pBytesRead, long pContentLength, int arg2) {
	                    System.out.println("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
	                    /**
	                     * 文件大小为：14608,当前已处理：4096
	                        文件大小为：14608,当前已处理：7367
	                        文件大小为：14608,当前已处理：11419
	                        文件大小为：14608,当前已处理：14608
	                     */
	                }
	            });
	             //解决上传文件名的中文乱码
	            upload.setHeaderEncoding("UTF-8"); 
	          //设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
	            upload.setFileSizeMax(1024*1024);
	            //设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
	            upload.setSizeMax(1024*1024*10);
	            //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
	            List<FileItem> list = upload.parseRequest(req);
	            for(FileItem item : list){
	                //如果fileitem中封装的是普通输入项的数据
	                if(item.isFormField()){                        	
	                    String fieldName = item.getFieldName();
	                    //解决普通输入项的数据的中文乱码问题
	                    if (fieldName.equals("nid")) {   //id号
	                        news.setNid(Integer.parseInt(item
	                                            .getString("UTF-8")));
	                    }else if (fieldName.equals("ntid")) { //主题id
	                    	news.setNtid(Integer.parseInt(item
                                    .getString("UTF-8")));
	                    }else if (fieldName.equals("ntitle")) { //标题
	                        news.setNtitle(item.getString("UTF-8"));
	                    } else if (fieldName.equals("nauthor")) {//作者
	                        news.setNauthor(item.getString("UTF-8"));
	                    } else if (fieldName.equals("nsummary")) { //摘要
	                        news.setNsummary(item.getString("UTF-8"));
	                    } else if (fieldName.equals("ncontent")) { //内容
	                        news.setNcontent(item.getString("UTF-8"));
	                    }
	                }else{//如果fileitem中封装的是上传文件
	                    //得到上传的文件名称，
	                    String filename = item.getName();
	                    System.out.println(filename);
	                    
	                    //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
	                    //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
	                    filename = filename.substring(filename.lastIndexOf("\\")+1);
	                    //得到上传文件的扩展名
	                    String fileExtName = filename.substring(filename.lastIndexOf(".")+1);
	                    //如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
	                    System.out.println("上传的文件的扩展名是："+fileExtName);
	                    //获取item中的上传文件的输入流
	                    InputStream in = item.getInputStream();
	                    //得到文件保存的名称
	                    String saveFilename = makeFileName(filename);
	                    //得到文件的保存目录
	                    String realSavePath = makePath(saveFilename, savePath);
	                    //创建一个文件输出流
	                    FileOutputStream out = new FileOutputStream(realSavePath + "\\" + saveFilename);
	                    //创建一个缓冲区
	                    byte buffer[] = new byte[1024];
	                    //判断输入流中的数据是否已经读完的标识
	                    int len = 0;
	                    //循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
	                    while((len=in.read(buffer))>0){
	                        //使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
	                        out.write(buffer, 0, len);
	                    }
	                    //关闭输入流
	                    in.close();
	                    //关闭输出流
	                    out.close();
	                    //删除处理文件上传时生成的临时文件
	                    item.delete();
	                    news.setNpicpath(saveFilename);
	                }  
	            }
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
			
			
			//新增新闻
			try {
				if(newsService.modifyNews(news)==1){
					resp.sendRedirect("news.do?opr=list");
				}else{
					PrintWriter out = resp.getWriter();
					out.println("修改新闻失败！");
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
				PrintWriter out = null;
				try {
					out = resp.getWriter();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				out.println("修改新闻失败！");
				out.close();
			}
			
		}
}
