package org.edu.timelycourse.mc.web.controller;

import org.edu.timelycourse.mc.beans.criteria.CourseAttendanceCriteria;
import org.edu.timelycourse.mc.beans.criteria.ContractCriteria;
import org.edu.timelycourse.mc.beans.enums.EBuiltInConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/academic")
public class AcademicController extends AbstractController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AcademicController.class);

    @RequestMapping("/course")
    public String showArrangementList (Model model,
                                    @RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer numPerPage,
                                    @ModelAttribute("contractCriteria") ContractCriteria criteria,
                                    HttpServletRequest request)
    {
        model.addAttribute("pagingBean", restServiceCaller.findContractsByPage(request, pageNum, numPerPage));
        model.addAttribute("criteria", criteria);
        model.addAttribute("module", getModuleName());
        return getModulePage("course/courseArrangementList");
    }

    @RequestMapping("/attendance")
    public String showAttendanceList (Model model,
                                       @RequestParam(required = false) Integer pageNum,
                                       @RequestParam(required = false) Integer numPerPage,
                                       @ModelAttribute("attendanceCriteria") CourseAttendanceCriteria criteria,
                                       HttpServletRequest request)
    {
        model.addAttribute("pagingBean", restServiceCaller.findContractsByPage(request, pageNum, numPerPage));
        model.addAttribute("criteria", criteria);
        model.addAttribute("module", getModuleName());
        return getModulePage("attendance/attendanceList");
    }

    @RequestMapping("/{contractId}/arrangement")
    public String showArrangement (Model model,
                                   @PathVariable(required = true, name = "contractId") Integer contractId,
                                   @RequestParam(required = false, name = "type") String type,
                                   HttpServletRequest request)
    {
        model.addAttribute("products", restServiceCaller.getAllProducts(request));
        model.addAttribute("level", restServiceCaller.findConfigByName(request, EBuiltInConfig.C_STUDENT_LEVEL.name()));
        model.addAttribute("entity", restServiceCaller.findContractById(request, contractId));
        model.addAttribute("type", type);
        model.addAttribute("arranges", restServiceCaller.findArrangementByContract(request, contractId));
        return getModulePage("course/dialog/dialogCourseArrangement");
    }

    @RequestMapping("/stat")
    public String showStat (Model model,
                            @RequestParam(required = false) Integer pageNum,
                            @RequestParam(required = false) Integer numPerPage,
                            @ModelAttribute("contractCriteria") ContractCriteria criteria,
                            HttpServletRequest request)
    {
        model.addAttribute("pagingBean", restServiceCaller.findContractsByPage(request, pageNum, numPerPage));
        model.addAttribute("criteria", criteria);
        model.addAttribute("module", getModuleName());
        return getModulePage("course/courseStatList");
    }

    protected String getMyModulePath()
    {
        return null;
    }

    @Override
    protected String getModuleName()
    {
        return "course";
    }

    @ModelAttribute("contractCriteria")
    private ContractCriteria getContractCriteria () { return new ContractCriteria(); }

    @ModelAttribute("attendanceCriteria")
    private CourseAttendanceCriteria getAttendanceCriteria () { return new CourseAttendanceCriteria(); }
}
