package listners;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

//@WebListener
public class ContextListener implements ServletContextListener {

    @Resource(name = "jdbc:comp/env/jdbc/pos")
    public static DataSource ds;
//    private static Connection connection;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Context Initialized");
        /*try {
            getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

        /*BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/JavaEE_POS");
        bds.setUsername("root");
        bds.setPassword("shiny1234");
        bds.setMaxTotal(100);
        bds.setInitialSize(100);

        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("bds",bds);*/

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Context Destroyed...");
        /*try {
         *//*ServletContext servletContext = servletContextEvent.getServletContext();
            BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");
            bds.close();*//*

            ServletContext ctx = servletContextEvent.getServletContext();
            String name = ctx.getServletContextName();
            System.out.println(name);
            connection.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

    }
}
