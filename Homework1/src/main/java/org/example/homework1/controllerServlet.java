package org.example.homework1;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "controllerServlet", value = "/controller-servlet")
public class controllerServlet extends HttpServlet{


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");

        String method = request.getMethod();
        String IP = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String language = request.getHeader("Accept-Language");
        String pageParameter = request.getParameter("page");

        String serverLog = String.format("Method: %s, IP: %s, userAgent: %s, language: %s, pageParameter: %s",
                method, IP, userAgent, language, pageParameter);

        System.out.println(serverLog);
        getServletContext().log(serverLog);

        boolean isDesktopApplication;

        if(userAgent != null && (userAgent.contains("Java") || userAgent.contains("Python") || userAgent.contains(".NET") ))
        {
            isDesktopApplication = true;
        }
        else{
            isDesktopApplication = false;
        }


        if(isDesktopApplication){
            response.setContentType("text/plain");
            response.getWriter().print("Parameter value: " + pageParameter);
            return;
        }

        if("1".equals(pageParameter)){
            request.getRequestDispatcher("/page1.html").forward(request,response);
        }
        else if("2".equals(pageParameter)){
            request.getRequestDispatcher("/page2.html").forward(request,response);
        }
        else{
            response.getWriter().println("Te rog să selectezi opțiunea 1 sau 2!");
        }

    }


}

