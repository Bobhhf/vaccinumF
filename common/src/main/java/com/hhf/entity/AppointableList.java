package com.hhf.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 
 * @TableName appointable_list
 */
@TableName(value ="appointable_list")
@Data
public class AppointableList implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 接种疫苗
     */
    private Long vaccinumId;

    /**
     * 可接种数量
     */
    private Integer rums;

    /**
     * 已经预约数量
     */
    private Integer appNum;

    /**
     * 接种地点
     */
    private String address;

    @TableField(exist = false)
    private Vaccinum vaccinum;

    /**
     * 预约日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date appDate;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date postTime;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AppointableList other = (AppointableList) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getVaccinumId() == null ? other.getVaccinumId() == null : this.getVaccinumId().equals(other.getVaccinumId()))
            && (this.getRums() == null ? other.getRums() == null : this.getRums().equals(other.getRums()))
            && (this.getAppNum() == null ? other.getAppNum() == null : this.getAppNum().equals(other.getAppNum()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
            && (this.getAppDate() == null ? other.getAppDate() == null : this.getAppDate().equals(other.getAppDate()))
            && (this.getPostTime() == null ? other.getPostTime() == null : this.getPostTime().equals(other.getPostTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getVaccinumId() == null) ? 0 : getVaccinumId().hashCode());
        result = prime * result + ((getRums() == null) ? 0 : getRums().hashCode());
        result = prime * result + ((getAppNum() == null) ? 0 : getAppNum().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getAppDate() == null) ? 0 : getAppDate().hashCode());
        result = prime * result + ((getPostTime() == null) ? 0 : getPostTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", vaccinumId=").append(vaccinumId);
        sb.append(", rums=").append(rums);
        sb.append(", appNum=").append(appNum);
        sb.append(", address=").append(address);
        sb.append(", appDate=").append(appDate);
        sb.append(", postTime=").append(postTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}