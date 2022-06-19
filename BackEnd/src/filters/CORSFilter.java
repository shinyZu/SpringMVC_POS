package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

//@WebFilter(urlPatterns = "/*")
@WebFilter(urlPatterns = {"/customer", "/item", "/orders"})
public class CORSFilter implements Filter {

    /*private String currentLoggedEmail;
    private String currentLoggedPwd;*/

    public CORSFilter() {
//        System.out.println("Object created from CORSFilter");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        System.out.println("CORS Filter initialized");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        servletResponse.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");

        /*String userEmailRegEx = "^[a-z|0-9]{2,}@(gmail)(.com|.lk)$"; //admin@gmail.com, cashier1@gmail.lk
        String userPwdRegEx = "^[a-z]*[0-9]{3,}$"; // admin123, cashier123

        String email = servletRequest.getParameter("email");
        String pwd = servletRequest.getParameter("pwd");

        boolean isValidEmail = Pattern.compile(userEmailRegEx).matcher(email).matches();
        boolean isValidPwd = Pattern.compile(userPwdRegEx).matcher(pwd).matches();

        if (isValidEmail && isValidPwd) {
            currentLoggedEmail = email;
            currentLoggedPwd = pwd;
            System.out.println("currentLoggedEmail :"+currentLoggedEmail);
            System.out.println("currentLoggedPwd :"+currentLoggedPwd);

            filterChain.doFilter(servletRequest, servletResponse);

        } else {
            resp.sendRedirect(req.getRequestURI());
        }*/

        filterChain.doFilter(servletRequest, servletResponse);

        if (req.getMethod().equals("OPTIONS")) {
            resp.addHeader("Access-Control-Allow-Methods", "DELETE,PUT");
            resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        }
    }

    @Override
    public void destroy() {
        System.out.println("CORS Filter destroyed");
    }
}
