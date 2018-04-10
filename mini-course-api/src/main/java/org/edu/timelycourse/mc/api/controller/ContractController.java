package org.edu.timelycourse.mc.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.edu.timelycourse.mc.biz.model.ContractModel;
import org.edu.timelycourse.mc.biz.service.ContractService;
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
@RequestMapping("/api/${api.version}/contract")
@Api(tags = { "合同信息API" })
public class ContractController extends BaseController
{
    private static Logger LOGGER = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private ContractService contractService;

    @RequestMapping(path="", method= RequestMethod.GET)
    @ApiOperation(value = "Get either list of all contracts or by given query")
    public ResponseData getContract(@RequestParam(required = false, value = "query") String query)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Enter getContract - [query: %s]", query);

        try
        {
            return ResponseData.success(contractService.getAll());
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{contractId}", method= RequestMethod.GET)
    @ApiOperation(value = "Get contract by given id")
    public ResponseData getContractById(@PathVariable(required = true) Integer contractId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter getContractById - [contractId: %d]", contractId));

        try
        {
            return ResponseData.success(Asserts.assertEntityNotNullById(contractService, contractId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="", method= RequestMethod.POST)
    @ApiOperation(value = "Add contract by given entity")
    public ResponseData addContract (@RequestBody ContractModel model)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter addContract - [model: %s]", model));

        try
        {
            return ResponseData.success(contractService.add(model));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{contractId}", method= RequestMethod.DELETE)
    @ApiOperation(value = "Delete contract by given id")
    public ResponseData deleteContractById (@PathVariable(required = true) Integer contractId)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter deleteContractById - [contractId: %d]", contractId));

        try
        {
            Asserts.assertEntityNotNullById(contractService, contractId);
            return ResponseData.success(contractService.delete(contractId));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }

    @RequestMapping(path="/{contractId}", method= RequestMethod.PATCH)
    @ApiOperation(value = "Update contract with respect to the specified id")
    public ResponseData updateContract (
            @PathVariable(required = true) Integer contractId,
            @RequestBody ContractModel model)
    {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug(String.format("Enter updateContract - [contractId: %d, model: %s]", contractId, model));

        try
        {
            Asserts.assertEntityNotNullById(contractService, contractId);

            // in order to avoid overwritten id in the payload
            model.setId(contractId);

            return ResponseData.success(this.contractService.update(model));
        }
        catch (ServiceException ex)
        {
            return ResponseData.failure(ex.getMessage());
        }
    }
}