package com.ambidekstr.testerapp.service;

import com.ambidekstr.testerapp.domain.MetaData;
import com.ambidekstr.testerapp.repository.MetaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by anatolii on 09.08.2016.
 */
@Service
public class MetaDataServiceImpl implements MetaDataService {

    @Autowired
    private MetaDataRepository metaDataRepository;

    @Override
    public void saveMetaData(MetaData metaData) {
        metaDataRepository.save(metaData);
    }

    @Override
    public List<MetaData> getAllMetaData() {
        return metaDataRepository.findAll();
    }
}
