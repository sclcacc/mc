package org.edu.timelycourse.mc.api.controller.school;

import org.edu.timelycourse.mc.api.controller.BaseController;
import org.edu.timelycourse.mc.biz.entity.school.SchoolBasicInfo;
import org.edu.timelycourse.mc.biz.service.school.SchoolBasicInfoService;
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
@RequestMapping("/api/${api.version}/school")
public class SchoolController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(SchoolController.class);

    @Autowired
    private SchoolBasicInfoService schoolService;

    @RequestMapping(path="", method= RequestMethod.GET)
    public ResponseData getAllSchools()
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getAllSchools");

        try
        {
            return ResponseData.success(schoolService.getAll());
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{id}", method= RequestMethod.GET)
    public ResponseData getSchool(@PathVariable(required = true) Integer id)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getSchool - [schoolId : %s]", id));

        try
        {
            SchoolBasicInfo entity = (SchoolBasicInfo) Asserts.assertEntityNotNullById(schoolService, id);
            return ResponseData.success(entity);
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{id}", method= RequestMethod.DELETE)
    public ResponseData deleteSchool(@PathVariable(required = true) Integer id)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteSchool - [schoolId: %d]", id));

        try
        {
            Asserts.assertEntityNotNullById(schoolService, id);
            return ResponseData.success(schoolService.delete(id));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    public ResponseData addSchool(@RequestBody SchoolBasicInfo schoolInfo)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addSchool - [schoolInfo: %s]", schoolInfo));

        try
        {
            return ResponseData.success(schoolService.add(schoolInfo));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{id}", method= RequestMethod.PATCH)
    public ResponseData updateSchool(@PathVariable(required = true) Integer id, @RequestBody SchoolBasicInfo schoolInfo)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateSchool - [id: %d, schoolInfo: %s]", id, schoolInfo));

        try
        {
            Asserts.assertEntityNotNullById(schoolService, id);
            return ResponseData.success(this.schoolService.update(schoolInfo));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }
}