package com.nuc.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nuc.pojo.Book;
import com.nuc.pojo.PageBean;
import com.nuc.pojo.User;
import com.nuc.service.BookService;
import com.nuc.service.UserService;
import com.nuc.util.ImageUtil;

@Controller
@SessionAttributes("sessionUser")
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;
	

	
	@RequestMapping("/user/login")
	public String Login() {
		return "user/login2";
	}
	
	@RequestMapping(value="/checkLogin",method=RequestMethod.POST)
	public String checkLogin(User user,Model model){
		user = userService.checkLogin(user.getLoginname(),user.getLoginpass());
		
		if(user==null){
			return "fail";
		}else {
			
			model.addAttribute("sessionUser", user);
			return "redirect:/./jsp/main.jsp";
		}
		
	}
	
    @RequestMapping("/user/regist")
    public String regist (){
    	return "user/regist";
    }
	
    
    
    /**
	 * 退出功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
    @RequestMapping("/quit")
	public String quit(HttpSession session) {
		session.invalidate();
		return "redirect:/jsp/user/login.jsp";
	}
    
    @RequestMapping("/user/updatePassword")
    /**
	 * 修改密码　
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatePassword(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到user中
		 * 2. 从session中获取uid
		 * 3. 使用uid和表单中的oldPass和newPass来调用service方法
		 *   > 如果出现异常，保存异常信息到request中，转发到pwd.jsp
		 * 4. 保存成功信息到rquest中
		 * 5. 转发到msg.jsp
		 */
		//User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		User user = (User)req.getSession().getAttribute("sessionUser");
		String newPass= req.getParameter("newpass");
		String loginpass= req.getParameter("loginpass");
		// 如果用户没有登录，返回到登录页面，显示错误信息
		if(user == null) {
			req.setAttribute("msg", "您还没有登录！");
			return "forward:/jsp/user/login.jsp";
		}
		
		userService.updatePassword(user.getUid(), newPass, 
				loginpass);
		req.setAttribute("msg", "修改密码成功");
		req.setAttribute("code", "success");
		return "msg";
//		else 
//		{
//			return "forward:/jsp/user/pwd.jsp";
//		}
	}
    
    /*
     * 注册功能
     * 验证登录名
     */
    @RequestMapping(value="ajaxValidateLoginname",method=RequestMethod.POST)
    public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取用户名
		 */
		String loginname = req.getParameter("loginname");
		/*
		 * 2. 通过service得到校验结果
		 */
		boolean b = userService.checkRegistName(loginname);
		/*
		 * 3. 发给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	
	/**
	 * ajax Email是否注册校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
    @RequestMapping(value="ajaxValidateEmail",method=RequestMethod.POST)
	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取Email
		 */
		String email = req.getParameter("email");
		/*
		 * 2. 通过service得到校验结果
		 */
		boolean b = userService.ajaxValidateEmail(email);
		/*
		 * 3. 发给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
  //验证码
  	@RequestMapping("/VerifyCodeServlet")
  	public void handleRnd(HttpServletRequest request,
  			HttpServletResponse response) throws Exception {
  		response.setHeader("Cache-Control", "no-store");
  		response.setHeader("Pragma", "no-cache");
  		response.setDateHeader("Expires", 0L);
  		response.setContentType("image/jpeg");
  		String aString= request.getParameter("a");
  		Integer a = null;
  		
			Random r = new Random();
	
  		BufferedImage image = new BufferedImage(65, 25,
  				BufferedImage.TYPE_INT_RGB);
  		Graphics g = image.getGraphics();
  		g.setColor(new Color(246,194,115));
  		g.fillRect(0, 0, 65, 25);
  		g.setColor(Color.yellow);
  		Font font = new Font("宋体", Font.BOLD, 20);
  		g.setFont(font);
  		
  		String rnd = "";
  		int ir = r.nextInt(10);
  		rnd = rnd + "" + ir;
  		g.drawString("" + ir, 5, 18);
  		g.setColor(Color.red);
  		ir = r.nextInt(10);
  		rnd = rnd + "" + ir;
  		g.drawString("" + ir, 20, 18);
  		g.setColor(Color.blue);
  		ir = r.nextInt(10);
  		rnd = rnd + "" + ir;
  		g.drawString("" + ir, 35, 18);
  		g.setColor(Color.green);
  		ir = r.nextInt(10);
  		rnd = rnd + "" + ir;
  		g.drawString("" + ir, 50, 18);
  		request.getSession().setAttribute("vCode", rnd);   //储存Session用于验证码判断
  		ServletOutputStream out = response.getOutputStream();
  		out.write(ImageUtil.imageToBytes(image, "gif"));
  		out.flush();
  		out.close();
  	}
  	
	/**
	 * ajax验证码是否正确校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
  	@RequestMapping("ajaxValidateVerifyCode")
	public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取输入框中的验证码
		 */
		String verifyCode = req.getParameter("verifyCode");
		/*
		 * 2. 获取图片上真实的校验码
		 */
		String vcode = (String) req.getSession().getAttribute("vCode");
		/*
		 * 3. 进行忽略大小写比较，得到结果
		 */
		boolean b = verifyCode.equalsIgnoreCase(vcode);
		/*
		 * 4. 发送给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	/**
	 * 注册功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
  	@RequestMapping(value="regist",method=RequestMethod.POST)
  	public String regist(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到User对象
		 */
  		User formUser =new User();
  		formUser.setLoginname(req.getParameter("loginname"));
  		formUser.setLoginpass(req.getParameter("loginpass"));
  		formUser.setReloginpass(req.getParameter("reloginpass"));
  		formUser.setEmail(req.getParameter("email"));
  		formUser.setVerifyCode(req.getParameter("verifyCode"));
		
		/*
		 * 2. 校验之, 如果校验失败，保存错误信息，返回到regist.jsp显示
		 */
		Map<String,String> errors = validateRegist(formUser, req.getSession());
		if(errors.size() > 0) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "forward:/jsp/user/regist.jsp";
		}
		/*
		 * 3. 使用service完成业务
		 */
		userService.doResgit(formUser);
		/*
		 * 4. 保存成功信息，转发到msg.jsp显示！
		 */
		req.setAttribute("code", "success");
		req.setAttribute("msg", "注册成功，请马上到邮箱激活！");
		return "forward:/jsp/msg.jsp";
	}
	
	/*
	 * 注册校验
	 * 对表单的字段进行逐个校验，如果有错误，使用当前字段名称为key，错误信息为value，保存到map中
	 * 返回map
	 */
	private Map<String,String> validateRegist(User formUser, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 1. 校验登录名
		 */
		String loginname = formUser.getLoginname();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if(!userService.checkRegistName(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}
		
		/*
		 * 2. 校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}
		
		/*
		 * 3. 确认密码校验
		 */
		String reloginpass = formUser.getReloginpass();
		if(reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}
		
		/*
		 * 4. 校验email
		 */
		String email = formUser.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email格式错误！");
		} else if(!userService.ajaxValidateEmail(email)) {
			errors.put("email", "Email已被注册！");
		}
		
		/*
		 * 5. 验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
		
		return errors;
	}
	
	/**
	 * 激活功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="activation",method=RequestMethod.GET)
	public String activation(@RequestParam("activationCode")String activationCode,HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取参数激活码
		 * 2. 用激活码调用service方法完成激活
		 *   > service方法有可能抛出异常, 把异常信息拿来，保存到request中，转发到msg.jsp显示
		 * 3. 保存成功信息到request，转发到msg.jsp显示。
		 */


			userService.activatioin(activationCode);
			req.setAttribute("activationCode", "success");//通知msg.jsp显示对号
			req.setAttribute("msg", "恭喜，激活成功，请马上登录！");
		
		return "forward:/jsp/msg.jsp";
	}
}
