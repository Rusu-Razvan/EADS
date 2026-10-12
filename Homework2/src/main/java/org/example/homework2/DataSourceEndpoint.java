package org.example.homework2;

import org.example.homework2.FileProperties;
import org.example.homework2.DataReader;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "datasource-info")
public class DataSourceEndpoint {

    private final FileProperties fileProperties;
    private final DataReader dataReader;

    public DataSourceEndpoint(FileProperties fileProperties, DataReader dataReader) {
        this.fileProperties = fileProperties;
        this.dataReader = dataReader;
    }

    @ReadOperation
    public Map<String, Object> getDataSourceInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("filePath", fileProperties.getPath());
        info.put("fileFormat", dataReader.getFormat());
        info.put("recordsLoadedCount", dataReader.readData().size());
        return info;
    }
}