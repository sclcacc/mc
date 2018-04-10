package org.edu.timelycourse.mc.biz.service;

import org.edu.timelycourse.mc.biz.model.ContractModel;
import org.edu.timelycourse.mc.biz.model.InvoiceModel;
import org.edu.timelycourse.mc.biz.model.StudentModel;
import org.edu.timelycourse.mc.biz.repository.ContractRepository;
import org.edu.timelycourse.mc.biz.repository.InvoiceRepository;
import org.edu.timelycourse.mc.biz.repository.StudentRepository;
import org.edu.timelycourse.mc.biz.utils.Asserts;
import org.edu.timelycourse.mc.common.exception.ServiceException;
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

    private StudentRepository studentRepository;

    private InvoiceRepository invoiceRepository;

    @Autowired
    public ContractService(ContractRepository repository,
                           StudentRepository studentRepository,
                           InvoiceRepository invoiceRepository)
    {
        super(repository);
        this.studentRepository = studentRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public ContractModel add(ContractModel model)
    {
        StudentModel studentEntity = model.getStudent();
        if (studentEntity != null)
        {
            studentEntity.setConsultantId(model.getConsultantId());
            studentEntity.setCourseId(model.getCourseId());
            studentEntity.setLevelId(model.getLevelId());
            studentEntity.setSubCourseId(model.getSubCourseId());
            studentEntity.setSubLevelId(model.getSubLevelId());

            if (model.isValid())
            {
                // in case student been selected from suggest lookup
                if (model.getStudentId() != null && model.getStudentId() > 0)
                {
                    studentEntity = (StudentModel) Asserts.assertEntityNotNullById(studentRepository, model.getStudentId());
                }
                else
                {
                    studentRepository.insert(model.getStudent());
                }

                model.setStudentId(studentEntity.getId());
                model.setCreationTime(new Date());

                // add contract
                super.add(model);

                // add invoices
                if (model.getInvoices() != null)
                {
                    for (InvoiceModel invoice : model.getInvoices())
                    {
                        invoice.setCreationTime(new Date());
                        invoice.setContractId(model.getId());
                        invoiceRepository.insert(invoice);
                    }
                }

                return model;
            }
        }

        throw new ServiceException(String.format("Invalid model data to add, %s", model));
    }

    @Override
    public ContractModel update(ContractModel entity)
    {
        entity.setLastUpdateTime(new Date());
        return super.update(entity);
    }

}