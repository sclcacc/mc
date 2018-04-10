package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.biz.model.ContractModel;
import org.edu.timelycourse.mc.biz.model.StudentModel;
import org.edu.timelycourse.mc.biz.service.StudentService;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.entity.ResponseData;
import org.edu.timelycourse.mc.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by x36zhao on 2018/4/3.
 */
@RestController
@RequestMapping("/api/${api.version}/student")
@Api(tags = { "学生信息API" })
public class StudentController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all students or by given query")
    public ResponseData getStudent(@RequestParam(required = false, value = "query") String query)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getStudent - [query: %s]", query);

        try
        {
            return ResponseData.success(studentService.getAll());
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{studentId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get student by given id")
    public ResponseData getStudentById(@PathVariable(required = true) Integer studentId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getStudentById - [studentId: %d]", studentId));

        try
        {
            return ResponseData.success(Asserts.assertEntityNotNullById(studentService, studentId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add student by given entity")
    public ResponseData addStudent (@RequestBody StudentModel model)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addStudent - [model: %s]", model));

        try
        {
            return ResponseData.success(studentService.add(model));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{studentId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete student by given id")
    public ResponseData deleteStudentById (@PathVariable(required = true) Integer studentId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteStudentById - [studentId: %d]", studentId));

        try
        {
            Asserts.assertEntityNotNullById(studentService, studentId);
            return ResponseData.success(studentService.delete(studentId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{studentId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update student with respect to the specified id")
    public ResponseData updateStudent (
            @PathVariable(required = true) Integer studentId,
            @RequestBody StudentModel model)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateStudent - [contractId: %d, model: %s]", studentId, model));

        try
        {
            Asserts.assertEntityNotNullById(studentService, studentId);

            // in order to avoid overwritten id in the payload
            model.setId(studentId);

            return ResponseData.success(studentService.update(model));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }
}