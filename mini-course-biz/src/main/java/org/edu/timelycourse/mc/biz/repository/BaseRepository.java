package org.edu.timelycourse.mc.biz.repository;

import com.github.pagehelper.Page;
import org.edu.timelycourse.mc.biz.model.BaseEntity;

import java.util.List;

public interface BaseRepository<T extends BaseEntity>
{
    T get(Integer id);
    Integer insert(final T entity);
    Integer update(final T entity);
    Integer delete(Integer id);
    List<T> getAll();
    List<T> getByEntity (final T entity);
    Page<T> getByPage();
    Page<T> getByPage (final T entity);
}
