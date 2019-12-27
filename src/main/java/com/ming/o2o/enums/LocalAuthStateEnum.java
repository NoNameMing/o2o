package com.ming.o2o.enums;

public enum LocalAuthStateEnum {
    LOGINFAIL(-1, "密码或帐号输入有误"), SUCCESS(0, "操作成功"), NULL_AUTH_INFO(-1006,
            "注册信息为空"), ONLY_ONE_ACCOUNT(-1007,"最多只能绑定一个本地帐号");
    // 状态
    private int state;
    // 状态信息
    private String stateInfo;


    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    /**
     * 构造器，状态与状态信息
     *
     * @param state
     * @param stateInfo
     */
    LocalAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static LocalAuthStateEnum stateOf(int state){
        for (LocalAuthStateEnum localAuthStateEnum : values()) {
            if(localAuthStateEnum.getState() == state) {
                return localAuthStateEnum;
            }
        }
        return null;
    }
}
