package com.pt.vx.domain.Api;

public class XinGuanDTO {

    private String diagnosis;
    private String death;
    private String cure;
    private String updatetime;
    private String Datafrom;

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getCure() {
        return cure;
    }

    public void setCure(String cure) {
        this.cure = cure;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getDatafrom() {
        return Datafrom;
    }

    public void setDatafrom(String datafrom) {
        Datafrom = datafrom;
    }

    @Override
    public String toString() {
        return
                "累计确诊:" + diagnosis + "\n" +
                "累计死亡:" + death + "\n" +
                "累计治愈:" + cure +  "\n"+
                "更新时间:" + updatetime +  "\n" +
                "数据来源:" + Datafrom;
    }
}
