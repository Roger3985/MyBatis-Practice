package com.powernode.bank.utils;

import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 工具類：可以動態生成 DAO 的實現類。（或者說可以動態生成 DAO 的代理類）
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class GenerateDaoProxy { // 先把 GenerateDaoProxy 看做是 mybatis 框架實現的。

    /**
     * 生成 dao 介面的實現類，並且將實現的物件創建出來並返回。
     * @param daoInterface DAO 介面。
     * @return DAO 介面實現類物件。
     */
    public static Object generate(SqlSession sqlSession, Class daoInterface) {
        // 類池
        ClassPool pool = ClassPool.getDefault();
        // 製造類 (com.powernode.bank.dao.AccountDao --> (com.powernode.bank.dao.AccountDaoProxy) com.powernode.bank.dao.impl.AccountImpl)
        CtClass ctClass = pool.makeClass(daoInterface.getName() + "Proxy"); // 實際本質上就是在內存中動態生成一個代理類。
        // 製造介面
        CtClass ctInterface = pool.makeInterface(daoInterface.getName());
        // 實現介面
        ctClass.addInterface(ctInterface);
        // 實現介面中的所以方法
        // ......
        Method[] methods = daoInterface.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            // method 是介面中抽象方法
            // 將 method 這個都像方法進行實現
            try {
                // Account selectByActon(String actno);
                // public Account selectByActno(String actno) { 程式碼; }
                StringBuilder methodCode = new StringBuilder();
                methodCode.append("public ");
                methodCode.append(method.getReturnType().getName());
                methodCode.append(" ");
                methodCode.append(method.getName());
                methodCode.append("(");
                // 需要方法的形式參數列表
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> parameterType = parameterTypes[i];
                    methodCode.append(parameterType.getName());
                    methodCode.append(" ");
                    methodCode.append("arg" + i);
                    if (i != parameterTypes.length - 1) {
                        methodCode.append(",");
                    }
                }
                methodCode.append(")");
                methodCode.append("{");
                // 需要方法體中的程式碼
                methodCode.append("org.apache.ibatis.session.SqlSession sqlSession = com.powernode.bank.utils.SqlSessionUtil.openSession();");
                // 需要知道是誰麼類型的
                // sql 語句的 id 是框架使用者提供的，具有多變性。對於我框架開發人員來說。我不知道。
                // 既然我框架的開發者不知道 sqlId，怎麼辦呢？ mybatis 框架的開發者於是出了一個規定：凡是使用 GenerateDaoProxy 機制的。
                // sqlId 都不能隨便寫。namespace 必須是 dao 介面的全限定名稱，id 必須是 dao 介面中的方法名。
                String sqlId = daoInterface.getName() + "." + method.getName();
                SqlCommandType sqlCommandType = sqlSession.getConfiguration().getMappedStatement(sqlId).getSqlCommandType();
                if (sqlCommandType == sqlCommandType.INSERT) {
                    // 先不實現
                }
                if (sqlCommandType == sqlCommandType.DELETE) {
                    // 先不實現
                }
                if (sqlCommandType == sqlCommandType.UPDATE) {
                    methodCode.append("return sqlSession.update(\""+ sqlId +"\", arg0);");
                }
                if (sqlCommandType == sqlCommandType.SELECT) {
                    String returnType = method.getReturnType().getName();
                    methodCode.append("return ("+ returnType +")sqlSession.selectOne(\""+ sqlId +"\", arg0);");
                }

                methodCode.append("}");
                CtMethod ctMethod = CtMethod.make(methodCode.toString(), ctClass);
                ctClass.addMethod(ctMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 創建物件
        Object obj = null;
        try {
            Class<?> clazz = ctClass.toClass();
            obj = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
