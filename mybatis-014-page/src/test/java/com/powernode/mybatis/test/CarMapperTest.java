package com.powernode.mybatis.test;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.powernode.mybatis.mapper.CarMapper;
import com.powernode.mybatis.pojo.Car;
import com.powernode.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class CarMapperTest {

    @Test
    public void testSelectAll() {
        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);

        // 一定一定一定要注意：在執行 DQL 語句之前。開啟分頁功能。
        int pageNum = 2;
        int pageSize = 3;
        PageHelper.startPage(pageNum, pageSize);

        List<Car> cars = mapper.selectAll();
        // cars.forEach(car -> System.out.println(car));

        // 封裝分頁信息物件 new PageInfo()
        // PageInfo 物件是 PageHelp 插件提供的，用來封裝分頁相關信息的物件。
        PageInfo<Car> carPageInfo = new PageInfo<>(cars, 3);

        /*
            PageInfo{pageNum=2, pageSize=3, size=3, startRow=4, endRow=6, total=10, pages=4,
            list=Page{count=true, pageNum=2, pageSize=3, startRow=3, endRow=6, total=10, pages=4, reasonable=false, pageSizeZero=false}
            [Car{id=4, carNum='8654', brand='凱美瑞', guidePrice=3.0, produceTime='2000-11-11', carType='新能源'},
            Car{id=5, carNum='8654', brand='凱美瑞', guidePrice=3.0, produceTime='2000-11-11', carType='新能源'},
            Car{id=6, carNum='8654', brand='凱美瑞', guidePrice=3.0, produceTime='2000-11-11', carType='新能源'}],
            prePage=1, nextPage=3, isFirstPage=false, isLastPage=false, hasPreviousPage=true, hasNextPage=true,
            navigatePages=3, navigateFirstPage=1, navigateLastPage=3, navigatepageNums=[1, 2, 3]}
         */

        // JavaWeb
        // request.setAttribute("pageInfo", carPageInfo);

        System.out.println(carPageInfo);

        sqlSession.close();
    }

    @Test
    public void testSelectByPage() {
        // 獲取每頁顯示的紀錄條數
        int pageSize = 3;
        // 顯示在第幾頁：頁碼
        int pageNum = 2;
        // 計算開始下標
        int startIndex = (pageNum - 1) * pageSize;

        SqlSession sqlSession = SqlSessionUtil.openSession();
        CarMapper mapper = sqlSession.getMapper(CarMapper.class);
        List<Car> cars = mapper.selectByPage(startIndex, pageSize);
        cars.forEach(car -> System.out.println(car));
        sqlSession.close();
    }
}
