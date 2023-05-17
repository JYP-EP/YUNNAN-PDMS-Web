package com.gridnt.pdms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gridnt.common.web.ResponseDTO;
import com.gridnt.common.web.WebUtils;
import com.gridnt.common.web.annotation.MultiRequestBody;
import com.gridnt.pdms.domain.Tb1046Ied;
import com.gridnt.pdms.domain.Tb1046IedListView;
import com.gridnt.pdms.domain.TbCrc;
import com.gridnt.pdms.domain.TreeNode;
import com.gridnt.pdms.service.ITb1041SubstationService;
import com.gridnt.pdms.service.ITb1046IedService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 保护设备Controller
 *
 * @author gridnt
 * @date 2021-10-23
 */
@RestController
@RequestMapping("/pdms/{version}/ied")
public class Tb1046IedController extends BaseController {

    @Autowired
    private ITb1041SubstationService substationService;

    @Autowired
    private ITb1046IedService baseService;

    /**
     * 查询保护设备列表
     *
     * @param version
     * @param page
     * @param tb1046Ied
     * @return
     */
    @PostMapping("/list")
    public ResponseDTO<IPage<Tb1046Ied>> list(@PathVariable("version") String version, @MultiRequestBody("page") Page<Tb1046Ied> page, @MultiRequestBody("entity") Tb1046Ied tb1046Ied) {
        final QueryWrapper<Tb1046Ied> queryWrapper = Wrappers.query();
        // 进行F1042Code的EQ查询
        queryWrapper.eq(tb1046Ied.getF1042Code() != null && !"".equals(tb1046Ied.getF1042Code().trim()), "F1042_CODE", tb1046Ied.getF1042Code());
        // 进行F1050Code的EQ查询
        queryWrapper.eq(tb1046Ied.getF1050Code() != null && !"".equals(tb1046Ied.getF1050Code().trim()), "F1050_CODE", tb1046Ied.getF1050Code());
        // 进行F1046Name的LIKE查询
        queryWrapper.like(tb1046Ied.getF1046Name() != null && !"".equals(tb1046Ied.getF1046Name().trim()), "F1046_NAME", tb1046Ied.getF1046Name());
        // 进行F1046Desc的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Desc() != null && !"".equals(tb1046Ied.getF1046Desc().trim()), "F1046_DESC", tb1046Ied.getF1046Desc());
        // 进行F1046Manufacturor的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Manufacturor() != null && !"".equals(tb1046Ied.getF1046Manufacturor().trim()), "F1046_MANUFACTUROR", tb1046Ied.getF1046Manufacturor());
        // 进行F1046Model的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Model() != null && !"".equals(tb1046Ied.getF1046Model().trim()), "F1046_MODEL", tb1046Ied.getF1046Model());
        // 进行F1046Configversion的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Configversion() != null && !"".equals(tb1046Ied.getF1046Configversion().trim()), "F1046_CONFIGVERSION", tb1046Ied.getF1046Configversion());
        // 进行F1046Aorb的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Aorb() != null, "F1046_AORB", tb1046Ied.getF1046Aorb());
        // 进行F1046Isvirtual的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Isvirtual() != null, "F1046_ISVIRTUAL", tb1046Ied.getF1046Isvirtual());
        // 进行F1046Type的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Type() != null, "F1046_TYPE", tb1046Ied.getF1046Type());
        // 进行F1046Subtype的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Subtype() != null, "F1046_SUBTYPE", tb1046Ied.getF1046Subtype());
        // 进行F1046Primaryequipment的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Primaryequipment() != null && !"".equals(tb1046Ied.getF1046Primaryequipment().trim()), "F1046_PRIMARYEQUIPMENT", tb1046Ied.getF1046Primaryequipment());
        // 进行F1046Dispatchname的LIKE查询
        queryWrapper.like(tb1046Ied.getF1046Dispatchname() != null && !"".equals(tb1046Ied.getF1046Dispatchname().trim()), "F1046_DISPATCHNAME", tb1046Ied.getF1046Dispatchname());
        // 进行F1046Deviceproperty的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Deviceproperty() != null, "F1046_DEVICEPROPERTY", tb1046Ied.getF1046Deviceproperty());
        // 进行F1046Rundate的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Rundate() != null && !"".equals(tb1046Ied.getF1046Rundate().trim()), "F1046_RUNDATE", tb1046Ied.getF1046Rundate());
        // 进行F1046Productiondate的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Productiondate() != null && !"".equals(tb1046Ied.getF1046Productiondate().trim()), "F1046_PRODUCTIONDATE", tb1046Ied.getF1046Productiondate());
        // 进行F1046Productionno的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Productionno() != null && !"".equals(tb1046Ied.getF1046Productionno().trim()), "F1046_PRODUCTIONNO", tb1046Ied.getF1046Productionno());
        // 进行F1046Softversion的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Softversion() != null && !"".equals(tb1046Ied.getF1046Softversion().trim()), "F1046_SOFTVERSION", tb1046Ied.getF1046Softversion());
        // 进行F1046Samplestyle的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Samplestyle() != null, "F1046_SAMPLESTYLE", tb1046Ied.getF1046Samplestyle());
        // 进行F1046Exportmodel的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Exportmodel() != null, "F1046_EXPORTMODEL", tb1046Ied.getF1046Exportmodel());
        // 进行F1046DcRatedvol的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046DcRatedvol() != null, "F1046_DC_RATEDVOL", tb1046Ied.getF1046DcRatedvol());
        // 进行F1046CtRatecurrent的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046CtRatecurrent() != null, "F1046_CT_RATECURRENT", tb1046Ied.getF1046CtRatecurrent());
        // 进行F1046Assetnature的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Assetnature() != null, "F1046_ASSETNATURE", tb1046Ied.getF1046Assetnature());
        // 进行F1046Localnote的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Localnote() != null, "F1046_LOCALNOTE", tb1046Ied.getF1046Localnote());
        // 进行F1046Protectioncategory的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Protectioncategory() != null, "F1046_PROTECTIONCATEGORY", tb1046Ied.getF1046Protectioncategory());
        // 进行F1046Protectiontype的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Protectiontype() != null, "F1046_PROTECTIONTYPE", tb1046Ied.getF1046Protectiontype());
        // 进行F1046Analognum的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Analognum() != null, "F1046_ANALOGNUM", tb1046Ied.getF1046Analognum());
        // 进行F1046Digitalnum的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Digitalnum() != null, "F1046_DIGITALNUM", tb1046Ied.getF1046Digitalnum());
        // 进行F1046Boardnum的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Boardnum() != null, "F1046_BOARDNUM", tb1046Ied.getF1046Boardnum());
        // 进行F1046Id的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Id() != null && !"".equals(tb1046Ied.getF1046Id().trim()), "F1046_ID", tb1046Ied.getF1046Id());
        // 进行F1046Sn的EQ查询
        queryWrapper.eq(tb1046Ied.getF1046Sn() != null && !"".equals(tb1046Ied.getF1046Sn().trim()), "F1046_SN", tb1046Ied.getF1046Sn());
        final Page<Tb1046Ied> result = baseService.page(page, queryWrapper);
        return WebUtils.createSuccessResponse(result);
    }


    /**
     * 获取保护设备详细信息
     *
     * @param version
     * @param {f1046Code}
     * @return
     */
    @GetMapping(value = "/{f1046Code}")
    public ResponseDTO<Tb1046Ied> get(@PathVariable("version") String version, @PathVariable("f1046Code") String f1046Code) {
        Tb1046Ied entity = baseService.getById(f1046Code);
        if (entity == null) {
            throw new CustomException("无法查得指定的保护设备");
        }
        return WebUtils.createSuccessResponse(entity);
    }
    /**
     * 获取子站信息
     * @return
     */
    @GetMapping("getSubstations")
    public ResponseDTO<List<TreeNode>> getSubstations(@PathVariable String version){
        List<TreeNode> substations = baseService.getSubstations();
        return WebUtils.createSuccessResponse(substations);
    }

    /**
     * 获取区域子站树信息
     * @return
     */
    @GetMapping("getSubstationsTree")
    public ResponseDTO<List<TreeNode>> getSubstationsTree(@PathVariable String version){
        List<TreeNode> substations = baseService.getSubstationsTree();
        return WebUtils.createSuccessResponse(substations);
    }

    /**
     * 根据区域获取子站信息
     * @return
     */
    @GetMapping("getSubstationsListByDq")
    public ResponseDTO<List<TreeNode>> getSubstationsListByDq(@PathVariable String version,@RequestParam("dq") String dq){
        List<TreeNode> substations = baseService.getSubstationsListByDq(dq);
        return WebUtils.createSuccessResponse(substations);
    }

    @GetMapping("getDQDescList")
    public ResponseDTO<List<TreeNode>> getDQDescList(@PathVariable String version){
        List<TreeNode> dqDescList = baseService.getDQDescList();
        return WebUtils.createSuccessResponse(dqDescList);
    }
    @GetMapping("getBaysByStation")
    public ResponseDTO<List<TreeNode>> getBaysByStation(@PathVariable("version") String version, @RequestParam("f1041Code") String f1041Code){
        return WebUtils.createSuccessResponse(baseService.getBaysVoltageByStation(f1041Code));
    }
    @GetMapping("getBaysVolByStation")
    public ResponseDTO<List<TreeNode>> getBaysVolByStation(@PathVariable("version") String version, @RequestParam("f1041Code") String f1041Code){
        return WebUtils.createSuccessResponse(baseService.getBaysVoltageByStation(f1041Code));
    }
    @GetMapping("getBaysByStationAndLevel")
    public ResponseDTO<List<TreeNode>> getBaysByStationAndLevel(@PathVariable("version") String version,
                                                                @RequestParam("f1041Code") String f1041Code,
                                                                @RequestParam("bayVol") String bayVol){
        return WebUtils.createSuccessResponse(baseService.getBaysByStationAndLevel(f1041Code,bayVol));
    }

    @GetMapping("getIedsByBay")
    public ResponseDTO<List<TreeNode>> getIEDsByBay(@PathVariable("version") String version, @RequestParam("bayCode") String bayCode){
        return WebUtils.createSuccessResponse(baseService.getIedsByBay(bayCode));
    }
    @GetMapping("getIEDsBySubstation")
    public ResponseDTO<List<Tb1046Ied>> getIEDsBySubstation(@PathVariable("version") String version, @RequestParam("Code") String Code,@RequestParam("Codetype") String Codetype){
        return WebUtils.createSuccessResponse(baseService.getIEDsBySubstation(Code,Codetype));
    }

    @GetMapping("getIEDListView")
    public ResponseDTO<List<Tb1046IedListView>> getIEDListView(@PathVariable("version") String version, @RequestParam("Code") String Code, @RequestParam("Codetype") String Codetype){
        return WebUtils.createSuccessResponse(baseService.getIEDListView(Code,Codetype));
    }
//    @GetMapping("getIEDsBySubstation")
//    public ResponseDTO<List<Tb1046Ied>> getIEDsBySubstation(@PathVariable("version") String version, @RequestParam("substationCode") String substationCode){
//        return WebUtils.createSuccessResponse(baseService.getIEDsBySubstation(substationCode));
//    }
    @PostMapping("getCrcData")
    public ResponseDTO<IPage<TbCrc>> getCrcData(@PathVariable("version") String version,@MultiRequestBody("page") Page<TbCrc> page){
        return WebUtils.createSuccessResponse(baseService.getCrcData(page));
    }

    @GetMapping("fulltree")
    public ResponseDTO<List<TreeNode>> getFullIedTree(){
       List<TreeNode> treelist =  baseService.getFullTree();
       return WebUtils.createSuccessResponse(treelist);
    }
}