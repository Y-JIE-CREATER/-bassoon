package cn.LYJ.LoginServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.LYJ.pojo.User;
import cn.LYJ.service.LoginService;
import cn.LYJ.service.impl.LoginServiceImpl;
/**
 * 此servlet用于校验浏览器是否携带所需的cookie信息，并校验是否正确
 * 
 * 如果携带所需的cookie信息，进行校验，
 * 		正确----->请求转发至主页面
 * 		错误----->请求转发至登录页面
 * 
 * 如果未携带所需的cookie信息，则请求转发至登录页面
 * 
 * 
 * @author Lsir
 *
 */
public class CookieServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//设置请求编码格式
		req.setCharacterEncoding("utf-8");
		//设置响应编码格式
		resp.setContentType("text/html;charset=utf-8");
		//获取请求信息
			//获取cookie信息
			Cookie[] cookies = req.getCookies();
		//处理请求信息
			if(cookies!=null){
				/*注意，此处获取cookie信息后仍需要在数据库中进行校验，看是否存在此人。因为这个人可能在第二天的时候注销了这个账号
				但浏览器端cookie信息还是储存着这个cookie信息。总不能一进入网页就进入登录状态吧*/
				//遍历cookie信息
				String uid="";
				for(Cookie cookie:cookies){
					if("uid".equals(cookie.getName())){
						uid = cookie.getValue();
					}
				}//如果遍历一遍之后uid的值还是没有获取到，即浏览器端存在cookie值，但是没有此网站所需的cookie值
				if(uid.equals("")){
					//请求转发
					req.getRequestDispatcher("page").forward(req, resp);
					return;
				}else{
					//校验UID用户信息
						//获取业务层对象
						LoginService ls = new LoginServiceImpl();
						User u = ls.checkUidLogin(uid);
						if(u!=null){
							//登陆成功，直接重定向
							
							//不管用户以哪种方式进入了主页面，页面到达主页面之前都得有用户的session对象
							req.getSession().setAttribute("user", u);
							resp.sendRedirect("/login/main");
							return;
						}else {
							//请求转发
							req.getRequestDispatcher("page").forward(req, resp);;
							return;
						}
				}
				
				
			}else {
				//请求转发
				req.getRequestDispatcher("page").forward(req, resp);;
				return;
			}
		//响应处理结果
	}
}
