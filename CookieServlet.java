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
 * ��servlet����У��������Ƿ�Я�������cookie��Ϣ����У���Ƿ���ȷ
 * 
 * ���Я�������cookie��Ϣ������У�飬
 * 		��ȷ----->����ת������ҳ��
 * 		����----->����ת������¼ҳ��
 * 
 * ���δЯ�������cookie��Ϣ��������ת������¼ҳ��
 * 
 * 
 * @author Lsir
 *
 */
public class CookieServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//������������ʽ
		req.setCharacterEncoding("utf-8");
		//������Ӧ�����ʽ
		resp.setContentType("text/html;charset=utf-8");
		//��ȡ������Ϣ
			//��ȡcookie��Ϣ
			Cookie[] cookies = req.getCookies();
		//����������Ϣ
			if(cookies!=null){
				/*ע�⣬�˴���ȡcookie��Ϣ������Ҫ�����ݿ��н���У�飬���Ƿ���ڴ��ˡ���Ϊ����˿����ڵڶ����ʱ��ע��������˺�
				���������cookie��Ϣ���Ǵ��������cookie��Ϣ���ܲ���һ������ҳ�ͽ����¼״̬��*/
				//����cookie��Ϣ
				String uid="";
				for(Cookie cookie:cookies){
					if("uid".equals(cookie.getName())){
						uid = cookie.getValue();
					}
				}//�������һ��֮��uid��ֵ����û�л�ȡ������������˴���cookieֵ������û�д���վ�����cookieֵ
				if(uid.equals("")){
					//����ת��
					req.getRequestDispatcher("page").forward(req, resp);
					return;
				}else{
					//У��UID�û���Ϣ
						//��ȡҵ������
						LoginService ls = new LoginServiceImpl();
						User u = ls.checkUidLogin(uid);
						if(u!=null){
							//��½�ɹ���ֱ���ض���
							
							//�����û������ַ�ʽ��������ҳ�棬ҳ�浽����ҳ��֮ǰ�������û���session����
							req.getSession().setAttribute("user", u);
							resp.sendRedirect("/login/main");
							return;
						}else {
							//����ת��
							req.getRequestDispatcher("page").forward(req, resp);;
							return;
						}
				}
				
				
			}else {
				//����ת��
				req.getRequestDispatcher("page").forward(req, resp);;
				return;
			}
		//��Ӧ������
	}
}
