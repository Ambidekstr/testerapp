package com.ambidekstr.testerapp.service;

import com.ambidekstr.testerapp.domain.MetaData;

import java.util.List;

/**
 * Created by anatolii on 09.08.2016.
 */
public interface MetaDataService {
    void saveMetaData(MetaData metaData);
    List<MetaData> getAllMetaData();
}
