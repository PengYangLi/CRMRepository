package com.py.crm.workbench.web.controller;

import com.py.crm.settings.domain.User;
import com.py.crm.settings.service.UserService;
import com.py.crm.settings.service.impl.UserServiceImpl;
import com.py.crm.utils.*;
import com.py.crm.vo.PaginationVO;
import com.py.crm.workbench.domain.Activity;
import com.py.crm.workbench.service.ActivityService;
import com.py.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(path)){

            getUserList(req,resp);

        }else if ("/workbench/activity/save.do".equals(path)){

            save(req,resp);

        }else if ("/workbench/activity/pageList.do".equals(path)){
            pageList(req,resp);
        }

    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        //进入到市场活动列表的操作（结合条件查询和分页查询）
        String name = req.getParameter("name");
        String owner = req.getParameter("owner");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String pageNoStr = req.getParameter("pageNo");
        int pageNo = Integer.parseInt(pageNoStr);
        String pageSizeStr = req.getParameter("pageSize");
        int pageSize = Integer.parseInt(pageSizeStr);
        int skipCount = (pageNo-1)*pageSize;

        //封装数据
        Map<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        //创建代理类
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        /*
        *   前端要数据总条数和市场活动信息列表，业务层拿到后用VO类来做封装
        *
        *   PaginationVO<T>
        * */
        //调用pageList方法
        PaginationVO<Activity> vo = as.pageList(map);
        PrintJson.printJsonObj(resp,vo);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {

        String id = UUIDUtil.getUUID();
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        String startDate = req.getParameter("startDate");
        String endDate = req.getParameter("endDate");
        String cost = req.getParameter("cost");
        String description = req.getParameter("description");
        //获取当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //获取创建人
        User user = (User) req.getSession().getAttribute("user");
        String createBy = user.getName();

        //创建对象封装数据
        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        //创建代理类
        ActivityService as = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        boolean flag = as.save(activity);

        PrintJson.printJsonFlag(resp,flag);
    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {
        //创建代理类
        UserService us  = (UserService) ServiceFactory.getService(new UserServiceImpl());

        List<User> ulist = us.getUserList();

        PrintJson.printJsonObj(resp,ulist);
    }
}
