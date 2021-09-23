package com;

import com.wzh.home.entity.po.UmsUser;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author weizhuohang
 * @since 2021/09/15 09:24
 */
public class SortTest {
    public static void main(String[] args) {
        List<UmsUser> userList = new ArrayList<>();
        UmsUser user1 = new UmsUser();
        user1.setUsername("wzh");
        user1.setUpdateCount(1);
        userList.add(user1);
        UmsUser user2 = new UmsUser();
        user2.setUsername("azh");
        user2.setUpdateCount(1);
        userList.add(user2);
        UmsUser user3 = new UmsUser();
        user3.setUsername("zzh");
        user3.setUpdateCount(1);
        userList.add(user3);
        // 1,4,3
        System.out.println(userList);
        userList.sort((o1, o2) -> {
            // >0升序，<0降序
            int flag = 0;
            if (o1.getUpdateCount() > o2.getUpdateCount()) {
                flag = 1;
            } else if (o1.getUpdateCount() < o2.getUpdateCount()) {
                flag = -1;
            }
            return flag;
        });
        // 1,3,4
        System.out.println(userList);
        userList.sort((o1, o2) -> {
            int flag = 0;
            if (o1.getUpdateCount() > o2.getUpdateCount()) {
                flag = -1;
            } else if (o1.getUpdateCount() < o2.getUpdateCount()) {
                flag = 1;
            }else{
                flag = o1.getUsername().compareTo(o2.getUsername());
            }
            return flag;
        });
        // 4,3,1
        System.out.println(userList);
        int i = user1.getUsername().compareTo(user2.getUsername());
        System.out.println(i);
        int i2 = user1.getUsername().compareTo(user3.getUsername());
        System.out.println(i2);
    }
}
