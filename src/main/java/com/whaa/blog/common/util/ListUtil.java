package com.whaa.blog.common.util;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * created by wangzelong 2021-07-09 17:04
 */
public class ListUtil {

    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>(100);
        for(int i=0;i<101;i++){
            list.add(i);
        }
        List<List<Integer>> partition = partition(list, 30);
        partition.forEach(System.out::println);
    }

    public static <T> List<List<T>> partition(List<T> list, int size) {
        if (list == null) {
            throw new NullPointerException("List must not be null");
        } else if (size <= 0) {
            throw new IllegalArgumentException("Size must be greater than 0");
        } else {
            return new ListUtil.Partition(list, size);
        }
    }

    private static class Partition<T> extends AbstractList<List<T>> {
        private final List<T> list;
        private final int size;

        private Partition(List<T> list, int size) {
            this.list = list;
            this.size = size;
        }

        @Override
        public List<T> get(int index) {
            int listSize = this.size();
            if (index < 0) {
                throw new IndexOutOfBoundsException("Index " + index + " must not be negative");
            } else if (index >= listSize) {
                throw new IndexOutOfBoundsException("Index " + index + " must be less than size " + listSize);
            } else {
                int start = index * this.size;
                int end = Math.min(start + this.size, this.list.size());
                return this.list.subList(start, end);
            }
        }

        @Override
        public int size() {
            return (int)Math.ceil((double)this.list.size() / (double)this.size);
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }
    }
}
