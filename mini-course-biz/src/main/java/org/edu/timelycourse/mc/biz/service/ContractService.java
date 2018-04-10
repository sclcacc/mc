package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.ContractModel;
import org.edu.timelycourse.mc.biz.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class ContractService extends BaseService<ContractModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(ContractService.class);

    @Autowired
    public ContractService(ContractRepository repository)
    {
        super(repository);
    }

    @Override
    public ContractModel add(ContractModel entity)
    {
        entity.setCreationTime(new Date());
        return super.add(entity);
    }

    @Override
    public ContractModel update(ContractModel entity)
    {
        entity.setLastUpdateTime(new Date());
        return super.update(entity);
    }

}
