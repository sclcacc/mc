package org.edu.timelycourse.mc.beans.dto;

import lombok.Data;
import org.edu.timelycourse.mc.beans.model.SystemConfigModel;

import java.io.Serializable;

@Data
public class SystemConfigDTO extends BaseDTO implements Serializable
{
    private Integer id;
    private String configName;
    private String configDescription;

    public static SystemConfigDTO from (final SystemConfigModel entity)
    {
        return null;
    }

    @Override
    public boolean isValid ()
    {
        return false;
    }
}
