package com.cms.template.common.result;

public class CodeMsg {

    public int code;
    public String msg;

    // 通用的错误码 500000
    public static CodeMsg SUCCESS = new CodeMsg(0, "请求成功！");
    public static CodeMsg FAILED = new CodeMsg(1, "请求失败！");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500000, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500001, "参数校验异常：%s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500002, "请求非法");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500004, "访问太频繁！");

    public static CodeMsg ALI_OSS_UPLOAD_ERROR = new CodeMsg(500010, "OSS上传文件错误！");
    public static CodeMsg ALI_OSS_UPLOAD_FAILED = new CodeMsg(500011, "OSS上传文件失败！");

    public static CodeMsg EXCEPTION_Exception = new CodeMsg(500050, "全局异常捕获");
    public static CodeMsg EXCEPTION_BadCredentialsException = new CodeMsg(500051, "security验证异常");
    public static CodeMsg EXCEPTION_BizException = new CodeMsg(500052, "业务异常，请检查请求");
    public static CodeMsg EXCEPTION_NullPointerException = new CodeMsg(500053, "空指针异常，请联系管理员反馈");
    public static CodeMsg EXCEPTION_SQLIntegrityConstraintViolationException = new CodeMsg(500054, "数据库字段值重复异常");
    public static CodeMsg EXCEPTION_BadPaddingException = new CodeMsg(500055, "解密失败，请重试！");
    public static CodeMsg EXCEPTION_MultipartException = new CodeMsg(500056, "Multipart文件流异常");
    public static CodeMsg EXCEPTION_DegradeException = new CodeMsg(500057, "接口达到限制熔断");

    public static CodeMsg BIZ_EXCEPTION_DETAILS_NULL_VALUE = new CodeMsg(500070, "token中对应值为null");

    // 用户模块 5001XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500100, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500101, "登录密码不能为空");
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500102, "用户不存在");
    public static CodeMsg USER_LOGIN_FAILED = new CodeMsg(500103, "登录失败");
    public static CodeMsg USER_REGISTER_FAILED = new CodeMsg(500104, "注册失败");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500112, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500113, "手机号格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500114, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500115, "密码错误");
    public static CodeMsg USER_NO_LOGIN = new CodeMsg(500116, "用户未登录");

    public static CodeMsg USER_AUTH_SHORT = new CodeMsg(500117, "权限不足");

    public static CodeMsg USER_ADDRESS_NOT_EXIST = new CodeMsg(500121, "地址不存在");

    public static CodeMsg USER_WX_MP_VALUE_ERROR = new CodeMsg(500150, "微信小程序数据错误");
    public static CodeMsg USER_WX_MP_SESSION_KEY_ERROR = new CodeMsg(500151, "微信sessionKey获取有误");
    public static CodeMsg USER_WX_MP_OPENID_ERROR = new CodeMsg(500152, "微信openId获取有误");
    public static CodeMsg USER_WX_MP_PHONE_FAILED = new CodeMsg(500152, "微信获取手机号失败");
    public static CodeMsg USER_WX_MP_PHONE_DEC_FAILED = new CodeMsg(500152, "微信手机号解密失败");

    // 分类文章模块 5003XX
    public static CodeMsg ARTICLE_CATE_NOT_EXIST = new CodeMsg(500110, "没有该商品分类");
    public static CodeMsg ARTICLE_CATE_SITE_ID_NOT_EXIST = new CodeMsg(500111, "站点ID为空");
    public static CodeMsg ARTICLE_NOT_EXIST = new CodeMsg(500120, "该文章不存在");


    public CodeMsg() {
    }

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }


}
