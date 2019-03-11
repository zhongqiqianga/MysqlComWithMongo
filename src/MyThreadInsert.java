
import java.util.concurrent.CountDownLatch;
import java.sql.*;

public class MyThreadInsert {
    private String url = "jdbc:mysql://localhost:3306/db?serverTimezone=GMT%2B8&useSSL=false";
    private String user = "root";
    private String password = "SQT1800405089";
    private int batchNum = 12500000;
    private static int count = 0;

    public Connection getConnect() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    public void multiThreadImport(final int ThreadNum) {
        final CountDownLatch cdl = new CountDownLatch(ThreadNum);
        long starttime = System.currentTimeMillis();
        for (int k = 0; k < ThreadNum; k++) {
            int startPoint = k * batchNum;
            System.out.println("batch: " + startPoint);
            new Thread(() -> {
                batchInsert(startPoint);
                cdl.countDown();
            }).start();
        }
        try {
            cdl.await();
            long spendtime = System.currentTimeMillis() - starttime;
            System.out.println(ThreadNum + "个线程花费时间:" + spendtime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void batchInsert(int startPoint) {
        System.out.println("In batch: " + startPoint);
        String sql = "INSERT INTO work3(id,bald,concept,fullId,skillId,skillVersion,name,address,sex,num,weight)values(?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection con = getConnect()) {
            con.setAutoCommit(false);
            PreparedStatement prst = con.prepareStatement(sql);
            for (int i = startPoint; i < startPoint + batchNum; i++) {
                count++;
                prst.setInt(1, i);
                prst.setString(2, "5ad41947eba49e000" + i);
                prst.setString(3, "company");
                prst.setString(4, "5ad41947eba49e0007c12332N0KK" + i);
                prst.setInt(5, 100024);
                prst.setInt(6, 100001);
                prst.setString(7, "zhongxia");
                prst.setString(8, "北京市海淀区");
                prst.setInt(9, 1);
                prst.setInt(10, 1445536229);
                prst.setInt(11, 60);
                prst.addBatch();
                if (count % 100000 == 0) {
                    prst.executeBatch();
                    con.commit();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        MyThreadInsert ti = new MyThreadInsert();
        ti.multiThreadImport(4);
    }

}