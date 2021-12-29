package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class JCWorkFlowNode implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * node_id,
     * defination_id,
     * node_name,
     * prev_node_id,
     * next_node_id,
     * max_day,
     * workflowdeptrole,
     * process_logic,
     * printed_tableid,
     * writable_fields,
     * READONLY_FIELDS,
     * node_type,
     * correspond_node_id,
     * CORRESPOND_SQL,
     * orsplit_value,
     * orsplit_desc
     */
    private Integer nodeId;
    private Integer definationId;
    private String nodeName;
    private String prevNodeId;
    private String nextNodeId;
    private Integer maxDay;
    private String workflowdeptrole;
    private String processLogic;
    private String printedTableid;
    private String writableF_fields;
    private String readOnlyFields;
    private String nodeType;
    private Integer correspondNodeId;
    private String correspondSql;
    private String orsplitValue;
    private String orsplitDesc;
}
