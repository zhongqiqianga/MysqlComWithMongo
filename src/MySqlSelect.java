
import java.sql.*;

/**
 * @author Dgd
 * @create 2017-10-16-21:25
 * 测试mysql数据库插入千万级数据
 */
public class MySqlSelect {
    //mysql驱动包名
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    //数据库连接地址
    private static final String URL = "jdbc:mysql://localhost:3306/db?serverTimezone=GMT%2B8&useSSL=false";
    //用户名
    private static final String USER_NAME = "root";
    //密码
    private static final String PASSWORD = "SQT1800405089";

    public static void main(String[] args) {
        Connection connection = null;
        try {
            //加载mysql的驱动类
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            connection.setAutoCommit(false); // 设置手动提交
            Long startTime = System.currentTimeMillis();
            //mysql查询语句
            String sql1 = "SELECT fullId FROM work3 where bald=\"5ad41947eba49e00026660000\" and  concept=\"company\" and fullId =\"5ad41947eba49e0007c12332N0KK26660000\"";
            PreparedStatement prst = connection.prepareStatement(sql1);
            //结果集
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                System.out.println("fullId:" + rs.getString("fullId"));
            }
            Long endTime = System.currentTimeMillis();
            System.out.println("OK,用时：" + (endTime - startTime) + "毫秒");
            rs.close();
            prst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
