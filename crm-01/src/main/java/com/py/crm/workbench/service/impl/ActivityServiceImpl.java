package com.py.crm.workbench.service.impl;

import com.py.crm.utils.SqlSessionUtil;
import com.py.crm.workbench.dao.ActivityDao;
import com.py.crm.workbench.domain.Activity;
import com.py.crm.workbench.service.ActivityService;

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
}
