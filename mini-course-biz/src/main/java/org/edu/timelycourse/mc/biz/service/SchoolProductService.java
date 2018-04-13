package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.SchoolModel;
import org.edu.timelycourse.mc.biz.model.SchoolProductModel;
import org.edu.timelycourse.mc.biz.repository.SchoolProductRepository;
import org.edu.timelycourse.mc.biz.repository.SchoolRepository;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by x36zhao on 2017/3/17.
 */
@Service
public class SchoolProductService extends BaseService<SchoolProductModel>
{
    private static Logger LOGGER = LoggerFactory.getLogger(SchoolProductService.class);

    private SchoolProductRepository productRepository;

    @Autowired
    public SchoolProductService(SchoolProductRepository repository)
    {
        super(repository);
        this.productRepository = repository;
    }

    @Override
    public SchoolProductModel add(SchoolProductModel entity)
    {
        if (EntityUtils.isValidEntityId(entity.getParentId()))
        {
            Asserts.assertEntityNotNullById(repository, entity.getParentId());
        }

        entity.setCreationTime(new Date());
        return super.add(entity);
    }

    @Override
    public SchoolProductModel update(SchoolProductModel entity)
    {
        entity.setLastUpdateTime(new Date());
        return super.update(entity);
    }
}
