package com.py.crm.workbench.service.impl;

import com.py.crm.utils.SqlSessionUtil;
import com.py.crm.workbench.dao.ActivityDao;
import com.py.crm.workbench.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);


}
