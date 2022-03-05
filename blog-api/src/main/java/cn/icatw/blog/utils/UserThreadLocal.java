package cn.icatw.blog.utils;

import cn.icatw.blog.dao.pojo.SysUser;

/**
 * @author icatw
 * @date 2022/3/3
 * @apiNote
 */
public class UserThreadLocal {
    private UserThreadLocal() {
    }

    private static final ThreadLocal<SysUser> LOCAL = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        LOCAL.set(sysUser);
    }

    public static SysUser get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }
}
