import org.apache.ibatis.session.SqlSession;
import settings.domain.User;

import java.util.List;

public class Test {

    @org.junit.Test
    public void Test(){
        SqlSession session = SqlSessionUtil.getSqlSession();
        User dao = session.getMapper(User.class);

    }
}
