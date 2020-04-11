package cn.itcast.mp;

import ch.qos.logback.core.net.SyslogOutputStream;
import cn.itcast.mp.mapper.UserMapper;
import cn.itcast.mp.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testSelect() {
        //查询tb_user记录
        List<User> user = userMapper.selectList(null);
        System.out.println(user);
    }

    @Test
    public void testFindByid() {
        //查询tb_user记录
        User byId = userMapper.findById(2l);
        System.out.println(byId);
    }

    @Test
    public void testInsert() {
        User user = new User();
        user.setName("张三");
        user.setPassword("11111");
        user.setAge(20);
        user.setUserName("caocao");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-DD HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse("1992-01-01 00:00:00", dateTimeFormatter);
        user.setBirthday(localDateTime);
        int i = userMapper.insert(user);
        System.out.println(i);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setAge(100);
        user.setPassword("123456");
        //设置条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "李四");
        //只要不为null的值更新到数据库中
        int i = userMapper.update(user, queryWrapper);
        System.out.println(i);
    }

    @Test
    public void testUpdate1() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name", "张三").set("birthday", null);
        int i = userMapper.update(null, updateWrapper);
        System.out.println(i);
    }

    @Test
    public void delete() {
        //设置条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("age", "100");
        int i = userMapper.delete(queryWrapper);
        System.out.println(i);
    }

    @Test
    public void testSelectOne() {
        //设置条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "张三");
        //根据条件查询，只能查出一个否则就报错
        User user = userMapper.selectOne(queryWrapper);
        System.out.println(user);
    }

    @Test
    public void testSelectCount() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "张三");//等于
        queryWrapper.gt("age", 20);//大于
        //根据条件查询所以
        Integer count = userMapper.selectCount(queryWrapper);
        System.out.println(count);
    }

    @Test
    public void testSelectPage() {
        //设置条件
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("name", "李四");//等于
        //用构造方法设置当前页码，每页记录数
        int pageIndex = 1;//当前页数
        int size = 2;//每,页记录数
        Page<User> page = new Page(pageIndex, size);
        IPage<User> userIPage = userMapper.selectPage(page, queryWrapper);
        long pages = userIPage.getPages();//总页数
        long total = userIPage.getTotal();//总记录数
        //记录列表
        List<User> records = userIPage.getRecords();
        System.out.println(records);
    }

    @Test
    public void testEq() {
        //条件
     /*   QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", "张三");
        queryWrapper.gt("age", "20");
        queryWrapper.in("user_name", "caocao");
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);
*/
        //Lamba表达式写法：
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getName, "张三");
        queryWrapper.ge(User::getAge, 20);
        queryWrapper.in(User::getUserName, "caocao");
        List<User> users = userMapper.selectList(queryWrapper);
        System.out.println(users);

    }
}

