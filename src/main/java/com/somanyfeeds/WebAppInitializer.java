package com.somanyfeeds;

import com.somanyfeeds.feeds.FeedUpdatesScheduler;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;

public class WebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.scan("com.somanyfeeds");
        ctx.setServletContext(servletContext);
        ctx.refresh();

        FeedUpdatesScheduler feedUpdatesScheduler = ctx.getBean(FeedUpdatesScheduler.class);
        feedUpdatesScheduler.start();

        servletContext.addListener(new ContextLoaderListener(ctx));

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
