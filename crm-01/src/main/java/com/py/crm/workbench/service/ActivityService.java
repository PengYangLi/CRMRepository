package com.py.crm.workbench.service;

import com.py.crm.vo.PaginationVO;
import com.py.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {
    boolean save(Activity activity);

    PaginationVO<Activity> pageList(Map<String, Object> map);
}
