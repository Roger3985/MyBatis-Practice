package com.powernode.bank.utils;

import org.apache.ibatis.javassist.CannotCompileException;
import org.apache.ibatis.javassist.ClassPool;
import org.apache.ibatis.javassist.CtClass;
import org.apache.ibatis.javassist.CtMethod;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 工具類：可以動態生成 DAO 的實現類。（或者說可以動態生成 DAO 的代理類）
 * @author Roger
 * @version 1.0
 * @since 1.0
 */
public class GenerateDaoProxy {

    /**
     * 生成 dao 介面的實現類，並且將實現的物件創建出來並返回。
     * @param daoInterface DAO 介面。
     * @return DAO 介面實現類物件。
     */
    public static Object generate(Class daoInterface) {
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
                methodCode.append("}");
                // 需要方法體中的程式碼

                CtMethod ctMethod = CtMethod.make("", ctClass);
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
