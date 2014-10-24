/**
 * 
 * @author Geloin
 * @date Oct 22, 2014 2:20:00 PM
 */
package me.geloin.door.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 获取服务地址
 * 
 * @author Geloin
 * @date Oct 22, 2014 2:20:00 PM
 */
@SuppressWarnings("serial")
public class ContextTag extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		ServletRequest request = this.pageContext.getRequest();
		ServletContext context = this.pageContext.getServletContext();
		String ctx = "http://" + request.getServerName() + ":"
				+ request.getServerPort() + context.getContextPath() + "/";
		JspWriter out = this.pageContext.getOut();

		try {
			out.print(ctx);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

}
