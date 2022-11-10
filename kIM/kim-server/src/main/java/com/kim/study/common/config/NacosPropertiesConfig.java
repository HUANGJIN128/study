package com.kim.study.common.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : kim.
 * Create Date  : 2022-03-23
 * Create Time  : 13:01
 * Description  : Nacos配置初始化
 * Project Name : project-business-sso
 * Package Name : com.meritdata.cloud.multianalysis.config
 */

@Slf4j
@Component
public class NacosPropertiesConfig {

    private static PropertiesConfig propertiesConfig;

    @Bean
    public PropertiesConfig propertiesConfig() {
        log.info("NacosPropertiesConfig => Loading Properties from Nacos ********************");
        return new PropertiesConfig();
    }

    @Bean(name = "dospMultiAnalysisProperties")
    public PropertiesConfig initNacosProperties(@Qualifier("propertiesConfig") PropertiesConfig propertiesConfig) {
        NacosPropertiesConfig.propertiesConfig = propertiesConfig;
        log.info("NacosPropertiesConfig => Loading Properties from Nacos {}", propertiesConfig);
        return propertiesConfig;
    }

    /**
     * 对外方法
     *
     * @return
     */
    public static PropertiesConfig get() {
        return NacosPropertiesConfig.propertiesConfig;
    }

    @Data
    @Component
    @RefreshScope
    @ConfigurationProperties(prefix = "kim.server")
    public class PropertiesConfig {

        /**
         * 指标的来源 PA-INDICATOR / INDICATOR
         */
        private String defaultIndicatorSource;
        /**
         * 指标分类的根节点类型，对应pf_loopup表
         */
        private String defaultIndicatorType;

        /**
         * 指标 数据查询的数据源类型
         */
        private String defaultIndicatorDataSourceType;

        /**
         * 指标查询的数据源ID， pf_tempo_resource表中
         */
        private String defaultIndicatorDataSourceId;

        /**
         * 第三方的appId
         */
        private String thirdIndicatorAppId;

        /**
         * 第三方接入的Key
         */
        private String thirdIndicatorAppKey;

        /**
         * 指标元数据接口
         */
        private String thirdIndicatorRestUrl;

        /**
         * 指标维度元数据接口
         */
        private String thirdIndicatorDimRestUrl;

        /**
         * 指标数据查询接口
         */
        private String thirdIndicatorResultUrl;


        /**
         * 文件上传单次最大处理条数
         */
        private Map<String, Integer> upLoadExcelCount = new HashMap<>();

        /**
         * 指标数据查询接口
         */
        private String downloadTempDir;

        /**
         * licenseKey
         */
        private String licenseKey;

        /**
         * designerKey
         */
        private String designerKey;

        /**
         * starRocks 采用Stream Load 方式导入数据的端口号
         */
        private Integer starrocksStreamLoadPort = 8030;
        /**
         * 成果操作的最大权限值
         */
        private Integer operationMax;
        /**
         *上传excel解析入库是否开启多sheet页
         */
        private Boolean isOpenMultipleSheet;
        /**
         * 大字段记录文件临时目录
         */
        private String longBlobTempDir;
        /**
         * 是否开启大字段数据保存临时文件
         */
        private boolean isOpenLongBlobToJson;
        /**
         * 模板分析相关桶名称
         */
        private String multianalysisBucketName;
        /**
         * 模板分析桶下数据集目录
         */
        private String multianalysisBucketNameDataSet;
        /**
         * 模板分析桶下草稿目录
         */
        private String multianalysisBucketNameFromwork;
        /**
         * 模板分析桶下模板目录
         */
        private String multianalysisBucketNameTemplate;

        /**
         * excel文件上传相关桶名称
         */
        private String multianalysisUploadBucketName;

        /**
         * minio地址
         */
        private String minioEndpoint;

        /**
         * minio用户名
         */
        private String minioUserName;

        /**
         * minio密码
         */
        private String minioPassword;
        /**
         * 白名单
         */
        private List<String> unAuthorizationUrl;
    }
}
