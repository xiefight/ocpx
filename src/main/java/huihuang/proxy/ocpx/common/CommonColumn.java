package huihuang.proxy.ocpx.common;

import java.util.Date;

/**
 * @Description:
 * @Author: xietao
 * @Date: 2023-04-24 16:38
 **/
public class CommonColumn {

    private Integer id;

    private String channelName;

    private Date createTime;
    private Date updateTime;

    /** 当前上报状态 */
    private String reportStatus;
    /** 当前回调状态 */
    private String callBackStatus;
    /** 回调时间戳 */
    private String callBackTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getCallBackStatus() {
        return callBackStatus;
    }

    public void setCallBackStatus(String callBackStatus) {
        this.callBackStatus = callBackStatus;
    }

    public String getCallBackTime() {
        return callBackTime;
    }

    public void setCallBackTime(String callBackTime) {
        this.callBackTime = callBackTime;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
