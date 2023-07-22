package com.kylin.biz.utils.enums;

import java.util.Objects;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //      ========`-.____`-.___\_____/___.-`____.-'========         //
 * //                           `=---='                              //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //         佛祖保佑           永无BUG           永不修改              //
 * //          佛曰:                                                  //
 * //                 写字楼里写字间，写字间里程序员;                      //
 * //                 程序人员写程序，又拿程序换酒钱.                      //
 * //                 酒醒只在网上坐，酒醉还来网下眠;                      //
 * //                 酒醉酒醒日复日，网上网下年复年.                      //
 * //                 但愿老死电脑间，不愿鞠躬老板前;                      //
 * //                 奔驰宝马贵者趣，公交自行程序员.                      //
 * //                 别人笑我忒疯癫，我笑自己命太贱;                      //
 * //                 不见满街漂亮妹，哪个归得程序员?                      //
 * ////////////////////////////////////////////////////////////////////
 *
 * @date : 2022/6/28 23:25
 * @author: linzhou
 * @description : EnumUtil
 */
public class EnumUtil {

    public static  <T extends Enum> T getEnumByCode(Object code,Class<T> tClass){

        T[] enumConstants = tClass.getEnumConstants();

        for (T enumItem : enumConstants) {
            Object enumCode = null;
            try {
                enumCode = tClass.getMethod("getCode").invoke(enumItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Objects.equals(enumCode,code)){
                return enumItem;
            }
        }
        return null;
    }

    public static  <T extends Enum> T getEnumByName(String enumName,Class<T> tClass){

        T[] enumConstants = tClass.getEnumConstants();

        for (T enumItem : enumConstants) {
            String name = null;
            try {
                name = tClass.getMethod("name").invoke(enumItem).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (Objects.equals(name,enumName)){
                return enumItem;
            }
        }
        return null;
    }


}
