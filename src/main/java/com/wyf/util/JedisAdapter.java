package com.wyf.util;

import org.hibernate.validator.internal.constraintvalidators.bv.past.PastValidatorForReadableInstant;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

/**
 * Created by w7397 on 2017/3/22.
 */
public class JedisAdapter {

    public static void print(int index, Object obj) {
        System.out.println(String.format("%d,%s", index, obj.toString()));
    }

    public static void main(String[] argv) {
        Jedis jedis = new Jedis();
        jedis.flushAll();

        jedis.set("hello", "world");
        print(1, jedis.get("hello"));
        jedis.rename("hello", "newhello");
        print(1, jedis.get("newhello"));
        jedis.setex("hello2", 15, "world");

        jedis.set("pv", "100");
        jedis.incr("pv");
        print(2, jedis.get("pv"));
        jedis.incrBy("pv", 5);
        print(2, jedis.get("pv"));

        //列表操作,最近来访
        String listName = "listA";
        for (int i = 0; i < 10; ++i) {
            jedis.lpush(listName, "a" + String.valueOf(i));
        }
        print(3, jedis.lrange(listName, 0, 12));
        print(4, jedis.llen(listName));
        print(5, jedis.lpop(listName));
        print(6, jedis.llen(listName));
        print(7, jedis.lindex(listName, 3));
        print(8, jedis.linsert(listName, BinaryClient.LIST_POSITION.AFTER, "a4", "xx"));
        print(9, jedis.linsert(listName, BinaryClient.LIST_POSITION.BEFORE, "a4", "BB"));
        print(10, jedis.lrange(listName, 0, 12));

        //Hash
        String userKey = "userxxx";
        jedis.hset(userKey, "name", "jim");
        jedis.hset(userKey, "age", "12");
        jedis.hset(userKey, "phone", "18181818181");

        print(12, jedis.hget(userKey, "name"));
        print(13, jedis.hgetAll(userKey));
        jedis.hdel(userKey, "phone");
        print(14, jedis.hgetAll(userKey));
        print(15, jedis.hkeys(userKey));
        print(16, jedis.hvals(userKey));
        print(17, jedis.hexists(userKey, "email"));
        print(18, jedis.hexists(userKey, "age"));
        jedis.hsetnx(userKey, "school", "lf");
        jedis.hsetnx(userKey, "name", "wyf");
        print(19, jedis.hgetAll(userKey));

        //set，共同好友
        String likeKeys1 = "newsLike1";
        String likeKeys2 = "newsLike2";
        for (int i = 0; i < 10; i++) {
            jedis.sadd(likeKeys1, String.valueOf(i));
            jedis.sadd(likeKeys2, String.valueOf(i * 2));
        }
        print(20, jedis.smembers(likeKeys1));
        print(21, jedis.smembers(likeKeys2));
        print(22, jedis.sinter(likeKeys1, likeKeys2));
        print(23, jedis.sunion(likeKeys1, likeKeys2));
        print(24, jedis.sdiff(likeKeys1, likeKeys2));
        print(25, jedis.sismember(likeKeys1, "5"));
        jedis.srem(likeKeys1, "5");
        print(26, jedis.smembers(likeKeys1));
        print(27, jedis.scard(likeKeys1));
        jedis.smove(likeKeys2, likeKeys1, "14");
        print(28, jedis.smembers(likeKeys1));

        //优先队列,排名
        String rankKey = "rankKey";
        jedis.zadd(rankKey, 15, "jim");
        jedis.zadd(rankKey, 60, "ben");
        jedis.zadd(rankKey, 90, "Lee");
        jedis.zadd(rankKey, 80, "mei");
        jedis.zadd(rankKey, 75, "lucy");
        print(30, jedis.zcard(rankKey));
        print(31, jedis.zcount(rankKey, 61, 100));
        print(32, jedis.zscore(rankKey, "lucy"));
        jedis.zincrby(rankKey, 2, "lucy");
        jedis.zincrby(rankKey, 2, "Lucy");
        print(33, jedis.zscore(rankKey, "lucy"));
        print(34, jedis.zcount(rankKey, 0, 100));
        print(35, jedis.zrange(rankKey, 1, 3));
        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, "0", "100")) {
            print(37, tuple.getElement() + ":" + String.valueOf(tuple.getScore()));
        }

        print(38, jedis.zrank(rankKey, "ben"));
        print(39, jedis.zrevrank(rankKey, "ben"));

        JedisPool pool=new JedisPool();
        for (int i=0;i<100;++i){
            Jedis j=pool.getResource(); //default 8
            j.get("a");
            System.out.println("POOL"+i);
            j.close();
        }
    }
}
