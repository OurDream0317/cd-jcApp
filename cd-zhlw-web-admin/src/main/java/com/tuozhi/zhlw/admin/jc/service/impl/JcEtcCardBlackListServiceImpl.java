package com.tuozhi.zhlw.admin.jc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.JcEtcCardBlackListEntity;
import com.tuozhi.zhlw.admin.jc.entity.JcEtcCardBlackListHistoryEntity;
import com.tuozhi.zhlw.admin.jc.mapper.JcEtcCardBlackListMapper;
import com.tuozhi.zhlw.admin.jc.service.JcEtcCardBlackListService;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName JcEtcCardBlackListServiceImpl
 * @Descriotion TODO ETC发行黑名单业务实现
 * @Author liyuyan
 * @Date 2019/11/29 10:59
 * @Version 1.0
 */
@Service
@Slf4j
public class JcEtcCardBlackListServiceImpl implements JcEtcCardBlackListService {

    @Resource
    JcEtcCardBlackListMapper jcEtcCardBlackListMapper;

    /**
     * 1、查询所有ETC发行黑名单信息 2、根据条件查询黑名单列表信息
     *
     * @param jcEtcCardBlackListEntity
     * @return
     */
    @Override
    public ResultExtGrid findEtcBlackListAll(JcEtcCardBlackListEntity jcEtcCardBlackListEntity, QueryParams queryParams) {
        //设置分页
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<JcEtcCardBlackListEntity> etcBlackListData = jcEtcCardBlackListMapper.findEtcBlackListAll(jcEtcCardBlackListEntity);
        PageInfo pageInfo = new PageInfo<>(etcBlackListData);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 根据编号查询ETC黑名单历史记录
     *
     * @param avtId
     * @return
     */
    @Override
    public List<JcEtcCardBlackListHistoryEntity> findEtcBlackHistoryByAvtId(Long avtId) {
        return jcEtcCardBlackListMapper.findEtcBlackListHistroyAll(avtId);
    }

    /**
     * 根据车牌号、车牌颜色查询
     *
     * @param vehicleLicense
     * @param vehicleColor
     * @return
     */
    @Override
    public ResultMsg findBlackByVehicleId(String vehicleLicense, String vehicleColor, Double sumMoneny) {
        ResultMsg result = new ResultMsg();
        JcEtcCardBlackListEntity etcBlackList = jcEtcCardBlackListMapper.findBlackByVehicleId(vehicleLicense, vehicleColor, sumMoneny);
        //判断该车辆信息是否已存在
        if (etcBlackList == null) {
            result.setSuccess(false);
            return result;
        }
        result.setSuccess(true);
        result.setData(etcBlackList);
        return result;
    }

    /**
     * 修改已存在的ETC黑名单车辆，并新增历史记录
     *
     * @param jcEtcCardBlackListEntity
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg updateEtcBlackList(JcEtcCardBlackListEntity jcEtcCardBlackListEntity, LoginUser loginUser) {
        ResultMsg result = new ResultMsg();
        String vehicleLicense = jcEtcCardBlackListEntity.getVehicleLicense();
        String vehicleColor = jcEtcCardBlackListEntity.getVehicleColor();
        //根据用户输入的车牌号及车牌颜色查询
        JcEtcCardBlackListEntity etcBlackList = jcEtcCardBlackListMapper.findBlackByVehicleId(vehicleLicense, vehicleColor, null);
        //汇总金额
        //Double sumMoney = jcEtcCardBlackListEntity.getSumMoneny() == null ? 0 : jcEtcCardBlackListEntity.getSumMoneny()*100;
        jcEtcCardBlackListEntity.setFlag(0); //更新标志位，默认0
        jcEtcCardBlackListEntity.setBlackListType(5); //状态名单类型 5:禁用(注：与黑名单值一致)
        jcEtcCardBlackListEntity.setBlackType("5"); //黑名单状态，5:黑名单 1:拉白
        jcEtcCardBlackListEntity.setSumMoneny(jcEtcCardBlackListEntity.getSumMoneny());
        jcEtcCardBlackListEntity.setDeleteFlag(0); //转白标志位默认0
        jcEtcCardBlackListEntity.setCreateDept(loginUser.getDeptId().toString());
        jcEtcCardBlackListEntity.setCreateDeptName(loginUser.getDeptName());
        jcEtcCardBlackListEntity.setCreateTime(etcBlackList.getCreateTime());
        jcEtcCardBlackListEntity.setTime(new Date());
        jcEtcCardBlackListEntity.setDeleteTime(etcBlackList.getCreateTime());
        //如果已经存在的车辆为灰名单状态，不追加其汇总金额以及欠费条数
        if (etcBlackList.getBlackType().equals("1")) {
            jcEtcCardBlackListEntity.setAvtId(etcBlackList.getAvtId());
            int uptCount1 = jcEtcCardBlackListMapper.addEtcBlackListHistory(jcEtcCardBlackListEntity);
            if (uptCount1 < 1) {
                result.setSuccess(false);
                result.setMessage("保存失败！");
            }
            result.setSuccess(true);
            result.setMessage("保存成功！");
        } else {
            JcEtcCardBlackListEntity etcCardBlackListEntity = new JcEtcCardBlackListEntity();
            etcCardBlackListEntity.setSumMoneny(etcBlackList.getSumMoneny() + jcEtcCardBlackListEntity.getSumMoneny()); //累加汇总金额
            etcCardBlackListEntity.setSumTotal(etcBlackList.getSumTotal() + jcEtcCardBlackListEntity.getSumTotal());//累加欠费条数
            etcCardBlackListEntity.setVehicleLicense(jcEtcCardBlackListEntity.getVehicleLicense());
            etcCardBlackListEntity.setVehicleColor(jcEtcCardBlackListEntity.getVehicleColor());
            int uptCount = jcEtcCardBlackListMapper.updateBlackToll(etcCardBlackListEntity);
            if (uptCount < 1) {
                result.setSuccess(false);
                result.setMessage("保存失败！");
                return result;
            }
            jcEtcCardBlackListEntity.setAvtId(etcBlackList.getAvtId());
            int uptCount1 = jcEtcCardBlackListMapper.addEtcBlackListHistory(jcEtcCardBlackListEntity);
            if (uptCount1 < 1) {
                throw new RuntimeException("新增失败，请重试！");
            }
            result.setSuccess(true);
            result.setMessage("保存成功，汇总金额及欠费条数已累加！");
        }
        return result;
    }

    /**
     * ETC黑名单添加，并同步添加至黑名单历史记录中
     *
     * @param jcEtcCardBlackListEntity
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg addEtcBlackList(JcEtcCardBlackListEntity jcEtcCardBlackListEntity, LoginUser loginUser) {
        ResultMsg result = new ResultMsg();
        //汇总金额
        //Double sumMoney = jcEtcCardBlackListEntity.getSumMoneny() == null ? 0 : jcEtcCardBlackListEntity.getSumMoneny()*100;
        jcEtcCardBlackListEntity.setFlag(0); //更新标志位（黑名单下发状态 0：未下发 1：已下发）
        jcEtcCardBlackListEntity.setBlackListType(5); //状态名单类型 5:禁用(注：与黑名单值一致)
        jcEtcCardBlackListEntity.setBlackType("5"); //黑名单状态，5:黑名单 1:拉白
        jcEtcCardBlackListEntity.setSumMoneny(jcEtcCardBlackListEntity.getSumMoneny());
        jcEtcCardBlackListEntity.setDeleteFlag(0); //转白标志位默认为0未下发状态
        jcEtcCardBlackListEntity.setCreateDept(loginUser.getDeptId().toString());
        jcEtcCardBlackListEntity.setCreateDeptName(loginUser.getDeptName());
        jcEtcCardBlackListEntity.setCreateTime(new Date());
        jcEtcCardBlackListEntity.setTime(new Date());
        jcEtcCardBlackListEntity.setDeleteTime(new Date());
        int addCount = jcEtcCardBlackListMapper.addEtcCardBlackList(jcEtcCardBlackListEntity);
        if (addCount < 1) {
            result.setSuccess(false);
            result.setMessage("保存失败");
            return result;
        }
        int addCount1 = jcEtcCardBlackListMapper.addEtcBlackListHistory(jcEtcCardBlackListEntity);
        if (addCount1 < 1) {
            throw new RuntimeException("保存失败,请重试！");
        }
        result.setSuccess(true);
        result.setMessage("保存成功！");
        return result;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeEtcBlackList(JcEtcCardBlackListEntity jcEtcCardBlackListEntity, LoginUser loginUser) {
        jcEtcCardBlackListMapper.changeEtcBlackList(jcEtcCardBlackListEntity);//更新
        String vehicleLicense = jcEtcCardBlackListEntity.getVehicleLicense();
        String vehicleColor = jcEtcCardBlackListEntity.getVehicleColor();
        //根据用户输入的车牌号及车牌颜色查询
        JcEtcCardBlackListEntity etcBlackList = jcEtcCardBlackListMapper.findBlackByVehicleId(vehicleLicense, vehicleColor, null);
        jcEtcCardBlackListEntity.setFlag(0); //更新标志位，默认0
        jcEtcCardBlackListEntity.setBlackListType(5); //状态名单类型 5:禁用(注：与黑名单值一致)
        jcEtcCardBlackListEntity.setBlackType("5"); //黑名单状态，5:黑名单 1:拉白
        jcEtcCardBlackListEntity.setSumMoneny(jcEtcCardBlackListEntity.getSumMoneny());
        jcEtcCardBlackListEntity.setDeleteFlag(0); //转白标志位默认0
        jcEtcCardBlackListEntity.setCreateDept(loginUser.getDeptId().toString());
        jcEtcCardBlackListEntity.setCreateDeptName(loginUser.getDeptName());
        jcEtcCardBlackListEntity.setCreateTime(etcBlackList.getCreateTime());
        jcEtcCardBlackListEntity.setTime(new Date());
        jcEtcCardBlackListEntity.setDeleteTime(etcBlackList.getCreateTime());
        jcEtcCardBlackListMapper.addEtcBlackListHistory(jcEtcCardBlackListEntity);
    }

    /**
     * 删除、转白
     *
     * @param avtId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg delEtcCardBlackList(Long avtId, Integer flag) {
        ResultMsg result = new ResultMsg();
        JcEtcCardBlackListEntity jcEtcCardBlackListEntity = new JcEtcCardBlackListEntity();
        jcEtcCardBlackListEntity.setAvtId(avtId);
        //根据AVT_ID查询ETC发行黑名单
        List<JcEtcCardBlackListEntity> etcBlackListData = jcEtcCardBlackListMapper.findEtcBlackListAll(jcEtcCardBlackListEntity);
        jcEtcCardBlackListEntity = etcBlackListData.get(0);
        if (jcEtcCardBlackListEntity.getBlackType().equals("1") && !"1".equals(Objects.isNull(flag)?null:flag.toString())) { //拉白状态，删除
            int delCount = jcEtcCardBlackListMapper.delEtcBlackList(jcEtcCardBlackListEntity.getAvtId());
            if (delCount < 1) {
                throw new RuntimeException("删除失败,请重试！");
            }
        } else if (jcEtcCardBlackListEntity.getBlackType().equals("1") && "1".equals(Objects.isNull(flag)?null:flag.toString())) { //当操作为撤销黑名单操作时，ETC卡黑名单状态为删除状态，不做任何操作
            result.setSuccess(true);
            return result;
        } else if (jcEtcCardBlackListEntity.getBlackType().equals("5")) { //黑名单状态，转白
            int uptCount = jcEtcCardBlackListMapper.uptEtcBlackList(jcEtcCardBlackListEntity.getAvtId());
            if (uptCount < 1) {
                throw new RuntimeException("更新失败,请重试！");
            }
            jcEtcCardBlackListEntity.setBlackType("1");
            jcEtcCardBlackListEntity.setBlackListType(1); //状态名单类型与黑名单状态值一致，更新为1:正常
            jcEtcCardBlackListEntity.setDeleteFlag(0); //转白标志位更新为0未下发状态
            jcEtcCardBlackListEntity.setFlag(0); //更新标志位为0未下发状态
            jcEtcCardBlackListEntity.setDeleteTime(new Date()); //转白时间
            jcEtcCardBlackListEntity.setSumMoneny(0d);
            jcEtcCardBlackListEntity.setSumTotal(0);
        }
        jcEtcCardBlackListEntity.setTime(new Date()); //更新时间
        //将删除更新的黑名单保存至历史表
        int addCount = jcEtcCardBlackListMapper.addEtcBlackListHistory(jcEtcCardBlackListEntity);
        if (addCount < 1) {
            result.setSuccess(false);
            throw new RuntimeException("添加失败，请重试！");
        }
        result.setSuccess(true);
        result.setData(jcEtcCardBlackListEntity.getAvtId());
        return result;
    }
}
