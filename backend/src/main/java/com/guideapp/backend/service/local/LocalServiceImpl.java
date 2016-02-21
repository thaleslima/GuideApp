package com.guideapp.backend.service.local;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.dao.local.LocalDAO;
import com.guideapp.backend.dao.local.LocalDAOImpl;
import com.guideapp.backend.entity.Local;
import com.guideapp.backend.util.ValidationUtil;

import java.util.List;

/**
 * Created by thales on 1/24/16.
 */
public class LocalServiceImpl implements LocalService {
    LocalDAO localDAO;

    public LocalServiceImpl() {
        this.localDAO = new LocalDAOImpl();
    }

    @Override
    public List<Local> list() {
        return localDAO.listAll();
    }

    @Override
    public List<Local> list(String search) throws NotFoundException {
        return localDAO.listByProperty("description", search);
    }

    @Override
    public Local getById(Long id) throws NotFoundException {
        Local local = localDAO.getByKey(id);

        if(local == null) {
            throw new NotFoundException("Local not found");
        }

        return local;
    }

    @Override
    public void insert(Local local) throws ConflictException, NotFoundException {
        if(local == null) {
            throw new ConflictException("Local não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getDescription())){
            throw new ConflictException("Descrição não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getIdCity())){
            throw new ConflictException("Cidade não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getIdCategory())){
            throw new ConflictException("Categoria não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getIdSubCategory())){
            throw new ConflictException("Sub-Categoria não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getLatitude())){
            throw new ConflictException("Latitude não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getLatitude())){
            throw new ConflictException("Longitude não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getImagePath())){
            throw new ConflictException("Imagem não informada.");
        }

        localDAO.insert(local);
    }

    @Override
    public void update(Local local) throws ConflictException, NotFoundException {

        if(local == null) {
            throw new ConflictException("Local não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getId())){
            throw new ConflictException("Id não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getDescription())){
            throw new ConflictException("Descrição não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getIdCity())){
            throw new ConflictException("Cidade não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getIdCategory())){
            throw new ConflictException("Categoria não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getIdSubCategory())){
            throw new ConflictException("Sub-Categoria não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getLatitude())){
            throw new ConflictException("Latitude não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getLatitude())){
            throw new ConflictException("Longitude não informado.");
        }

        if(ValidationUtil.nullOrEmpty(local.getImagePath())){
            throw new ConflictException("Imagem não informada.");
        }

        Local l = localDAO.getByKey(local.getId());

        if(l == null){
            throw new NotFoundException("Local não encontrado.");
        }

        localDAO.update(local);
    }

    @Override
    public void remove(Long id) throws ConflictException, NotFoundException {
        Local local = localDAO.getByKey(id);

        if(local == null){
            throw new NotFoundException("Local não encontrado.");
        }

        localDAO.delete(local);
    }
}
