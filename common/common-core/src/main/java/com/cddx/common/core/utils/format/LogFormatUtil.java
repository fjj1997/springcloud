package com.cddx.common.core.utils.format;


import com.cddx.common.core.utils.thread.ThreadLocalUtil;

/**
 * 日志追踪工具
 *
 * @author liliang
 */
public class LogFormatUtil {

    public static void init(String businessName) {
        ThreadLocalUtil.set("businessName",businessName);
    }

    /**
     * 初始化日志打印前缀信息
     * @param businessName  业务名
     * @param author  创建人
     */
    public static void init(String businessName,String author) {
        ThreadLocalUtil.set("businessName",businessName);
        ThreadLocalUtil.set("author",author);
    }

    /**
     * 销毁日志打印前缀信息
     */
    public static void destory() {
        if (ThreadLocalUtil.get("businessName") != null) {
            ThreadLocalUtil.remove("businessName");
        }

        if (ThreadLocalUtil.get("author") != null) {
            ThreadLocalUtil.remove("author");
        }
    }

    /**
     * 启始打印
     * @param content
     * @return
     */
    public static String start(String content) {
        return startAndEndPrint(content,"start");
    }

    /**
     * 启始打印
     * @return
     */
    public static String start() {
        return startAndEndPrint("","start");
    }


    /**
     * 结束打印
     * @param content
     * @return
     */
    public static String end(String content) {
        return startAndEndPrint(content,"end");
    }

    /**
     * 结束打印
     * @return
     */
    public static String end() {
        return startAndEndPrint("","end");
    }

    /**
     * 常规打印
     * @param content
     * @return
     */
    public static String print(String content) {
        return addPrefix()+content;
    }

    /**
     * http接口入参打印
     * @param httpApiName
     * @param url
     * @param params
     * @return
     */
    public static String httpParam(String httpApiName, String url, String params) {
        return httpCommont(httpApiName,url,params,"入参>>>");
    }

    /**
     * http接口入参打印
     * @param httpApiName
     * @param params
     * @return
     */
    public static String httpParam(String httpApiName,String params) {
        return httpCommont(httpApiName,null,params,"入参>>>");
    }

    /**
     * http接口出参打印
     * @param httpApiName
     * @param url
     * @param result
     * @return
     */
    public static String httpResult(String httpApiName,String url,String result) {
        return httpCommont(httpApiName,url,result,"出参<<<");
    }

    /**
     * http接口出参打印
     * @param httpApiName
     * @param result
     * @return
     */
    public static String httpResult(String httpApiName,String result) {
        return httpCommont(httpApiName,null,result,"出参<<<");
    }

    /**
     * webService接口入参打印
     * @param methodName
     * @param params
     * @param nameSpace
     * @param url
     * @return
     */
    public static String webServiceParam(String apiName,String methodName,String params,String nameSpace,String url) {
        return webServiceCommont(apiName,methodName,params,nameSpace,url,"入参>>>");
    }

    /**
     * webService接口入参打印
     * @param apiName
     * @param methodName
     * @param params
     * @param url
     * @return
     */
    public static String webServiceParam(String apiName,String methodName,String params,String url) {
        return webServiceCommont(apiName,methodName,params,null,url,"入参>>>");
    }

    /**
     * webService接口入参打印
     * @param apiName
     * @param methodName
     * @param params
     * @return
     */
    public static String webServiceParam(String apiName,String methodName,String params) {
        return webServiceCommont(apiName,methodName,params,null,null,"入参>>>");
    }

    /**
     * webService接口出参打印
     * @param methodName
     * @param result
     * @param nameSpace
     * @param url
     * @return
     */
    public static String webServiceResult(String apiName,String methodName,String result,String nameSpace,String url) {
        return webServiceCommont(apiName,methodName,result,nameSpace,url,"出参<<<");
    }

    /**
     * webService接口出参打印
     * @param apiName
     * @param methodName
     * @param result
     * @param url
     * @return
     */
    public static String webServiceResult(String apiName,String methodName,String result,String url) {
        return webServiceCommont(apiName,methodName,result,null,url,"出参<<<");
    }

    /**
     * webService接口出参打印
     * @param apiName
     * @param methodName
     * @param result
     * @return
     */
    public static String webServiceResult(String apiName,String methodName,String result) {
        return webServiceCommont(apiName,methodName,result,null,null,"出参<<<");
    }






    /** ------------------------------------自定义封装方法区-------------------------------------------- start */
    private static String startAndEndPrint(String content,String flag) {
        Object businessName = ThreadLocalUtil.get("businessName");
        Object author = ThreadLocalUtil.get("author");
        return "==============="+addPrefix()+content+"==============="+flag;
    }

    private static String httpCommont(String httpApiName, String url, String param, String flag) {
        Object businessName = ThreadLocalUtil.get("businessName");
        Object author = ThreadLocalUtil.get("author");
        return addPrefix()+"http接口:["+httpApiName+"]\n"+addUrl(url)+"\n"+flag+"[\n"+param+"\n]";
    }

    private static String webServiceCommont(String apiName,String methodName,String param,String nameSpace,String url,String flag) {
        Object businessName = ThreadLocalUtil.get("businessName");
        Object author = ThreadLocalUtil.get("author");
        return addPrefix()+" webService接口:["+apiName+"]，方法:["+methodName+"]\n"+addUrl(url)+"\n"+flag+"[\n"+param+"\n]"+addNameSpace(nameSpace);
    }

    public static String addPrefix() {
        Object businessName = ThreadLocalUtil.get("businessName");
        Object author = ThreadLocalUtil.get("author");
        if (businessName != null || author != null) {
            if (businessName != null && author != null) {
                return "[MODULE:"+String.valueOf(businessName)+",author:"+author+"]";
            }else if (businessName != null){
                return "[MODULE:"+String.valueOf(businessName)+"]";
            }else {
                return "[author:"+author+"]";
            }
        }else {
            return "";
        }
    }

    private static String addUrl(String url) {
        if (url != null) {
            return "\nurl地址:["+url+"]";
        }else {
            return "";
        }
    }

    private static String addNameSpace(String nameSpace) {
        if (nameSpace != null) {
            return "\n命名空间:["+nameSpace+"]";
        }else {
            return "";
        }
    }

    /** ------------------------------------自定义封装方法区-------------------------------------------- end */

}
