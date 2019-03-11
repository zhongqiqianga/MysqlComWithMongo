import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import java.net.UnknownHostException;

public class MongoDB {
    public static void main(String[] args) {

//建立连接
        Mongo mongo = null;
        try {
            mongo = new Mongo("localhost", 27017);
            //获取指定的数据库
            DB db = mongo.getDB("test");
            //获取指定的集合,并且插入文档
            long time = OneThread(db);
            System.out.println("OK,用时：" + time + "秒");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } finally {
            if (mongo != null)
                mongo.close();
        }
    }

    public static long OneThread(DB db) {
        DBCollection dbCollection = db.getCollection("work");
        Long startTime = System.currentTimeMillis();
        for (int i = 0; i < 5000000; i++) {
            BasicDBObject dbObject = new BasicDBObject();//DBObject代表的是一个对象
            dbObject.put("bald", "5ad41947eba49e0007c12332N0KK19");
            dbObject.put("concept", "company");
            dbObject.put("fullId", "5ad41947eba49e0007c12332N0KK19-100024-100001");
            dbObject.put("skillId", "100024");
            dbObject.put("skillVersion", "100001");
            dbCollection.insert(dbObject);
        }
        Long endTime = System.currentTimeMillis();
        return (endTime - startTime) / 1000;
    }

    public static void ManyThread(DB db) {


    }
}
