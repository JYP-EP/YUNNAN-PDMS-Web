package com.gridnt.pdms.constants;

public enum StatusQueryType {
    PANELLIGHT("面板灯"),PLPIECE("硬压板"),FSOFTPLATEN("功能软压板"),
    ESOFTPLATEN("出口软压板"),ALARM("告警"),ACTION("保护动作");

    private String desc;

    StatusQueryType(String desc){
        this.desc = desc;
    }

    public  enum AnalogType{
        TEMP("温度"),VOLTAGE("电压"),LIGHT("光强");
        private String desc;

        AnalogType(String desc){
            this.desc = desc;
        }
    }
}
