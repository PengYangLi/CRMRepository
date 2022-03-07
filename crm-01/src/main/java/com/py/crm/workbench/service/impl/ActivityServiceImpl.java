package com.py.crm.workbench.service.impl;

import com.py.crm.settings.dao.UserDao;
import com.py.crm.settings.domain.User;
import com.py.crm.utils.SqlSessionUtil;
import com.py.crm.vo.PaginationVO;
import com.py.crm.workbench.dao.ActivityDao;
import com.py.crm.workbench.dao.ActivityRemarkDao;
import com.py.crm.workbench.domain.Activity;
import com.py.crm.workbench.domain.ActivityRemark;
import com.py.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);


    @Override
    public boolean save(Activity activity) {
        //打标记
        boolean flag = true;
        //调用dao层
        int count = activityDao.save(activity);

        if (count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        //取得总条数
        int total = activityDao.getTotalByCondition(map);

        //取得dataList
        List<Activity> dataList = activityDao.getActivityByCondition(map);

        //封装数据到VO
        PaginationVO<Activity> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        //打标记
        boolean flag = true;

        //查询出需要删除备注的总数
        int count1 = activityRemarkDao.getCountByIds(ids);

        //删除备注，返回实际删除的数量
        int count2 = activityRemarkDao.deleteByIds(ids);

        if (count1!=count2) {
            flag = false;
        }

        //删除市场活动，返回实际删除的数量
        int count = activityDao.delete(ids);

        if (count != ids.length){
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getUserListActivity(String id) {

        //取userList
        List<User> userList = userDao.getUserList();

        //取activity
        Activity activity = activityDao.getById(id);

        //打包数据
        Map<String,Object> map = new HashMap<>();
        map.put("userList",userList);
        map.put("activity",activity);

        return map;
    }

    @Override
    public boolean update(Activity activity) {
        //打标记
        boolean flag = true;
        //调用dao层
        int count = activityDao.update(activity);

        if (count != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {

        Activity activity = activityDao.detail(id);

        return activity;
    }

    @Override
    public List<ActivityRemark> getRemarkListByAid(String activityId) {

        List<ActivityRemark> activityRemarkList = activityRemarkDao.getRemarkListByAid(activityId);
        return activityRemarkList;
    }
}
