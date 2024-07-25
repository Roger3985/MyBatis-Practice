package com.powernode.javassist.test;

import com.powernode.bank.dao.AccountDao;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class JavassistTest {

    @Test
    public void testGenerateAccountDaoImpl() throws Exception {
        // 獲取類池
        ClassPool pool = ClassPool.getDefault();
        // 製造類
        CtClass ctClass = pool.makeClass("com.powernode.bank.dao.impl.AccountDaoImpl");
        // 製造介面
        CtClass ctInterface = pool.makeInterface("com.powernode.bank.dao.AccountDao");
        // 實現介面
        ctClass.addInterface(ctInterface);
        // 實現介面中的所有方法
        // 獲取介面中所有的方法
        Method[] methods = AccountDao.class.getDeclaredMethods();
        Arrays.stream(methods).forEach(method -> {
            // method 是介面中的抽象方法
            // 把 method 抽象方法給實現了

            try {
                // 製造方法
                // public void delete() {}
                // public int update(String actno, Double balance) {}
                StringBuilder methodCode = new StringBuilder();
                methodCode.append("public "); // 追加修飾符列表
                methodCode.append(method.getReturnType().getName()); // 追加返回值類型
                methodCode.append(" ");
                methodCode.append(method.getName()); // 追加方法名
                methodCode.append("(");
                // 拼接參數 String actno, Double balance
                Class<?>[] parameterTypes = method.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    Class<?> parameterType = parameterTypes[i];
                    methodCode.append(parameterType.getName());
                    methodCode.append(" ");
                    methodCode.append("arg" + i);
                    if (i != parameterTypes.length -1) {
                        // 不是最後一個元素
                        methodCode.append(",");
                    }
                }
                methodCode.append("){System.out.println(11111); ");
                // 動態的添加 return 語句
                String returnTypeSimpleName = method.getReturnType().getSimpleName();
                if ("void".equals(returnTypeSimpleName)) {

                } else if ("int".equals(returnTypeSimpleName)) {
                    methodCode.append("return 1;");
                } else if ("String".equals(returnTypeSimpleName)) {
                    methodCode.append("return \"hello\";");
                }
                methodCode.append("}");
                System.out.println(methodCode);
                CtMethod ctMethod = CtMethod.make(String.valueOf(methodCode), ctClass);
                // 將方法添加到類中
                ctClass.addMethod(ctMethod);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // 在內存中生成 class，並加載到 JVM 當中
        Class<?> clazz = ctClass.toClass();
        // 創造物件
        AccountDao accontDao = (AccountDao) clazz.newInstance();
        // 調用方法
        accontDao.insert("aaaaaa");
        accontDao.delete();
        accontDao.update("aaaaaa", 1000.0);
        accontDao.selectByActno("aaaaaa");
    }

    @Test
    public void testGenerateImpl() throws Exception {
        // 獲取類池
        ClassPool pool = ClassPool.getDefault();
        // 製造類
        CtClass ctClass = pool.makeClass("com.powernode.bank.dao.impl.AccountDaoImpl");
        // 製造介面
        CtClass ctInterface = pool.makeInterface("com.powernode.bank.dao.AccountDao");
        // 添加介面到類中
        ctClass.addInterface(ctInterface); // AccountDaoImpl implements AccountDao
        // 實現介面中的方法
        // 製造方法
        CtMethod ctMethod = CtMethod.make("public void delete() {System.out.println(\"hello delete!\");}", ctClass);
        // 將方法添加到類中
        ctClass.addMethod(ctMethod);
        // 在內存中生成類：同時將生成的類加載到 JVM 當中。
        Class<?> clazz = ctClass.toClass();
        AccountDao accountDao = (AccountDao) clazz.newInstance();
        accountDao.delete();
    }

    @Test
    public void testGenerateFirstClass() throws Exception {
        // 獲取類池，這個類池就是用來給我生成 class 的
        ClassPool pool = ClassPool.getDefault();
        // 製造類（需要告訴 javassist，類名是啥）
        CtClass ctClass = pool.makeClass("com.powernode.bank.dao.impl.AccountDaoImpl");
        // 製造方法
        String methodCode = "public void insert() {System.out.println(123);}";
        CtMethod ctMethod = CtMethod.make(methodCode, ctClass);
        // 將方法添加到類中
        ctClass.addMethod(ctMethod);
        // 在內存中生成 class
        ctClass.toClass();


        // 類加載到 JVM 當中，返回 AccountDaoImpl 的字節碼
        Class<?> clazz = Class.forName("com.powernode.bank.dao.impl.AccountDaoImpl");
        // 創建物件
        Object obj = clazz.newInstance();
        // 獲取 AccountDaoImpl 中的 insert 方法
        Method insertMethod = clazz.getDeclaredMethod("insert");
        // 調用方法 insert
        insertMethod.invoke(obj);
    }
}
