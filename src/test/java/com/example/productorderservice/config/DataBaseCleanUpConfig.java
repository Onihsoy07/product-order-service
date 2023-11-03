package com.example.productorderservice.config;

import com.google.common.base.CaseFormat;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataBaseCleanUpConfig implements InitializingBean {
/*
    private static final String SYS_CONFIG_TABLE_NAME = "sys_config";

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    private final List<String> tableNames = new ArrayList<>();

    @Override
    public void afterPropertiesSet() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet rs = databaseMetaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) {
                addExceptSysConfigTable(rs);
            }
        } catch (SQLException e) {
        }
    }

    private void addExceptSysConfigTable(ResultSet rs) throws SQLException {
        String tableName = rs.getString("TABLE_NAME");
        if (!tableName.equals(SYS_CONFIG_TABLE_NAME)) {
            tableNames.add(tableName);
            System.out.println("tableName = " + tableName);
        }
    }

    @Transactional
    public void execute() {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();
        entityManager.flush();
        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            entityManager.createNativeQuery(
                            "ALTER TABLE " + tableName + " AUTO_INCREMENT = 1")
                    .executeUpdate();
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }

*/


    @PersistenceContext
    private EntityManager em;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() throws Exception {
        final Set<EntityType<?>> entityTypes = em.getMetamodel().getEntities();

        tableNames = entityTypes.stream()
                .filter(e -> isEntity(e) && hasTableAnnotation(e))
                .map(e -> e.getJavaType().getSimpleName())
                .collect(Collectors.toList());

        final List<String> entityNames = entityTypes.stream()
                .filter(e -> isEntity(e) && !hasTableAnnotation(e))
                .map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
                .collect(Collectors.toList());
    }

    private Boolean isEntity(final EntityType<?> entityType) {
        return null != entityType.getJavaType().getAnnotation(Entity.class);
    }

    private Boolean hasTableAnnotation(final EntityType<?> entityType) {
        return null != entityType.getJavaType().getAnnotation(Table.class);
    }

    @Transactional
    public void execute() {
        em.flush();
        //연관관계 문제로 삭제 안될 시 강제 삭제 가능
        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0").executeUpdate();

        for (String tableName : tableNames) {
            em.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            //ID 생성 시퀀스 1로 초기화
            em.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1").executeUpdate();
        }

        em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1").executeUpdate();
    }



}
