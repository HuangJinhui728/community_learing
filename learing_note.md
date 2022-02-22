# Day 1

 1. github 提交保管仓库

    .git/config 下修改user

    以便commit时不会用其他名字，仓库隔离

    ```
     [user]
    
        	name =  HuangJinhui728 
    
    		email = 18357515851@163.com
    
    
    ```

    

2. ssh密钥 仓库

3. spring IOC 依赖注入 控制反转

   @Component



​		@Atowired

​		@Value

4. github 超时问题，之后拟采用gitee账号进行替代



#Day 2


 mvn flyway:migrate

数据库中部分字段取出为空

分析： 全都包含驼峰标识（含有下划线）




mybatis 驼峰标识无法识别导致 数据库数据无法读取问题

去官网-> configuration(配置设置) ——>在其中对应文件中（MyBatis reference page.）查找配置
查找于 驼峰标识相关部分（camel）
