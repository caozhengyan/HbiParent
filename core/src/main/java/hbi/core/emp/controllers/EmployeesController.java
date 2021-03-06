package hbi.core.emp.controllers;

import com.google.gson.Gson;
import com.hand.hap.core.IRequest;
import com.hand.hap.system.controllers.BaseController;
import com.hand.hap.system.dto.ResponseData;
import hbi.core.emp.dto.Employees;
import hbi.core.emp.service.IEmployeesService;
import hbi.core.parsesoap.ResultBom;
import hbi.core.parsesoap.ResultInfo;
import hbi.core.parsesoap.SoapUtil;
import org.apache.axis.encoding.XMLType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeesController extends BaseController {

@Autowired
private IEmployeesService service;
private List<Employees>list=null;

    /**
     * 获得BOM关系及属性
     * @param dto
     * @param page
     * @param pageSize
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/hap/employees/query")
@ResponseBody
public ResponseData query(Employees dto, @RequestParam(defaultValue = DEFAULT_PAGE) int page,
                          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize, HttpServletRequest request,@RequestParam String id) {
  /*  list=new ArrayList<>();
    IRequest requestContext = createRequestContext(request);
    List<Employees> list2=new ArrayList<>();
    if(dto.getName()!=null) {
        List<Employees> list1 = service.select(requestContext, dto, 1, 10);
        for (int i = 0; i < list1.size(); i++) {
            List<Employees> employeesList = queryAll(list1.get(i));
            list2.addAll(employeesList);
        }
        return new ResponseData(list2);
    }else
    return new ResponseData(service.select(requestContext, dto, page, pageSize));*/
        String[] result=(String[])new SoapUtil().request("getBom","plmadmin","abc.1234", XMLType.XSD_STRING,"{id:"+id+"}");
        Gson gson=new Gson();
        ResultBom bom=gson.fromJson(result[0],ResultBom.class);
  return new ResponseData(bom.getBom());
}

@RequestMapping(value = "/hap/employees/submit")
@ResponseBody
public ResponseData update(HttpServletRequest request, @RequestBody List<Employees> dto){
    IRequest requestCtx = createRequestContext(request);
    return new ResponseData(service.batchUpdate(requestCtx, dto));
}

@RequestMapping(value = "/hap/employees/remove")
@ResponseBody
public ResponseData delete(HttpServletRequest request, @RequestBody List<Employees> dto){
    service.batchDelete(dto);
    return new ResponseData();
}
    @RequestMapping(value = "/hap/employees/updateParent")
    @ResponseBody
    public ResponseData updateParent(HttpServletRequest request, @RequestParam Long parentId, @RequestParam Long employeeId){
        IRequest requestCtx = createRequestContext(request);
        Employees e=new Employees();
        e.setEmployeeId(employeeId);
        e.setParentId(parentId.toString());
        service.updateByPrimaryKeySelective(requestCtx,e);
        return new ResponseData();
    }
    public List<Employees> queryAll(Employees emp)
    {
        list.add(emp);
        if(emp.getParentId()==null||"".equals(emp.getParentId())) {
            return list;
        }
        else
        {
            Employees e=new Employees();
            e.setEmployeeId(Long.parseLong(emp.getParentId()));
            return queryAll(service.selectByPrimaryKey(null,e));
        }
    }
}