package org.edu.timelycourse.mc.beans.dto;

import lombok.Data;
import org.edu.timelycourse.mc.beans.model.ContractAttendanceModel;
import org.edu.timelycourse.mc.beans.paging.PagingBean;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by x36zhao on 2018/4/20.
 */
@Data
public class CourseAttendanceDTO extends BaseDTO
{
    private Date date;
    private NamedOptionProperty teacher;
    private NamedOptionProperty student;
    private String courseName;
    private Integer status;
    private double cost;
    private String periodConversion;
    private Integer contractId;

    @Override
    public boolean isValid()
    {
        return false;
    }

    public static CourseAttendanceDTO from (ContractAttendanceModel model)
    {
        CourseAttendanceDTO dto = new CourseAttendanceDTO();
        BeanUtils.copyProperties(model, dto, "student", "teacher");
        dto.setTeacher(NamedOptionProperty.from(model.getTeacherId(), model.getTeacher(), "userName"));
        dto.setStudent(NamedOptionProperty.from(model.getStudentId(), model.getStudent(), "name"));
        dto.setCourseName(model.getContract() != null ? (model.getContract().getCourse().getProductName() + "" + model.getContract().getSubLevel().getConfigDescription()) : "");
        return dto;
    }

    public static List<CourseAttendanceDTO> from (List<ContractAttendanceModel> models)
    {
        List<CourseAttendanceDTO> vos = new ArrayList<>();
        for (ContractAttendanceModel model : models)
        {
            vos.add(from(model));
        }
        return vos;
    }

    public static PagingBean<CourseAttendanceDTO> from (PagingBean<ContractAttendanceModel> pagingBean)
    {
        PagingBean<CourseAttendanceDTO> result = new PagingBean<>();
        result.setItems(from(pagingBean.getItems()));
        result.setPageNumber(pagingBean.getPageNumber());
        result.setPageSize(pagingBean.getPageSize());
        result.setTotalItems(pagingBean.getTotalItems());
        result.setTotalPageNumber(pagingBean.getTotalPageNumber());
        return result;
    }
}
