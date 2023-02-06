package com.dreamcatcher.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.util.List;

/**
 * @author zgl
 * @create 2023-02-2023/2/6-14:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode//用于后期的去重使用
public class Author implements Comparable<Author> {
    //id
    private Long id;
    //姓名
    private String name;
    //年龄
    private Integer age;
    //简介
    private String intro;
    //作品
    private List<Book> books;


    @Override
    public int compareTo(Author o) {
        return o.getAge() - this.getAge();  //目标对象对与本身作比较前后顺序也意味着排序的不同
    }
}
