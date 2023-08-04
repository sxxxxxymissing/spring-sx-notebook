package com.sxxxxx.message.center.core.cache;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.sxxxxx.message.center.core.exception.EnumCacheException;
import org.apache.commons.collections4.MapUtils;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class EnumCache {

    private EnumCache() {

    }

    /**
     * 以枚举任意值构建的缓存结构
     **/
    static final Map<Class<? extends Enum>, Map<Object, Enum>> CACHE_BY_NAME = new ConcurrentHashMap<>();

    /**
     * 以枚举任意值构建的缓存结构
     **/
    static final Map<Class<? extends Enum>, Map<Object, Enum>> CACHE_BY_VALUE = new ConcurrentHashMap<>();

    /**
     * 以枚举名称构建缓存, 在枚举的静态块里面调用
     * @param clazz
     * @param es
     * @param <E>
     */
    public static <E extends Enum> void registerByName(Class<E> clazz, E[] es) throws EnumCacheException {
        if (!Enum.class.isAssignableFrom(clazz)) {
            throw new EnumCacheException("该Class类型不是枚举类" + clazz.getSimpleName());
        }
        Map<Object, Enum> map = new ConcurrentHashMap<>();
        for (E e : es) {
            map.put(e.name(), e);
        }
        CACHE_BY_NAME.put(clazz, map);
    }

    /**
     * 以枚举类型构建缓存, 在枚举的静态块里面调用
     * @param clazz
     * @param es
     * @param <E>
     */
    public static <E extends Enum> void registerByValue(Class<E> clazz, E[] es, EnumMapping<E> enumMapping) throws EnumCacheException {
        if (!Enum.class.isAssignableFrom(clazz)) {
            throw new EnumCacheException("该Class类型不是枚举类" + clazz.getSimpleName());
        }
        if (CACHE_BY_VALUE.containsKey(clazz)) {
            log.error("枚举已经构建过value缓存,不允许重复构建 " + clazz.getSimpleName());
        }
        Map<Object, Enum> map = new ConcurrentHashMap<>();
        for (E e : es) {
            Object value = enumMapping.value(e);
            if (map.containsKey(value)) {
                log.error("枚举存在相同的值映射同一个枚举"  + clazz.getSimpleName() + value);
            }
            else {
                map.put(value, e);
            }
        }
        CACHE_BY_VALUE.put(clazz, map);
    }

    /**
     * 从以枚举转换值构建的缓存中通过枚举转换值获取枚举
     * @param clazz
     * @param value
     * @param defaultEnum
     * @param <E>
     * @return
     */
    public static <E extends Enum> E findByValue(Class<E> clazz, Object value, E defaultEnum) throws EnumCacheException {
        return find(clazz, value, CACHE_BY_VALUE, defaultEnum, false);
    }

    /**
     * 从以枚举转换值构建的缓存中通过枚举转换值获取枚举
     * @param clazz
     * @param value
     * @param defaultEnum
     * @param defaultEnum
     * @param isLike
     * @param <E>
     * @return
     */
    public static <E extends Enum> E findByValue(Class<E> clazz, Object value, E defaultEnum, boolean isLike) throws EnumCacheException {
        return find(clazz, value, CACHE_BY_VALUE, defaultEnum, isLike);
    }


    /**
     * 从以枚举名称构建的缓存中通过枚举名获取枚举
     * @param clazz
     * @param name
     * @param defaultEnum
     * @param <E>
     * @return
     */
    public static <E extends Enum> E findByName(Class<E> clazz, String name, E defaultEnum) throws EnumCacheException {
        return find(clazz, name, CACHE_BY_NAME, defaultEnum, false);
    }

    /**
     * 从以枚举名称构建的缓存中通过枚举名获取枚举
     * @param clazz
     * @param name
     * @param defaultEnum
     * @param isLike
     * @param <E>
     * @return
     */
    public static <E extends Enum> E findByName(Class<E> clazz, String name, E defaultEnum, boolean isLike) throws EnumCacheException {
        return find(clazz, name, CACHE_BY_NAME, defaultEnum, isLike);
    }

    private static <E extends Enum> E find(Class<E> clazz, Object obj, Map<Class<? extends Enum>, Map<Object, Enum>> cache, E defaultEnum, boolean isLike) throws EnumCacheException {
        if (!Enum.class.isAssignableFrom(clazz)) {
            throw new EnumCacheException("该Class类型不是枚举类" + clazz.getSimpleName());
        }
        if (obj == null) {
            return defaultEnum;
        }
        Map<Object, Enum> map = cache.get(clazz);
        if (MapUtils.isEmpty(map)) {
            // executeEnumStatic(clazz);// 触发枚举静态块执行
            map = cache.get(clazz); // 执行枚举静态块后重新获取缓存
        }
        if (MapUtils.isEmpty(map)) {
            String msg = null;
            if (cache == CACHE_BY_NAME) {
                msg = String.format(
                        "该枚举类%s还没有注册到枚举缓存中，请在%s.static代码块中加入如下代码 : EnumCache.registerByName(%s.class, %s.values());",
                        clazz.getSimpleName(), clazz.getSimpleName(), clazz.getSimpleName(), clazz.getSimpleName());
            }
            if (cache == CACHE_BY_VALUE) {
                msg = String.format(
                        "枚举%s还没有注册到枚举缓存中，请在%s.static代码块中加入如下代码 : EnumCache.registerByValue(%s.class, %s.values(), %s::getXxx);",
                        clazz.getSimpleName(), clazz.getSimpleName(), clazz.getSimpleName(), clazz.getSimpleName(), clazz.getSimpleName());
            }
            throw new EnumCacheException(msg);
        }
        if (isLike) {
            obj = getLikeKey(obj, map.keySet());
        }
        E result = (E) map.get(obj);
        return result == null ? defaultEnum : result;
    }

    private static Object getLikeKey(Object obj, Set<Object> keys) throws EnumCacheException {
        if (!(obj instanceof String)) {
            throw new EnumCacheException("模糊匹配 key 只支持 String ");
        }
        for (Object key : keys) {
            //相互支持模糊匹配
            if (key.toString().contains(obj.toString()) || obj.toString().contains(key.toString())) {
                return key;
            }
        }

        return obj;
    }

    /**
     * 枚举缓存映射器函数式接口
     */
    @FunctionalInterface
    public interface EnumMapping<E extends Enum> {
        /**
         * 自定义映射器
         *
         * @param e 枚举
         * @return 映射关系，最终体现到缓存中
         */
        Object value(E e);
    }
}
