package com.py.crm.workbench.service.impl;

import com.py.crm.utils.SqlSessionUtil;
import com.py.crm.vo.PaginationVO;
import com.py.crm.workbench.dao.ActivityDao;
import com.py.crm.workbench.domain.Activity;
import com.py.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);


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
}
