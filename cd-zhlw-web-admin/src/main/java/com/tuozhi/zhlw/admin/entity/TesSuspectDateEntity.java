package com.tuozhi.zhlw.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Descriotion TODO 外部稽核协查工单嫌疑数据
 * @author liyuyan
 * @create 2019/10/10
 *
 */
@Entity
@Data
@Table(name="JC_INVESTIGATION_EXTERNALORDER")
public class TesSuspectDateEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id; //主键ID
	
	private String passId; //通行标识ID
	
	private String mediaType; //通行介质类型
	
	private String obuId; //OBU序号编码
	
	private String cardId; //CPC卡或者ETC卡编号
	
	private String escapeType; //疑似逃费类型

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date enTime; //入口处理时间
	
	private String enlaneId; //入口车道
	
	private String exlaneId; //出口处理时间

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date exTime; //出口车道
	
	private Long sid; //外部协查的关联id

	/*追加字段*/
	private String evidenceArr; //选中的嫌疑数据
}
