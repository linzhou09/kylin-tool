package com.kylin.http.client.biz.handler.analysis.url;

import com.kylin.http.client.biz.annotation.HttpUrlAnalysis;
import com.kylin.http.client.biz.context.HttpRequestContext;
import com.kylin.spring.utils.utils.SpringUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
 * @date : 2021/12/12 15:03
 * @author: linzhou
 * @description : AnalysisMethodParamHandlerManager
 */
public class AnalysisUrlHandlerManager {

    private static Map<Class<? extends AnalysisUrlHandler>, AnalysisUrlHandler> analysisUrlHandlerMap;


    private static Class<? extends AnalysisUrlHandler> defaultAnalysisUrlHandler;

    public static String analysisUrl(HttpRequestContext context) throws Exception {

        HttpUrlAnalysis httpUrlAnalysis = context.getMethodOrInterfaceAnnotation(HttpUrlAnalysis.class);
        Class<? extends AnalysisUrlHandler> analysisUrlHandlerClass;
        if(Objects.nonNull(httpUrlAnalysis)){
            analysisUrlHandlerClass = httpUrlAnalysis.value();
        }else {
//            analysisUrlHandlerClass = DefaultAnalysisUrlHandler.class;
            analysisUrlHandlerClass = defaultAnalysisUrlHandler;
        }

        AnalysisUrlHandler analysisUrlHandler = getAnalysisUrlHandler(analysisUrlHandlerClass);
        return analysisUrlHandler.analysisUrl(context);
    }

    private static AnalysisUrlHandler getAnalysisUrlHandler(Class<? extends AnalysisUrlHandler> clazz) {
        return getAnalysisUrlHandlerMap().get(clazz);
    }

    private static Map<Class<? extends AnalysisUrlHandler>, AnalysisUrlHandler> getAnalysisUrlHandlerMap() {
        if (Objects.isNull(analysisUrlHandlerMap)) {
            analysisUrlHandlerMap = new HashMap<>();
            List<AnalysisUrlHandler> analysisUrlHandlers = SpringUtil.getBeanList(AnalysisUrlHandler.class);
            if (CollectionUtils.isNotEmpty(analysisUrlHandlers)) {
                for (AnalysisUrlHandler analysisUrlHandler : analysisUrlHandlers) {
                    analysisUrlHandlerMap.put(analysisUrlHandler.getClass(), analysisUrlHandler);

                }
            }
        }
        return analysisUrlHandlerMap;
    }

    public static synchronized void setDefaultAnalysisUrlHandler(Class<? extends AnalysisUrlHandler> clazz){
        if (Objects.isNull(defaultAnalysisUrlHandler)) {
            defaultAnalysisUrlHandler = clazz;
        }
    }

}
