package org.edu.timelycourse.mc.biz.model;

import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import org.edu.timelycourse.mc.biz.enums.EContractDebtStatus;
import org.edu.timelycourse.mc.biz.enums.EContractStatus;
import org.edu.timelycourse.mc.biz.enums.EEnrollmentType;
import org.edu.timelycourse.mc.common.utils.EntityUtils;
import org.edu.timelycourse.mc.common.utils.ValidatorUtil;

import java.util.Date;
import java.util.List;

@Data
public class ContractModel extends BaseEntity
{
    /**
     * 合同编号
     */
    private String contractNo;

    /**
     * 咨询师ID
     */
    private Integer consultantId;
    private UserModel consultant;

    /**
     * 学管师
     */
    private Integer supervisorId;
    private UserModel supervisor;

    /**
     * 报名类型
     */
    private Integer enrollType;

    /**
     * 学生ID
     */
    private Integer studentId;
    /**
     * 学生信息
     */
    private StudentModel student;

    /**
     * 学生年段
     */
    private Integer levelId;
    private SystemConfigModel level;

    /**
     * 细分年段
     */
    private Integer subLevelId;
    private SystemConfigModel subLevel;

    /**
     * 课程名称
     */
    private Integer courseId;
    private SchoolProductModel course;

    /**
     * 课程子类
     */
    private Integer subCourseId;
    private SchoolProductModel subCourse;

    /**
     * 报名课时
     */
    private double enrollPeriod;

    /**
     * 赠送课时
     */
    private double freePeriod;

    /**
     * 签约总价
     */
    private double contractPrice;

    /**
     * 优惠金额
     */
    private double discountPrice;

    /**
     * 实收金额
     */
    private double totalPrice;

    /**
     * 合同签约日期
     */
    private Date contractDate;

    /**
     * 合同创建时间
     */
    private Date creationTime;

    /**
     * 合同更新时间
     */
    private Date lastUpdateTime;

    /**
     * 收据列表
     */
    private List<InvoiceModel> invoices;

    /**
     * 合同状态
     */
    private Integer contractStatus;

    /**
     * 缴费状态
     */
    private Integer payStatus;

    /**
     * 获取已缴费金额
     */
    public double getPayTotal ()
    {
        double total = 0;
        if (invoices != null)
        {
            for (InvoiceModel invoice : invoices)
            {
                total += invoice.getPrice();
            }
        }
        return total;
    }

    /**
     * 学校ID
     */
    private Integer schoolId;

    public ContractModel () {}

    public ContractModel (String contractNo, Integer schoolId)
    {
        this.contractNo = contractNo;
        this.schoolId = schoolId;
    }

    @Override
    public boolean isValidInput ()
    {
        boolean valid =
                EEnrollmentType.hasValue(enrollType) && EContractStatus.hasValue(contractStatus) &&
                Strings.isNotEmpty(contractNo) && EContractDebtStatus.hasValue(payStatus) &&
                ValidatorUtil.isFloatNumber(contractPrice, totalPrice) &&
                EntityUtils.isValidEntityId(consultantId, levelId, subLevelId, courseId, subCourseId, schoolId) &&
                student.isValidInput() && contractDate != null;

        if (valid && invoices != null)
        {
            for (InvoiceModel invoice : invoices)
            {
                invoice.setSchoolId(schoolId);
                valid = invoice.isValidInput();
                if (!valid)
                {
                    break;
                }
            }
        }

        return valid;
    }

    @Override
    public String getUrlParams()
    {
        StringBuilder builder = new StringBuilder();
        appendParam(builder, "schoolId", schoolId);
        appendParam(builder, "studentId", student != null ? student.getId() : null);
        appendParam(builder, "consultantId", consultantId);
        appendParam(builder, "contractStatus", contractStatus);
        appendParam(builder, "payStatus", payStatus);
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }
}
