package com.cms.template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.sql.Timestamp;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 文章
 * </p>
 *
 * @author author
 * @since 2021-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Article extends Model<Article> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long siteId;

    private Long cateId;

    private Integer status;

    private Integer recommend;

    private String title;

    private String time;

    private String author;

    private Integer views;

    private String description;

    private String detail;

    private String header;
    private String files;

    private Timestamp createTime;

    private Timestamp updateTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
