package com.guideapp.backend.service.local;

import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.guideapp.backend.dao.local.LocalDAO;
import com.guideapp.backend.dao.local.LocalDAOImpl;
import com.guideapp.backend.entity.Local;
import com.guideapp.backend.util.ValidationUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by thales on 1/24/16.
 */
public class LocalServiceImpl implements LocalService {
    private LocalDAO mLocalDAO;

    /**
     * Configure the local service.
     */
    public LocalServiceImpl() {
        this.mLocalDAO = new LocalDAOImpl();
    }

    @Override
    public List<Local> list(Long idCity, Long idCategory) {
        Map<String, Object> filter = new TreeMap<>();

        if (idCity != null)
            filter.put("idCity", idCity);

        if (idCategory != null)
            filter.put("idCategories", idCategory);

        if (!filter.isEmpty())
            return mLocalDAO.listByProperties(filter);

        return mLocalDAO.listAll();
    }

    @Override
    public List<Local> list(String search) throws NotFoundException {
        return mLocalDAO.listByProperty("description", search);
    }

    @Override
    public Local getById(Long id) throws NotFoundException {
        Local local = mLocalDAO.getByKey(id);

        if (local == null) {
            throw new NotFoundException("Local not found");
        }

        return local;
    }

    @Override
    public void insert(Local local) throws ConflictException, NotFoundException {
        if (local == null) {
            throw new ConflictException("Local não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getDescription())) {
            throw new ConflictException("Descrição não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getIdCity())) {
            throw new ConflictException("Cidade não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getIdSubCategories())) {
            throw new ConflictException("Categoria não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getIdSubCategories())) {
            throw new ConflictException("Sub-Categoria não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getLatitude())) {
            throw new ConflictException("Latitude não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getLatitude())) {
            throw new ConflictException("Longitude não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getImagePath())) {
            throw new ConflictException("Imagem não informada.");
        }

        local.setTimestamp(new Date().getTime());
        mLocalDAO.insert(local);
    }

    @Override
    public void update(Local local) throws ConflictException, NotFoundException {

        if (local == null) {
            throw new ConflictException("Local não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getId())) {
            throw new ConflictException("Id não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getDescription())) {
            throw new ConflictException("Descrição não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getIdCity())) {
            throw new ConflictException("Cidade não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getIdSubCategories())) {
            throw new ConflictException("Categoria não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getIdSubCategories())) {
            throw new ConflictException("Sub-Categoria não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getLatitude())) {
            throw new ConflictException("Latitude não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getLatitude())) {
            throw new ConflictException("Longitude não informado.");
        }

        if (ValidationUtil.nullOrEmpty(local.getImagePath())) {
            throw new ConflictException("Imagem não informada.");
        }

        Local l = mLocalDAO.getByKey(local.getId());

        if (l == null) {
            throw new NotFoundException("Local não encontrado.");
        }

        local.setTimestamp(new Date().getTime());
        mLocalDAO.update(local);
    }

    @Override
    public void remove(Long id) throws ConflictException, NotFoundException {
        Local local = mLocalDAO.getByKey(id);

        if (local == null) {
            throw new NotFoundException("Local não encontrado.");
        }

        mLocalDAO.delete(local);
    }
}
