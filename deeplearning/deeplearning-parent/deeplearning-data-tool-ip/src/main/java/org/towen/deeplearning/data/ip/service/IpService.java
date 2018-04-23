package org.towen.deeplearning.data.ip.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.towen.deeplearning.common.util.FileUtil;
import org.towen.deeplearning.common.util.IpUtil;
import org.towen.deeplearning.data.dao.MetaIpLocationDao;
import org.towen.deeplearning.data.ip.common.IpAppConfig;
import org.towen.deeplearning.data.ip.common.LocationUtil;
import org.towen.deeplearning.data.model.MetaIpLocationEntity;

@Service
public class IpService {

    private File sourceFile;

    @Resource
    private IpAppConfig ipAppConfig;
    @Resource
    private MetaIpLocationDao metaIpLocationDao;

    @Transactional(rollbackFor = Exception.class)
    public void startUp() {
        sourceFile = getSourceFile();
        int totalLines = 0;
        int lineSize = ipAppConfig.getLineSize();
        if (sourceFile.isFile() && sourceFile.exists()) {
            try {
                totalLines = FileUtil.getTotalLines(sourceFile);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (totalLines == 0) {
                return;
            }
            metaIpLocationDao.createMetaIpLocationTempTable();
            System.out.println("Create meta ip location temp table.");
            System.out.println("totalLines " + totalLines);
            int length = totalLines % lineSize == 0 ? totalLines / lineSize : totalLines / lineSize + 1;
            int begin = 0;
            int end = 0;
            for (int i = 0; i < length; i++) {
                begin = lineSize * i + 1;
                if (i != length - 1) {
                    end = lineSize * (i + 1);
                } else {
                    end = totalLines;
                }
                List<String> ipContents = null;
                try {
                    ipContents = FileUtil.readDataByLineNumber(sourceFile, begin, end);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.print(i + ". ");
                analysisIP(ipContents);

            }
            metaIpLocationDao.createIndex();
            metaIpLocationDao.updateMetaIpLocationTable();
            metaIpLocationDao.updateMetaIpLocationIsEnable();
            metaIpLocationDao.insertMetaIpLocationTable();
            System.out.println("Update meta_ip_location table.");
//            metaIpLocationDao.dropIpTempTable();
            System.out.println("Drop ip temp table.");
        } else {
            System.out.println("Source file is not exists");
        }
    }

    private File getSourceFile() {
        if (sourceFile == null) {
            sourceFile = new File(ipAppConfig.getDataSource());
        }
        return sourceFile;
    }

    private void analysisIP(List<String> ipContents) {
        List<MetaIpLocationEntity> metaIpLocationEntities = new ArrayList<MetaIpLocationEntity>();
        for (String ipContent : ipContents) {
            MetaIpLocationEntity metaIpEntity = new MetaIpLocationEntity();
            String[] dataArray = FileUtil.analysisLine(ipContent);
            metaIpEntity.setBegin(IpUtil.transformIP(dataArray[0]));
            metaIpEntity.setEnd(IpUtil.transformIP(dataArray[1]));
            LocationUtil.analysisLocation(metaIpEntity, dataArray[2], ipAppConfig.getProvince());
            if (dataArray.length > 3) {
                metaIpEntity.setNet(dataArray[3]);
            }
            metaIpLocationEntities.add(metaIpEntity);
        }
        metaIpLocationDao.saveMetaIpLocationEntity(metaIpLocationEntities);
        System.out.println("size is " + metaIpLocationEntities.size());
    }

}
