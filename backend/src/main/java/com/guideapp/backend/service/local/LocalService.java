package com.guideapp.backend.service.local;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.entity.Local;

import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public interface LocalService {
    List<Local> list();
    List<Local> list(String search) throws NotFoundException;
    List<Local> listByIdCategory(Long idCategory) throws NotFoundException;
    Local getById(Long id) throws NotFoundException;
    void insert(Local local) throws ConflictException, NotFoundException;
    void update(Local local) throws ConflictException, NotFoundException;
    void remove(Long id) throws ConflictException, NotFoundException;
}
