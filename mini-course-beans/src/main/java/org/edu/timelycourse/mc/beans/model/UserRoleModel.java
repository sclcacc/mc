package org.edu.timelycourse.mc.beans.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

/**
 * 平台用户角色
 *
 * Created by Marco on 2018/3/31.
 */
@Data
@ToString(exclude = "id")
@JsonIgnoreProperties(value= { "id", "userId" } )
public class UserRoleModel extends BaseModel
{
    private int userId;
    private String role;

    public UserRoleModel()
    {
    }

    public UserRoleModel(int userId, String authorityName)
    {
        this.userId = userId;
        this.role = authorityName;
    }

    @Override
    public boolean isValidInput ()
    {
        return true;
    }
}
