package com.dreamcatcher.demo;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zgl
 * @create 2023-02-2023/2/6-14:48
 */
public class StreamDemo {
    public static void main(String[] args) {
        List<Author> authors = getAuthors();

        test16();
        //test15();
        //test14();
        //test13();
        //test12();
        //test11();
        //test10();
        //test09();
        //test08();
        //test07();
        //test06();
        //test05();
        //test04();
        //test03();
        //test02();
        //test01(authors);
    }

    private static void test16() {
        //findAny 获取流中的任意一个元素。该方法没有办法保证获取的一定是流中的第一个元素。
        //        获取任意一个年龄大于18的作家，如果存在就输出他的名字
        List<Author> authors = getAuthors();
        Optional<Author> optionalAuthor = authors.stream()
                .filter(author -> author.getAge()>18)
                .findAny();

        optionalAuthor.ifPresent(author -> System.out.println(author.getName()));
    }

    private static void test15() {
        //        获取一个年龄最小的作家，并输出他的姓名。
        List<Author> authors = getAuthors();
        Optional<Author> first = authors.stream()
                .sorted((o1, o2) -> o1.getAge() - o2.getAge())
                .findFirst();

        first.ifPresent(author -> System.out.println(author.getName()));
    }

    private static void test14() {
        //        获取一个存放所有作者名字的List集合。
        List<Author> authors = getAuthors();
        List<String> nameList = authors.stream()
                .map(author -> author.getName())
                .collect(Collectors.toList());
        System.out.println(nameList);
    }

    private static void test13() {
        //        分别获取这些作家的所出书籍的最高分和最低分并打印。
        //Stream<Author>  -> Stream<Book> ->Stream<Integer>  ->求值

        List<Author> authors = getAuthors();
        Optional<Integer> max = authors.stream()
                .flatMap(author -> author.getBooks().stream())//一个对象转换多个对象
                .map(book -> book.getScore())//一个对象操作
                .max((score1, score2) -> score1 - score2);

        Optional<Integer> min = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .map(book -> book.getScore())
                .min((score1, score2) -> score1 - score2);

        System.out.println(max.get());
        System.out.println(min.get());
    }

    private static void test12() {
        //count 可以用来获取当前流中元素的个数。
        //        打印这些作家的所出书籍的数目，注意删除重复元素。
        List<Author> authors = getAuthors();

        long count = authors.stream()
                .flatMap(author -> author.getBooks().stream())
                .distinct()
                .count();
        System.out.println(count);
    }

    private static void test11() {
        //forEach对流中的元素进行遍历操作，我们通过传入的参数去指定对遍历到的元素进行什么具体操作
        //        输出所有作家的名字
        List<Author> authors = getAuthors();

        authors.stream()
                .map(author -> author.getName())
                .distinct()
                .forEach(name-> System.out.println(name));
    }

    private static void test10() {
        //map只能把一个对象转换成另一个对象来作为流中的元素。而flatMap可以把一个对象转换成多个对象作为流中的元素。
        //打印所有书籍的名字。要求对重复的元素进行去重。
        List<Author> authors = getAuthors();
        authors.stream()
                .flatMap(new Function<Author, Stream<Book>>() {
                    @Override
                    public Stream<Book> apply(Author author) {
                        return author.getBooks().stream();
                    }
                })
                .distinct()
                .forEach(author-> System.out.println(author.getName()));

        //打印现有数据的所有分类。要求对分类进行去重。不能出现这种格式：哲学,爱情
        authors.stream()
                .flatMap(author -> author.getBooks().stream())//先找出所有的书
                .distinct()
                .flatMap(book -> Arrays.stream(book.getCategory().split(",")))//数组转换流对象
                .distinct()
                .forEach(book-> System.out.println(book));

    }

    private static void test09() {
        //        打印除了年龄最大的作家外的其他作家，要求不能有重复元素，并且按照年龄降序排序。
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted()
                .skip(1)
                .forEach(author -> System.out.println(author.getName()));
    }

    private static void test08() {
        //对流中的元素按照年龄进行降序排序，并且要求不能有重复的元素,然后打印其中年龄最大的两个作家的姓名。
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted()
                .limit(2)
                .forEach(author -> System.out.println(author.getName()));
    }

    private static void test07() {
        //sorted 可以对流中的元素进行排序。
        // 对流中的元素按照年龄进行降序排序，并且要求不能有重复的元素。
        //返回由该流的元素组成的流，按自然顺序排序。如果此流的元素不是Comparable，
        // 则在执行终端操作时可能会抛出 。java.lang.ClassCastException
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .sorted()
                .forEach(author -> System.out.println(author.getAge()));


        authors.stream()
                .distinct()
                //.sorted(Comparator.comparingInt(Author::getAge))  //默认升序
                .sorted((o1, o2) -> o2.getAge()-o1.getAge())
                .forEach(author -> System.out.println(author.getAge()));
    }

    private static void test06() {
        //distinct 可以去除流中的重复元素。
        //distinct方法是依赖Object的equals方法来判断是否是相同对象的。所以需要注意重写equals方法
        List<Author> authors = getAuthors();
        authors.stream()
                .distinct()
                .forEach(author -> System.out.println(author.getName()));
    }

    private static void test05() {
        List<Author> authors = getAuthors();
        //打印所有作家的姓名
        authors.stream()
                .map(author -> author.getName())//map 转换成自己想要的数据 对流中的元素进行计算或转换
                .forEach(s -> System.out.println(s));

        authors.stream()
                .map(author -> author.getAge())
                .map(age -> age + 10)
                .forEach(age -> System.out.println(age));
    }


    private static void test04() {
        List<Author> authors = getAuthors();
        //打印所有姓名长度大于1的作家的姓名
        Stream<Author> stream = authors.stream();
        stream.distinct()
                .filter(author -> author.getName().length() > 1)
                .forEach(author -> System.out.println(author.getName()));
    }

    private static void test03() {
        Map<String, Integer> map = new HashMap<>();
        map.put("蜡笔小新", 19);
        map.put("黑子", 17);
        map.put("日向翔阳", 16);

        //Set集合是单列集合
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        //for (Map.Entry<String, Integer> stringIntegerEntry : entrySet) {
        //    System.out.println(stringIntegerEntry); //蜡笔小新=19
        //}
        //转换了流对象
        Stream<Map.Entry<String, Integer>> stream = entrySet.stream();
        stream.filter(entry -> entry.getValue() > 16) //对集合中value>16进行判断
                .forEach(entry -> System.out.println(entry.getKey() + "========" + entry.getValue()));
    }

    private static void test02() {
        Integer[] arr = {1, 2, 3, 4, 5};
        //想转换stream流对象，这里用到了Arrays工具类
        //Stream<Integer> stream = Arrays.stream(arr);
        //方法二 底层使用的也是Arrays.stream(values);方法
        Stream<Integer> stream = Stream.of(arr);
        stream.distinct()
                .filter(integer -> integer > 2)
                .forEach(integer -> System.out.println(integer));
    }

    /**
     * 我们可以调用getAuthors方法获取到作家的集合。现在需要打印所有年龄小于18的
     * 作家的名字，并且要注意去重。
     *
     * @param authors
     */
    private static void test01(List<Author> authors) {

        authors.stream()  //把集合转换成流
                .distinct() //去重  对象去重重写equals和hashCode方法
                .filter(new Predicate<Author>() {
                    @Override
                    public boolean test(Author author) {
                        return author.getAge() < 18;
                    }
                })  //过滤
                .forEach(new Consumer<Author>() {
                    @Override
                    public void accept(Author author) {
                        System.out.println(author.getName()); //打印名字
                    }
                });

        System.out.println("===========简写=============");

        authors.stream()
                .distinct()
                .filter(author -> author.getAge() < 18)
                .forEach(author -> System.out.println(author.getName()));
    }

    private static List<Author> getAuthors() {
        //数据初始化
        Author author = new Author(1L, "蒙多", 33, "一个从菜刀中明悟哲理的祖安人", null);
        Author author2 = new Author(2L, "亚拉索", 15, "狂风也追逐不上他的思考速度", null);
        Author author3 = new Author(3L, "易", 14, "是这个世界在限制他的思维", null);
        Author author4 = new Author(3L, "易", 14, "是这个世界在限制他的思维", null);

        //书籍列表
        List<Book> books1 = new ArrayList<>();
        List<Book> books2 = new ArrayList<>();
        List<Book> books3 = new ArrayList<>();

        books1.add(new Book(1L, "刀的两侧是光明与黑暗", "哲学,爱情", 88, "用一把刀划分了爱恨"));
        books1.add(new Book(2L, "一个人不能死在同一把刀下", "个人成长,爱情", 99, "讲述如何从失败中明悟真理"));

        books2.add(new Book(3L, "那风吹不到的地方", "哲学", 85, "带你用思维去领略世界的尽头"));
        books2.add(new Book(3L, "那风吹不到的地方", "哲学", 85, "带你用思维去领略世界的尽头"));
        books2.add(new Book(4L, "吹或不吹", "爱情,个人传记", 56, "一个哲学家的恋爱观注定很难把他所在的时代理解"));

        books3.add(new Book(5L, "你的剑就是我的剑", "爱情", 56, "无法想象一个武者能对他的伴侣这么的宽容"));
        books3.add(new Book(6L, "风与剑", "个人传记", 100, "两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));
        books3.add(new Book(6L, "风与剑", "个人传记", 100, "两个哲学家灵魂和肉体的碰撞会激起怎么样的火花呢？"));

        author.setBooks(books1);
        author2.setBooks(books2);
        author3.setBooks(books3);
        author4.setBooks(books3);

        List<Author> authorList = new ArrayList<>(Arrays.asList(author, author2, author3, author4));
        return authorList;
    }
}
