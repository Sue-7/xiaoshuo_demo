package com.xiaoshuo.business.api.association.util;

import com.xiaoshuotech.cloud.core.convertor.ApplicationContextHelper;
import com.xiaoshuotech.cloud.core.date.DateBaseUtil;
import com.xiaoshuotech.minmetals.association.api.enums.SerialCodeNameEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author liyaunyuan
 * @description redis生成编号工具类
 * @date 2019/12/16
 */
public class GenerateCodeUtils {

    /**
     * 分隔符
     */
    public static final String SPLIT = ":";

    /**
     * key前缀
     */
    public static final String KEY_PREFIX = "dc_salver_serial_number";

    private static StringRedisTemplate redisTemplate;

    //使用@Autowired注解无法注入static修饰的类变量,spring默认cglib对象注入方式,通过以下方法解决;
    //使用spring工厂获取bean对象然后在静态代码块中赋值
    static {
        if (redisTemplate == null) {
            ApplicationContext applicationContext = ApplicationContextHelper.getContext();
            redisTemplate = applicationContext.getBean(StringRedisTemplate.class);
        }
    }


    /**
     * 生成单号
     *
     * @param serialCodeNameEnum 生成流水编号标识枚举
     * @return 单号
     */
    public static String generateCode(SerialCodeNameEnum serialCodeNameEnum) {
        // 当天时间字符串
        String currentDate = DateBaseUtil.format(new Date(), DateBaseUtil.SHORT_YEAR_FORMAT);
        // key 标识前缀+分隔符+当天
        String key = KEY_PREFIX + SPLIT + serialCodeNameEnum.getCode() + SPLIT + currentDate;
        // 自增
        Long serialNumber = redisTemplate.opsForValue().increment(key, 1);
        // 设置过期时间
        redisTemplate.expire(key, 86400L, TimeUnit.SECONDS);
        // 返回流水号
        return serialCodeNameEnum.getPrefix() + currentDate + String.format("%0" + serialCodeNameEnum.getNumber() + "d", serialNumber);
    }

    public static void main(String[] args) {
        String s = generateCode(SerialCodeNameEnum.T_AUDIT_CODE);

        System.out.printf("ssssssssss=" + s);
    }

    /**
     * 融资/白条申请订单编号规则：平台编号+业务编号+申请日期+6位流水号
     * 融资/白条还款订单编号规则：平台编号+业务编号+HK+申请日期+6位流水号
     *
     * @param platformCode       平台编号
     * @param businessCode       业务编码
     * @param serialCodeNameEnum
     * @return
     */
    public static String generateCode(String platformCode, String businessCode, SerialCodeNameEnum serialCodeNameEnum) {

        platformCode = StringUtils.isNotBlank(platformCode) ? platformCode : "";

        businessCode = StringUtils.isNotBlank(businessCode) ? businessCode : "";

        // 当天时间字符串
        String currentDate = DateBaseUtil.format(new Date(), DateBaseUtil.SHORT_YEAR_FORMAT);
        // key 标识前缀+分隔符+当天
        String key = KEY_PREFIX + SPLIT + platformCode + businessCode + serialCodeNameEnum.getCode() + SPLIT + currentDate;
        // 自增
        Long serialNumber = redisTemplate.opsForValue().increment(key, 1);
        // 设置过期时间
        redisTemplate.expire(key, 86400L, TimeUnit.SECONDS);

        // 返回流水号
        return platformCode + businessCode + serialCodeNameEnum.getPrefix() + currentDate + String.format("%0" + serialCodeNameEnum.getNumber() + "d", serialNumber);
    }

}
