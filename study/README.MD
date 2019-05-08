### mybatis

#### 项目介绍
* 学习 mybatis 的测试用例在```recaton.test.study.mybatis.TestMybatis```中，相关的配置文件在test的resources文件夹下
* 测试使用内存数据库h2

#### h2数据库介绍
为方便测试，使用内存数据库，无需在本地安装mysql，postgresql等独立数据库，使用h2需注意以下几点：
1. h2目录下文件为数据文件，已包含3张表
```sql
create table company (id IDENTITY primary key, name text);
create table user (id IDENTITY primary key, company_id int, name text);
create table address (id IDENTITY primary key, user_id int, type text, province text, city text);
```
2. 需要直接访问h2，可以使用如下命令启动，在弹出的网页中连接成功后即可访问数据库
```shell
java -jar ~/.m2/repository/com/h2database/h2/1.4.197/h2-1.4.197.jar
```
3. 一份h2的数据文件同时只能启动一个实例，这就意味着在运行测试用例时不能使用java命令启动h2，反之亦然